import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class TimerWindow extends JFrame
{
    Thread timerThread;

    Integer[] hoursArray = IntStream.rangeClosed(0, 23).boxed().toArray(Integer[]::new);
    Integer[] minutesArray = IntStream.rangeClosed(0, 59).boxed().toArray(Integer[]::new);
    Integer[] secondsArray = IntStream.rangeClosed(0, 59).boxed().toArray(Integer[]::new);

    public TimerWindow()
    {
        super.setTitle("Timer");
        super.setPreferredSize(new Dimension(250,240));
        super.setLayout(new BorderLayout());
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        super.setResizable(false);
        super.setVisible(true);
    }

    public void createGui()
    {
        AtomicReference<String> fileName= new AtomicReference<>("");

        TimeListPanel<Integer> hoursListPanel = new TimeListPanel<>(hoursArray, "HRS:");
        TimeListPanel<Integer> minutesListPanel = new TimeListPanel<>(minutesArray, "MIN:");
        TimeListPanel<Integer> secondsListPanel = new TimeListPanel<>(secondsArray, "SEC:");

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setBackground(new Color(205,205,205));
        comboBoxPanel.setLayout(new FlowLayout());
        comboBoxPanel.add(hoursListPanel, FlowLayout.LEFT);
        comboBoxPanel.add(minutesListPanel, FlowLayout.CENTER);
        comboBoxPanel.add(secondsListPanel, FlowLayout.RIGHT);

        JPanel mainPanel1=new JPanel();
        mainPanel1.setLayout(new BorderLayout());
        mainPanel1.add(comboBoxPanel, BorderLayout.CENTER);
        mainPanel1.add(new JSeparator(), BorderLayout.PAGE_END);

        JLabel timeLabel = new JLabel();
        timeLabel.setText("HH:MM:SS");
        timeLabel.setFont(new Font("", Font.BOLD, 30));
        timeLabel.setBackground(Color.white);
        timeLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        timeLabel.setOpaque(true);

        JLabel endLabel = new JLabel();
        endLabel.setHorizontalAlignment(SwingConstants.CENTER);
        endLabel.setPreferredSize(new Dimension(this.getPreferredSize().width, 20));
        endLabel.setBackground(new Color(140, 180, 255));
        endLabel.setFont(new Font("", Font.BOLD,16));
        endLabel.setOpaque(true);

        JButton startButton = new JButton("Start");
        startButton.setBackground(new Color(154, 255, 154));
        startButton.addActionListener((event)->
        {
            if((hoursListPanel.timeList.getItemAt(hoursListPanel.timeList.getSelectedIndex())!=0 || minutesListPanel.timeList.getItemAt(minutesListPanel.timeList.getSelectedIndex())!=0 || secondsListPanel.timeList.getItemAt(secondsListPanel.timeList.getSelectedIndex())!=0))
            {
                timerThread = TimerTask.createSingleTask(hoursListPanel.timeList.getItemAt(hoursListPanel.timeList.getSelectedIndex()), minutesListPanel.timeList.getItemAt(minutesListPanel.timeList.getSelectedIndex()), secondsListPanel.timeList.getItemAt(secondsListPanel.timeList.getSelectedIndex()), timeLabel, endLabel);
            }

            if (timerThread!=null)
            {
                endLabel.setText(TimerState.RUNNING.stateDescrition);
            }

            TimerTask.isWaiting = false;
        });

        JButton stopButton = new JButton("Stop");
        stopButton.setBackground(new Color(255, 255, 137));
        stopButton.addActionListener((event)->
        {
            if(timerThread!=null && timerThread.isAlive())
            {
                TimerTask.isWaiting = true;
                endLabel.setText(TimerState.STOPPED.stateDescrition);
            }
        });

        JButton resetButton = new JButton("Reset");
        resetButton.setBackground(new Color(255, 154, 154));
        resetButton.addActionListener((event)->
        {
            if(timerThread!=null)
            {
                TimerTask.isWaiting = false;
                timerThread.interrupt();
                endLabel.setText("");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(0,0,0,0));

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);

        JPanel mainPanel2 = new JPanel()
        {
            public void paintComponent(Graphics g)
            {
                if(!fileName.toString().equals(""))
                {
                    ImageIcon imageIcon = new ImageIcon(fileName.toString());
                    Image image = imageIcon.getImage();

                    g.drawImage(new ImageIcon(image.getScaledInstance(super.getWidth(), super.getHeight(),  java.awt.Image.SCALE_SMOOTH)).getImage(), 0, 0, null);
                }
                else
                {
                    super.paintComponent(g);
                }
            }
        };

        mainPanel2.setLayout(new FlowLayout());
        mainPanel2.add(timeLabel);
        mainPanel2.add(buttonPanel);
        mainPanel2.add(endLabel);

        JMenuItem jMenuItem = new JMenuItem("Set background image");
        jMenuItem.setFont(new Font("", Font.BOLD, 10));
        jMenuItem.addActionListener((event)->
        {
            JFileChooser fileChooser = new JFileChooser();
            if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
            {
                fileName.set(fileChooser.getSelectedFile().getAbsolutePath());
                mainPanel2.repaint();
            }
        });

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenuItem);

        super.setJMenuBar(jMenuBar);
        super.add(mainPanel1, BorderLayout.PAGE_START);
        super.add(mainPanel2, BorderLayout.CENTER);

        super.pack();
   }
}
