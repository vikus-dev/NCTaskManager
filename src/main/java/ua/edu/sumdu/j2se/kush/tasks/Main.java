package ua.edu.sumdu.j2se.kush.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.edu.sumdu.j2se.kush.tasks.controller.TaskManagerController;
import ua.edu.sumdu.j2se.kush.tasks.model.*;
import ua.edu.sumdu.j2se.kush.tasks.notifications.NotificationManager;
import ua.edu.sumdu.j2se.kush.tasks.notifications.Notifier;
import ua.edu.sumdu.j2se.kush.tasks.notifications.ScreenNotifier;
import ua.edu.sumdu.j2se.kush.tasks.utils.JsonFileStorage;
import ua.edu.sumdu.j2se.kush.tasks.utils.Storage;
import ua.edu.sumdu.j2se.kush.tasks.view.TaskManagerView;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Task Manager is running.");
        Storage jsonFileStorage = new JsonFileStorage("db/json/tasks.json");
        TaskManagerModel model = new TaskManagerModel(jsonFileStorage);
        Notifier screenNotifier = new ScreenNotifier();
        List<Notifier> notifiers = new ArrayList<>();
        notifiers.add(screenNotifier);
        NotificationManager notificationManager = new NotificationManager(
                model, notifiers);
        TaskManagerController controller = new TaskManagerController(
                new TaskManagerView(), model);
        notificationManager.start();
        controller.process();
    }
}
