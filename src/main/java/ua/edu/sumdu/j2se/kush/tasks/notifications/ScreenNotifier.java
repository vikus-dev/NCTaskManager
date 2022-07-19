package ua.edu.sumdu.j2se.kush.tasks.notifications;

import ua.edu.sumdu.j2se.kush.tasks.model.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class ScreenNotifier implements Notifier {

    public ScreenNotifier() {
    }

    @Override
    public void notify(Set<Task> taskSet) {
        final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd.MM.yyyy HH:mm");
        System.out.println();
        taskSet.forEach(task -> {
            System.out.println(LocalDateTime.now().format(formatter)
                    + ": time to do - " + task.getTitle());
        });
    }
}
