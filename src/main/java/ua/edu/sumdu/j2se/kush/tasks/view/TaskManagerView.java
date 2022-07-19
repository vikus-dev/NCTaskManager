package ua.edu.sumdu.j2se.kush.tasks.view;

import ua.edu.sumdu.j2se.kush.tasks.controller.Action;
import ua.edu.sumdu.j2se.kush.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.kush.tasks.model.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class TaskManagerView {
    private final String dateFormat = "dd.MM.yyyy";
    //    private final String timeFormat = "H:mm";
    private final String dateTimeFormat = dateFormat + " H:mm";
    private final DateTimeFormatter dateTimeFormatter;
    private final DateTimeFormatter dateFormatter;


    public TaskManagerView() {
        dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
        dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    }

    private String getInputString() {
        String inputString = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            inputString = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputString;
    }

    private int getInputNumber() {
        return Integer.parseInt(getInputString());
    }

    public void displayActionHeader(Action action) {
        System.out.println();
        System.out.println(action.title);
    }

    public void displayActions(List<Action> actionList) {
        for (int i = 0; i < actionList.size(); i++) {
            System.out.println(i + ". " + actionList.get(i).title);
        }
    }

    /**
     * Outputs a character string of the specified length.
     *
     * @param ch    the character to be printed.
     * @param width number of characters to be printed.
     */
    public void printLine(char ch, int width) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < width; i++) sb.append(ch);
        System.out.println(sb.toString());
    }

    /**
     * Prompts the user to enter a date and time in the specified format.
     *
     * @param prompt prompt string
     * @return dateTime
     */
    public LocalDateTime requestUserToEnterDateTime(String prompt) {
        String promptStr = prompt + " in format '" + dateTimeFormat + "': ";
        LocalDateTime value;
        while (true) {
            try {
                value = LocalDateTime.parse(requestUserToEnterString(promptStr),
                        dateTimeFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input! Please try again.");
                continue;
            }
            break;
        }
        return value;
    }

    public String requestUserToEnterString(String prompt) {

        String value;
        while (true) {
            System.out.print(prompt);
            value = getInputString();
            if (value.isEmpty()) {
                System.out.println("Invalid input! Cannot be empty.");
                continue;
            }
            break;
        }
        return value;
    }

    public int requestUserToEnterNumber(String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = getInputNumber();
            } catch (NumberFormatException e) {
                System.out.println("Invalid number! Please try again.");
                continue;
            }
            break;
        }
        return value;
    }

    public Action requestUserToChooseAction(List<Action> actionList) {
        int value;
        while (true) {
            value = requestUserToEnterNumber("Enter an action number from the "
                    + "list: ");
            if (0 > value || value > actionList.size() - 1) {
                System.out.println("Wrong action! Please try again.");
                continue;
            }
            break;
        }
        return actionList.get(value);
    }

    public void displayTaskList(AbstractTaskList taskList) {
        for (int i = 0; i < taskList.size(); i++) {
            System.out.print((i + 1) + ".  ");
            displayTask(taskList.getTask(i));
        }
    }

    public void displayTask(Task task) {
        System.out.print(task.getTitle());
        if (!task.isRepeated()) {
            System.out.print(", at " + task.getTime().format(dateTimeFormatter));
        } else {
            int hours = task.getRepeatInterval() / 60;
            int minutes = task.getRepeatInterval() % 60;
            System.out.print(", from "
                    + task.getStartTime().format(dateTimeFormatter) + " to "
                    + task.getEndTime().format(dateTimeFormatter) + ", every ");
            if (hours > 0) {
                System.out.print(hours + " hour(s)");
                if (minutes > 0) {
                    System.out.print(" and " + minutes + " minute(s)");
                }
            } else {
                System.out.print(minutes + " minute(s)");
            }
        }
        System.out.print(", " + (task.isActive() ? "active" : "inactive"));
        if (task.nextTimeAfter(LocalDateTime.now()) != null) {
            System.out.println(", next time " + task.nextTimeAfter(
                    LocalDateTime.now()).format(dateTimeFormatter));
        } else {
            System.out.println(", expired");
        }
    }

    public void displayTaskDetails(Task task) {
        String format = "%-15s";
        System.out.printf(format, "title:");
        System.out.println(task.getTitle());
        System.out.printf(format, "time:");
        System.out.println(task.getTime().format(dateTimeFormatter));
        System.out.printf(format, "startTime:");
        System.out.println(task.getStartTime().format(dateTimeFormatter));
        System.out.printf(format, "endTime:");
        System.out.println(task.getEndTime().format(dateTimeFormatter));
        System.out.printf(format, "isRepeating:");
        System.out.println(task.isRepeated());
        System.out.printf(format, "isActive:");
        System.out.println(task.isActive());
    }

    public void displayCalendar(SortedMap<LocalDateTime, Set<Task>> map) {
        System.out.println("");
        for (Map.Entry<LocalDateTime, Set<Task>> entry : map.entrySet()) {
            System.out.println(entry.getKey().format(dateTimeFormatter));
            for (Task t : entry.getValue()) {
                System.out.println("  > " + t.getTitle());
            }
        }
    }
}
