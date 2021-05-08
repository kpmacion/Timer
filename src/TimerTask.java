import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class TimerTask implements Runnable
{
    private static Thread timerThread;
    static boolean isWaiting = false;

    private long secondsCounter;

    int hh = 0;
    int mm = 0;
    int ss = 0;

    JLabel timeLabel;
    JLabel endLabel;
    StringBuilder timeString = new StringBuilder();

    private TimerTask(int hours, int minutes, int seconds, JLabel timeLabel, JLabel endLabel)
    {
        this.timeLabel = timeLabel;
        this.endLabel = endLabel;
        this.secondsCounter = (long) (hours*Math.pow(60,2) + minutes * 60L + seconds);
    }

    public static Thread createSingleTask(int hours, int minutes, int seconds, JLabel timeLabel, JLabel endLabel)
    {
        if(TimerTask.timerThread==null)
        {
           TimerTask.timerThread = new Thread(new TimerTask(hours, minutes, seconds, timeLabel, endLabel));
           timerThread.start();
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
                if(!isWaiting)
                {
                    hh = (int)(secondsCounter/Math.pow(60,2));
                    mm = (int)((secondsCounter - hh*Math.pow(60,2))/60);
                    ss = (int)((secondsCounter - hh*Math.pow(60,2))-mm*60);

                    timeString.setLength(0);
                    timeString.append((hh<10)?"0"+hh:hh)
                            .append(":")
                            .append((mm<10)?"0"+mm:mm)
                            .append(":")
                            .append((ss<10)?"0"+ss:ss);

                    timeLabel.setText(timeString.toString());

                    TimeUnit.SECONDS.sleep(1);
                    if(secondsCounter == 0)
                    {
                        timerThread = null;
                        timeLabel.setText("00:00:00");
                        endLabel.setText(TimerState.FINISH.stateDescription);
                        Thread.currentThread().interrupt();
                    }
                    secondsCounter--;
                }
            }
            catch (InterruptedException e)
            {
                timerThread = null;
                timeLabel.setText("00:00:00");
                return;
            }
        }
    }
}
