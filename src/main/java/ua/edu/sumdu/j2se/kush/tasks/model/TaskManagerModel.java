package ua.edu.sumdu.j2se.kush.tasks.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.sumdu.j2se.kush.tasks.utils.Storage;
import ua.edu.sumdu.j2se.kush.tasks.utils.Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;

public class TaskManagerModel {
    private static final Logger LOGGER =
            LogManager.getLogger(TaskManagerModel.class);
    private final Storage storage;
    private final AbstractTaskList taskList = new ArrayTaskList();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
            .ofPattern("dd.MM.yyyy H:mm");

    public TaskManagerModel(Storage storage) {
        this.storage = storage;
        loadData();
    }

    private void loadData() {
        LOGGER.debug("invoke method loadData()");
        storage.loadData(taskList);
    }

    private void saveData() {
        LOGGER.debug("invoke method saveData()");
        storage.saveData(taskList);
    }

    void addTask(Task task) {
        LOGGER.debug("invoke method addTask()");
        taskList.add(task);
        saveData();
        LOGGER.info("New task added " + task);
    }

    public void removeTask(Task task) {
        LOGGER.debug("Invoke method removeTask()");
        taskList.remove(task);
        saveData();
        LOGGER.info("Remove task " + task);
    }

    public AbstractTaskList getTaskList() {
        return taskList;
    }

    public void createNonRepeatingTask(String title, LocalDateTime dateTime) {
        LOGGER.debug("invoke method createNonRepeatingTask()");
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (!validateTitle(title)) {
            throw new IllegalArgumentException("Wrong task title! " +
                    "Title length must be from 3 to 30 characters");
        }
        if (!validateTaskStartTime(dateTime)) {
            throw new IllegalArgumentException("Wrong task time! "
                    + "The task time must be after the current time ("
                    + currentDateTime.format(dateTimeFormatter) + ").");
        }
        Task task = new Task(title, dateTime);
        task.setActive(true);
        addTask(task);
    }

    public void createRepeatingTask(String title, LocalDateTime start,
                                    LocalDateTime end, int interval) {
        LOGGER.debug("invoke method createRepeatingTask()");
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (!validateTitle(title)) {
            throw new IllegalArgumentException("Wrong task title! " +
                    "Title length must be from 3 to 30 characters");
        }

        if (!validateTaskStartTime(start)) {
            throw new IllegalArgumentException("Wrong task start time! "
                    + "The start time of the task must be after the" +
                    " current time ("
                    + currentDateTime.format(dateTimeFormatter) + ").");
        }

        if (!validateTaskEndTime(start, end)) {
            throw new IllegalArgumentException("Wrong task end time! "
                    + "The end time of the task must be after the start time.");
        }

        if (!validateRepeatingTaskInterval(interval)) {
            throw new IllegalArgumentException("Wrong interval! " +
                    "The interval must be greater than 0.");
        }
        Task task = new Task(title, start, end, interval);
        task.setActive(true);
        addTask(task);
    }

    /**
     * The title length must be form 3 to 30 characters.
     *
     * @param title title to validate.
     * @return true if title is valid, false otherwise.
     */
    private boolean validateTitle(String title) {
        return title.length() >= 3 && title.length() <= 30;
    }

    /**
     * Validates the start time of the task.
     *
     * @param dateTime dateTime to validate.
     * @return true if valid, otherwise false.
     */
    private boolean validateTaskStartTime(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now());
    }

    /**
     * Validates the end time if the task.
     * The end time must be after start time.
     *
     * @param start the start time of the task.
     * @param end   the end time of the task.
     * @return true if end > start, otherwise false.
     */
    private boolean validateTaskEndTime(LocalDateTime start,
                                        LocalDateTime end) {
        return end.isAfter(start);
    }

    /**
     * Validate the interval of a repeating task.
     * Interval must be greater than 0.
     *
     * @param interval interval to validate
     * @return true if valid, otherwise false.
     */
    private boolean validateRepeatingTaskInterval(int interval) {
        return interval > 0;
    }

    public SortedMap<LocalDateTime, Set<Task>> getCalendar(LocalDateTime start,
                                                           LocalDateTime end) {
        return Tasks.calendar(taskList, start, end);
    }

    public Set<Task> getTasksToPerform() {
        Set<Task> taskSet = new HashSet<>();
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        SortedMap<LocalDateTime, Set<Task>> timeline =
                getCalendar(now.minusMinutes(1), now);
        if (timeline.size() > 0) {
            if (timeline.firstKey().isEqual(now)) {
                taskSet = timeline.get(timeline.firstKey());
            }
        }
        return taskSet;
    }
}
