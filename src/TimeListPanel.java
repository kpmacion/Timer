import javax.swing.*;
import java.awt.*;

public class TimeListPanel<T> extends JPanel
{
    JComboBox<T> timeList;

    public TimeListPanel(T[] timeArray, String labelText)
    {
        super.setLayout(new BorderLayout());
        super.setBackground(new Color(0,0,0,0));

        timeList = new JComboBox<>(timeArray);
        JLabel timeListLabel = new JLabel(labelText);
        timeListLabel.setFont(new Font("", Font.BOLD, 8));

        super.add(timeList, BorderLayout.CENTER);
        super.add(timeListLabel, BorderLayout.PAGE_START);
    }
}
