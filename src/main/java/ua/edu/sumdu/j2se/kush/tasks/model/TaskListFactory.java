package ua.edu.sumdu.j2se.kush.tasks.model;

import ua.edu.sumdu.j2se.kush.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.kush.tasks.model.ArrayTaskList;
import ua.edu.sumdu.j2se.kush.tasks.model.LinkedTaskList;
import ua.edu.sumdu.j2se.kush.tasks.model.ListTypes;

public abstract class TaskListFactory {
    public static AbstractTaskList createTaskList(ListTypes.types type) {
        switch (type) {
            case ARRAY:
                return new ArrayTaskList();
            case LINKED:
                return new LinkedTaskList();
            default:
                throw new IllegalArgumentException();
        }
    }
}
