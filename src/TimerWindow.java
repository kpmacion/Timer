import javax.swing.*;
import java.awt.*;
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
        super.setPreferredSize(new Dimension(250,220));
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        super.setLayout(new GridLayout(2,1));

        super.setResizable(false);
        super.setVisible(true);
    }

    public void createGui()
    {
        TimeListPanel<Integer> hoursListPanel = new TimeListPanel<>(hoursArray, "HRS:");
        TimeListPanel<Integer> minutesListPanel = new TimeListPanel<>(minutesArray, "MIN:");
        TimeListPanel<Integer> secondsListPanel = new TimeListPanel<>(secondsArray, "SEC:");

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new FlowLayout());
        comboBoxPanel.add(hoursListPanel, FlowLayout.LEFT);
        comboBoxPanel.add(minutesListPanel, FlowLayout.CENTER);
        comboBoxPanel.add(secondsListPanel, FlowLayout.RIGHT);

        JPanel mainPanel1=new JPanel();
        mainPanel1.setLayout(new GridLayout(2,1));

        mainPanel1.add(comboBoxPanel, BorderLayout.CENTER);
        mainPanel1.add(new JSeparator(), BorderLayout.PAGE_END);

        JLabel timeLabel = new JLabel();
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);

        timeLabel.setText("HH:MM:SS");
        timeLabel.setFont(new Font("", Font.BOLD, 30));

        timeLabel.setBackground(Color.white);
        timeLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        timeLabel.setOpaque(true);

        JButton startButton = new JButton("Start");
        startButton.setBackground(new Color(154, 255, 154));

        startButton.addActionListener((e)->
        {
            if(hoursListPanel.timeList.getItemAt(hoursListPanel.timeList.getSelectedIndex())!=0 || minutesListPanel.timeList.getItemAt(minutesListPanel.timeList.getSelectedIndex())!=0 || secondsListPanel.timeList.getItemAt(secondsListPanel.timeList.getSelectedIndex())!=0)
            {
                timerThread = TimerTask.createSingleTask(hoursListPanel.timeList.getItemAt(hoursListPanel.timeList.getSelectedIndex()), minutesListPanel.timeList.getItemAt(minutesListPanel.timeList.getSelectedIndex()), secondsListPanel.timeList.getItemAt(secondsListPanel.timeList.getSelectedIndex()), timeLabel);
            }
        });

        JButton stopButton = new JButton("Stop");
        stopButton.setBackground(new Color(255, 154, 154));

        stopButton.addActionListener((e)->
        {
            if(timerThread!=null)
            {
                timerThread.interrupt();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        JPanel mainPanel2 = new JPanel();
        mainPanel2.setLayout(new FlowLayout());

        mainPanel2.add(timeLabel);
        mainPanel2.add(buttonPanel);

        super.add(mainPanel1, BorderLayout.PAGE_START);
        super.add(mainPanel2, BorderLayout.PAGE_END);

        super.pack();
   }
}
