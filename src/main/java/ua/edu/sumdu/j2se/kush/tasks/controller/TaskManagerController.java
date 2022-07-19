package ua.edu.sumdu.j2se.kush.tasks.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.sumdu.j2se.kush.tasks.model.Task;
import ua.edu.sumdu.j2se.kush.tasks.model.TaskManagerModel;
import ua.edu.sumdu.j2se.kush.tasks.utils.TaskIO;
import ua.edu.sumdu.j2se.kush.tasks.utils.Tasks;
import ua.edu.sumdu.j2se.kush.tasks.view.TaskManagerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static ua.edu.sumdu.j2se.kush.tasks.controller.Action.*;

public class TaskManagerController {
    private final TaskManagerView VIEW;
    private final TaskManagerModel MODEL;
    private final List<Action> MAIN_PAGE_ACTION_LIST;
    private final List<Action> ADD_TASK_PAGE_ACTION_LIST;
    private static final Logger LOGGER =
            LogManager.getLogger(TaskManagerController.class);

    {
        MAIN_PAGE_ACTION_LIST = new ArrayList<Action>() {{
            add(SHOW_TASK_LIST);
            add(ADD_TASK);
            add(EDIT_TASK);
            add(REMOVE_TASK);
            add(SHOW_CALENDAR);
            add(EXIT);
        }};

        ADD_TASK_PAGE_ACTION_LIST = new ArrayList<Action>() {{
            add(MAIN_MENU);
            add(ADD_NON_REPEATING_TASK);
            add(ADD_REPEATING_TASK);
        }};
    }

    public TaskManagerController(TaskManagerView VIEW, TaskManagerModel MODEL) {
        this.VIEW = VIEW;
        this.MODEL = MODEL;
    }

    public void process() {
        LOGGER.debug("Invoke method process()");
        LOGGER.info("Start TaskManager");
        while (true) {
            VIEW.displayActionHeader(MAIN_MENU);
            VIEW.displayActions(MAIN_PAGE_ACTION_LIST);
            switch (VIEW.requestUserToChooseAction(MAIN_PAGE_ACTION_LIST)) {
                case ADD_TASK:
                    addTaskHandler();
                    break;
                case SHOW_TASK_LIST:
                    showTaskListHandler();
                    break;
                case REMOVE_TASK:
                    removeTaskHandler();
                    break;
                case EDIT_TASK:
                    editTaskHandler();
                    break;
                case SHOW_CALENDAR:
                    showCalendarHandler();
                    break;
                case EXIT:
                    exit();
            }
        }
    }

    private void showCalendarHandler() {
        LOGGER.debug("Invoke method showCalendarHandler()");
        VIEW.displayActionHeader(SHOW_CALENDAR);
        VIEW.displayCalendar(Tasks.calendar(MODEL.getTaskList(),
                VIEW.requestUserToEnterDateTime("Enter start date"),
                VIEW.requestUserToEnterDateTime("Enter end date")));
    }

    private void editTaskHandler() {
        LOGGER.debug("Invoke method editTaskHandler()");
        VIEW.displayActionHeader(EDIT_TASK);
        if (MODEL.getTaskList().size() == 0) {
            System.out.println("\nThere's nothing to edit\n");
            return;
        }
        VIEW.displayTaskList(MODEL.getTaskList());
        int taskNumber;
        while (true) {
            taskNumber = VIEW.requestUserToEnterNumber("Enter the number of"
                    + " the task to be edited: ");
            int finalTaskNumber = taskNumber;
            if (IntStream.range(1, MODEL.getTaskList().size() + 1)
                    .noneMatch(n -> n == finalTaskNumber)) {
                System.out.println("Invalid task number!");
                continue;
            }
            break;
        }
        Task task = MODEL.getTaskList().getTask(taskNumber - 1);
        VIEW.displayActionHeader(EDIT_TASK);
        VIEW.displayTaskDetails(task);
    }

    private void removeTaskHandler() {
        LOGGER.debug("Invoke method removeTaskHandler()");
        VIEW.displayActionHeader(REMOVE_TASK);
        if (MODEL.getTaskList().size() == 0) {
            System.out.println("\nThere's nothing to remove\n");
            return;
        }
        VIEW.displayTaskList(MODEL.getTaskList());
        int taskNumber;
        while (true) {
            taskNumber = VIEW.requestUserToEnterNumber("Enter the number of "
                    + "the task to be removed: ");
            int finalTaskNumber = taskNumber;
            if (IntStream.range(1, MODEL.getTaskList().size() + 1)
                    .noneMatch(n -> n == finalTaskNumber)) {
                System.out.println("Invalid task number!");
                continue;
            }
            break;
        }
        if ("y".equalsIgnoreCase(VIEW.requestUserToEnterString("To confirm " +
                "deleting the task, enter 'y', otherwise 'n': "))) {
            MODEL.removeTask(MODEL.getTaskList().getTask(taskNumber - 1));
            System.out.println("Task deleted!");
        }
    }

    private void showTaskListHandler() {
        LOGGER.debug("Invoke method showTaskListHandler()");
        VIEW.displayActionHeader(SHOW_TASK_LIST);
        if (MODEL.getTaskList().size() == 0) {
            System.out.println("\nThere's nothing to show\n");
            return;
        }
        VIEW.displayTaskList(MODEL.getTaskList());
    }

    private void addTaskHandler() {
        LOGGER.debug("Invoke method addTaskHandler()");
        VIEW.displayActionHeader(ADD_TASK);
        VIEW.displayActions(ADD_TASK_PAGE_ACTION_LIST);
        switch (VIEW.requestUserToChooseAction(ADD_TASK_PAGE_ACTION_LIST)) {
            case MAIN_MENU:
                break;
            case ADD_NON_REPEATING_TASK:
                createNonRepeatingTaskHandler();
                break;
            case ADD_REPEATING_TASK:
                createRepeatingTaskHandler();
                break;
        }
        System.out.println("Task created!");
    }

    private void createNonRepeatingTaskHandler() {
        LOGGER.debug("Invoke method createNonRepeatingTaskHandler()");
        while (true) {
            try {
                MODEL.createNonRepeatingTask(
                        VIEW.requestUserToEnterString("Enter task title: "),
                        VIEW.requestUserToEnterDateTime("Enter task start time"));
                break;
            } catch (IllegalArgumentException e) {
                displayErrorAndTryAgain(e.getMessage());
            }
        }
    }

    private void createRepeatingTaskHandler() {
        LOGGER.debug("Invoke method createRepeatingTaskHandler()");
        while (true) {
            try {
                MODEL.createRepeatingTask(
                        VIEW.requestUserToEnterString("Enter task title: "),
                        VIEW.requestUserToEnterDateTime("Enter task start time"),
                        VIEW.requestUserToEnterDateTime("Enter task end time"),
                        VIEW.requestUserToEnterNumber("Enter task repeating interval: "));
                break;
            } catch (IllegalArgumentException e) {
                displayErrorAndTryAgain(e.getMessage());
            }
        }
    }

    private void displayErrorAndTryAgain(String error) {
        System.out.println();
        System.out.println(">>> " + error);
        System.out.println("    Try again.");
        System.out.println();
    }

    private void exit() {
        System.exit(0);
    }
}
