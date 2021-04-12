import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class TimerTask implements Runnable
{
    private static Thread timerThread = null;
    private long secondsCounter;

    int hh = 0;
    int mm = 0;
    int ss = 0;

    JLabel label;
    StringBuilder time = new StringBuilder();

    private TimerTask(int hours, int minutes, int seconds, JLabel label)
    {
        this.label = label;
        this.secondsCounter = (long) (hours*Math.pow(60,2) + minutes * 60L + seconds);
    }

    public static Thread createSingleTask(int hours, int minutes, int seconds, JLabel label)
    {
        if(TimerTask.timerThread==null)
        {
           TimerTask.timerThread = new Thread(new TimerTask(hours, minutes, seconds, label));
           TimerTask.timerThread.start();
        }
        return TimerTask.timerThread;
    }

    @Override
    public void run()
    {
        while(!Thread.currentThread().isInterrupted())
        {
            try
            {
                hh = (int)(secondsCounter/Math.pow(60,2));
                mm = (int)((secondsCounter - hh*Math.pow(60,2))/60);
                ss = (int)((secondsCounter - hh*Math.pow(60,2))-mm*60);

                time.setLength(0);
                time.append((hh<10)?"0"+hh:hh)
                        .append(":")
                        .append((mm<10)?"0"+mm:mm)
                        .append(":")
                        .append((ss<10)?"0"+ss:ss);

                label.setText(time.toString());

                if(secondsCounter == 0)
                {
                    Thread.currentThread().interrupt();
                }
                else
                {
                    TimeUnit.SECONDS.sleep(1);
                    secondsCounter--;
                }
            }
            catch (InterruptedException e)
            {
                timerThread=null;
                label.setText("00:00:00");
                return;
            }
        }
        timerThread=null;
    }
}
