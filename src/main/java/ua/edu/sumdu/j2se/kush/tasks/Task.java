package ua.edu.sumdu.j2se.kush.tasks;

/**
 * The Task class that describes the user's tasks.
 * <p>Tasks may or may not be repeated. Non-recurring tasks have a specific
 * execution time. Recurring tasks have a start time, an end time, and a
 * recurrence interval. Non-active tasks are never executed.</p>
 *
 * @author <a href="mailto:vitaly.kush@gmail.com">Vitalii Kush</a>
 */
public class Task {

    /**
     * Task name (brief task description).
     */
    private String title;

    /**
     * Time to complete a non-recurring task.
     */
    private int time;

    /**
     * Start time of recurring task.
     */
    private int startTime;

    /**
     * End time for recurring task.
     */
    private int endTime;

    /**
     * The recurrence interval for the recurring task.
     */
    private int repeatInterval;

    /**
     * Task active or inactive status.
     */
    private boolean isActive;

    /**
     * Indicates whether the task is recurring or not.
     */
    private boolean isRepeated;

    /**
     * This class constructor defines a non-recurring task.
     *
     * @param title task name.
     * @param time  task time.
     */
    public Task(String title, int time) {
        if (time < 0) {
            throw new IllegalArgumentException("The time must be"
                    + " a positive number.");
        }
            this.title = title;
        this.time = time;
    }

    /**
     * This class constructor defines a recurring task.
     *
     * @param title    task name.
     * @param start    task start time.
     * @param end      task end time.
     * @param interval task recurrence interval.
     */
    public Task(String title, int start, int end, int interval) {
        if (start < 0) {
            throw new IllegalArgumentException("The start time must be"
                    + " a positive number.");
        }
        if (end < 0) {
            throw new IllegalArgumentException("The end time must be"
                    + " a positive number.");
        }
        if (interval <= 0) {
            throw new IllegalArgumentException("The start time must be"
                    + " a positive number and greater than 0.");
        }
        this.title = title;
        startTime = start;
        endTime = end;
        repeatInterval = interval;
        isRepeated = true;
    }

    /**
     * Returns the task name.
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
     * Gets the active or inactive status of a task.
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
     * Returns the time of the task if the task is non-repeatable, otherwise the
     * start time of the task.
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
     * @param end      the end time of the task.
     * @param interval the recurrence interval of the task.
     */
    public void setTime(int start, int end, int interval) {
        startTime = start;
        endTime = end;
        repeatInterval = interval;
        isRepeated = true;
    }

    /**
     * Returns the task start time if the task is repeatable, the task time
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
     * Returns the task end time if the task is repeatable, the task time
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
     * Returns the task's recurrence interval if the task is repeatable, and 0
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
     * @param interval the task recurrence interval.
     */
    public void setRepeatInterval(int interval) {
        repeatInterval = interval;
    }

    /**
     * Returns the task recurrence status.
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
     * Calculates the next execution time for the task.
     * <p>The method returns -1 in the following cases:</p>
     * <ul>
     * <li>the task is not active.</li>
     * <li>the non-repetitive task time less than current time</li>
     * <li>the repetitive task end time less than current time</li>
     * <li>the repetitive task next time more than end time</li>
     * </ul>
     * <p>Otherwise, returns the next time the task should be executed.</p>
     *
     * @param current current time.
     * @return the next time of the task or -1.
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

        int nextTime = startTime
                + ((current - startTime) / repeatInterval + 1)
                * repeatInterval;

        return nextTime <= endTime ? nextTime : -1;
    }
}
