package ua.edu.sumdu.j2se.kush.tasks;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * The Task class that describes the user's tasks.
 * <p>Tasks may or may not be repeated. Non-recurring tasks have a specific
 * execution time. Recurring tasks have a start time, an end time, and a
 * recurrence interval. Non-active tasks are never executed.</p>
 *
 * @author <a href="mailto:vitaly.kush@gmail.com">Vitalii Kush</a>
 */
public class Task implements Cloneable, Serializable {

    private static final long serialVersionUID = -8816909865961436667L;

    /**
     * Task name (brief task description).
     */
    private String title;

    /**
     * Time to complete a non-recurring task.
     */
    private LocalDateTime time;

    /**
     * Start time of recurring task.
     */
    private LocalDateTime start;

    /**
     * End time for recurring task.
     */
    private LocalDateTime end;

    /**
     * The recurrence interval for the recurring task.
     */
    private int interval;

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
    public Task(String title,
                LocalDateTime time) {
        if (title == null) {
            throw new IllegalArgumentException("The title must be non-null.");
        }
        if (time == null) {
            throw new IllegalArgumentException("The time must be non-null.");
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
    public Task(String title, LocalDateTime start, LocalDateTime end,
                int interval) {

        if (title == null) {
            throw new IllegalArgumentException("The title must be non-null.");
        }
        if (start == null) {
            throw new IllegalArgumentException("The start time "
                    + "must be non-null.");
        }
        if (end == null) {
            throw new IllegalArgumentException("The end time "
                    + "must be non-null.");
        }
        if (interval <= 0) {
            throw new IllegalArgumentException("The interval must be >= 0.");
        }

        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
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
    public LocalDateTime getTime() {
        return !isRepeated ? time : start;
    }

    /**
     * Sets the task time and makes it non-repeatable.
     *
     * @param time the time of the task.
     */
    public void setTime(LocalDateTime time) {
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
    public void setTime(LocalDateTime start,
                        LocalDateTime end,
                        int interval) {

        if (start == null) {
            throw new IllegalArgumentException("The start time "
                    + "must be non-null.");
        }
        if (end == null) {
            throw new IllegalArgumentException("The end time "
                    + "must be non-null.");
        }

        if (interval <= 0) {
            throw new IllegalArgumentException("The interval must be >= 0.");
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
        isRepeated = true;
    }

    /**
     * Returns the task start time if the task is repeatable, the task time
     * otherwise.
     *
     * @return the task start time or time.
     */
    public LocalDateTime getStartTime() {
        return isRepeated ? start : time;
    }

    /**
     * Sets the task start time .
     *
     * @param start the start time of the task.
     */
    public void setStartTime(LocalDateTime start) {
        if (start == null) {
            throw new IllegalArgumentException("The start time "
                    + "must be non-null.");
        }
        this.start = start;
    }

    /**
     * Returns the task end time if the task is repeatable, the task time
     * otherwise.
     *
     * @return the end time or the time.
     */
    public LocalDateTime getEndTime() {
        return isRepeated ? end : time;
    }

    /**
     * Sets the task end time
     *
     * @param end the task end time.
     */
    public void setEndTime(LocalDateTime end) {
        if (end == null) {
            throw new IllegalArgumentException("The end time "
                    + "must be non-null.");
        }
        this.end = end;
    }

    /**
     * Returns the task's recurrence interval if the task is repeatable, and 0
     * otherwise.
     *
     * @return recurrence interval or 0.
     */
    public int getRepeatInterval() {
        return isRepeated ? interval : 0;
    }

    /**
     * Sets the task recurrence interval.
     *
     * @param interval the task recurrence interval.
     */
    public void setInterval(int interval) {
        if (interval <= 0) {
            throw new IllegalArgumentException("The interval must be >= 0.");
        }
        this.interval = interval;
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
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (!isActive) {
            return null;
        }
        LocalDateTime next = null;
        if (!isRepeated) {
            next = current.isBefore(time) ? time : null;
        } else if (current.isBefore(start)) {
            next = start;
        } else if (!current.isAfter(end)) {
            long diff = ChronoUnit.SECONDS.between(start, current);
            next = start.plusSeconds(interval * (diff / interval + 1));
            next = !next.isAfter(end) ? next : null;
        }
        return next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;

        return time.equals(task.time)
                && start == task.start
                && end == task.end
                && interval == task.interval
                && isActive == task.isActive
                && isRepeated == task.isRepeated
                && title.equals(task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, start, end, time, interval, isActive,
                isRepeated);
    }

    @Override
    public Task clone() {
        Task clone;
        try {
            clone = (Task) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", isActive=" + isActive +
                ", isRepeated=" + isRepeated +
                '}';
    }
}
