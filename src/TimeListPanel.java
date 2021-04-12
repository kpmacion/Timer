import javax.swing.*;
import java.awt.*;

public class TimeListPanel<T> extends JPanel
{
    JComboBox<T> timeList;

    public TimeListPanel(T[] timeArray, String labelText)
    {
        super.setLayout(new BorderLayout());

        timeList = new JComboBox<>(timeArray);
        JLabel timeListLabel = new JLabel(labelText);
        timeListLabel.setFont(new Font("", Font.BOLD, 8));

        super.add(timeList, BorderLayout.CENTER);
        super.add(timeListLabel, BorderLayout.PAGE_START);
    }
}
