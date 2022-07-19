package ua.edu.sumdu.j2se.kush.tasks.controller;

public enum Action {
    MAIN_MENU("Main menu"),
    SHOW_TASK_LIST("Show my task list"),
    ADD_TASK("Add new task"),
    ADD_NON_REPEATING_TASK("Add a one-time task"),
    ADD_REPEATING_TASK("Add a recurring task"),
    EDIT_TASK("Edit task"),
    REMOVE_TASK("Remove task"),
    SHOW_CALENDAR("Show calendar"),
    EXIT("Exit");

    public String title;

    Action(String title) {
        this.title = title;
    }
}
