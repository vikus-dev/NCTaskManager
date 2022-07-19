package ua.edu.sumdu.j2se.kush.tasks.notifications;

import ua.edu.sumdu.j2se.kush.tasks.model.Task;

import java.util.Set;

public interface Notifier {
    void notify(Set<Task> taskSet);
}
