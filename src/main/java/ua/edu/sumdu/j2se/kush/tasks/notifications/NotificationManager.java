package ua.edu.sumdu.j2se.kush.tasks.notifications;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.sumdu.j2se.kush.tasks.model.Task;
import ua.edu.sumdu.j2se.kush.tasks.model.TaskManagerModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class NotificationManager extends Thread {
    private final Logger LOGGER =
            LogManager.getLogger(NotificationManager.class);
    private final TaskManagerModel MODEL;
    private final List<Notifier> NOTIFIERS;

    public NotificationManager(TaskManagerModel model,
                               List<Notifier> notifiers) {
        this.MODEL = model;
        this.NOTIFIERS = notifiers;
    }

    @Override
    public void run() {
        LOGGER.info("Start notification thread");

        while (true) {
            int seconds = LocalDateTime.now().getSecond();
            try {
                Thread.sleep(60000 - seconds * 1000);
            } catch (InterruptedException e) {
                LOGGER.error("Notification thread exception " + e.getMessage());
            }
            Set<Task> taskSet = MODEL.getTasksToPerform();
            if (taskSet.size() > 0) {
                LOGGER.info("Send notification.");
                for (Notifier notifier : NOTIFIERS) {
                    notifier.notify(taskSet);
                }
            }
        }
    }
}
