public enum TimerState
{
    RUNNING("..."),
    FINISH("END OF COUNTDOWN"),
    STOPPED("STOPPED");

    String stateDescrition;

    TimerState(String stateDescrition)
    {
        this.stateDescrition = stateDescrition;
    }
}
