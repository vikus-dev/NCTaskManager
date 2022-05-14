package ua.edu.sumdu.j2se.kush.tasks;

/**
 * @author <a href="mailto:vitaly.kush@gmail.com">Vitalii Kush</a>
 */
public class Task {
    private String title;
    private int time;
    private int startTime;
    private int endTime;
    private int repeatInterval;
    private boolean isActive;
    private boolean isRepeated;

    /**
     * Class constructor specifying a non-repeatable task.
     *
     * @param title task name.
     * @param time  task time.
     */
    public Task(String title, int time) {
        this.title = title;
        this.time = time;
    }

    /**
     * Class constructor specifying a repeatable task.
     *
     * @param title    task name.
     * @param start    task start time.
     * @param end      task end time.
     * @param interval task recurrence interval.
     */
    public Task(String title, int start, int end, int interval) {
        this.title = title;
        startTime = start;
        endTime = end;
        repeatInterval = interval;
        isRepeated = true;
    }

    /**
     * Gets the task name.
     *
     * @return the task name.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the task name.
     *
     * @param title the task name.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the active or inactive status of the task.
     *
     * @return <b>true</b> if the task is active, <b>false</b> otherwise.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Activates or deactivates the task.
     *
     * @param active <b>true</b> to activate the task,
     *               <b>false</b> to deactivate.
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Gets the task time if the task is non-repeatable, the task start time
     * otherwise.
     *
     * @return the task time or the task start time.
     */
    public int getTime() {
        return !isRepeated ? time : startTime;
    }

    /**
     * Sets the task time and makes it non-repeatable.
     *
     * @param time the time of the task.
     */
    public void setTime(int time) {
        this.time = time;
        isRepeated = false;
    }

    /**
     * Sets the task period and makes it repeatable at regular intervals.
     *
     * @param start    the start time of the task.
     * @param end      the end time of the task (should be more than the start
     *                 time).
     * @param interval the recurrence interval of the task.
     */
    public void setTime(int start, int end, int interval) {
        startTime = start;
        endTime = end;
        repeatInterval = interval;
        isRepeated = true;
    }

    /**
     * Gets the task start time if the task is repeatable, the task time
     * otherwise.
     *
     * @return the task start time or time.
     */
    public int getStartTime() {
        return isRepeated ? startTime : time;
    }

    /**
     * Sets the task start time .
     *
     * @param start the start time of the task.
     */
    public void setStartTime(int start) {
        startTime = start;
    }

    /**
     * Gets the task end time if the task is repeatable, the task time
     * otherwise.
     *
     * @return the end time or the time.
     */
    public int getEndTime() {
        return isRepeated ? endTime : time;
    }

    /**
     * Sets the task end time
     *
     * @param end the task end time.
     */
    public void setEndTime(int end) {
        endTime = end;
    }

    /**
     * Gets the task recurrence interval if the task is repeatable, 0
     * otherwise.
     *
     * @return recurrence interval or 0.
     */
    public int getRepeatInterval() {
        return isRepeated ? repeatInterval : 0;
    }

    /**
     * Sets the task recurrence interval.
     *
     * @param interval the task recurrence interval
     */
    public void setRepeatInterval(int interval) {
        this.repeatInterval = interval;
    }

    /**
     * Gets the task recurrence status.
     *
     * @return <b>true</b> if the task is repeatable, <b>false</b> otherwise.
     */
    public boolean isRepeated() {
        return isRepeated;
    }

    /**
     * Makes the task repeatable.
     *
     * @param repeated true - make the task repeatable, false otherwise.
     */
    public void setRepeated(boolean repeated) {
        isRepeated = repeated;
    }

    /**
     * Calculates the next execution time for the task.<br> The method returns
     * -1 in the following cases:
     * <ul>
     * <li>the task is not active.</li>
     * <li>the non-repetitive task time less than current time</li>
     * <li>the repetitive task end time less than current time</li>
     * <li>the repetitive task next time more than end time</li>
     * </ul>
     *
     * @param current current time.
     * @return the next time of the task or -1 .
     */
    public int nextTimeAfter(int current) {

        if (!isActive) {
            return -1;
        }

        if (!isRepeated) {
            return current < time ? time : -1;
        }

        if (current > endTime) {
            return -1;
        }

        if (current < startTime) {
            return startTime;
        }

        int nextTime = ((current - startTime) / repeatInterval + 1)
                * repeatInterval + startTime;

        return nextTime <= endTime ? nextTime : -1;
    }

    @Override
    public String toString() {
        return "Task{"
                + "title='" + title + '\''
                + ", time=" + time
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", repeatInterval=" + repeatInterval
                + ", isActive=" + isActive
                + ", isRepeated=" + isRepeated
                + '}';
    }
}
