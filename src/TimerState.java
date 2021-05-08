public enum TimerState
{
    RUNNING("..."),
    FINISH("END OF COUNTDOWN"),
    STOPPED("STOPPED");

    String stateDescription;

    TimerState(String stateDescription)
    {
        this.stateDescription = stateDescription;
    }
}
