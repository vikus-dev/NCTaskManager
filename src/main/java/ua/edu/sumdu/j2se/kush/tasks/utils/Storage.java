package ua.edu.sumdu.j2se.kush.tasks.utils;

import ua.edu.sumdu.j2se.kush.tasks.model.AbstractTaskList;

public interface Storage {
    /**
     * Load data
     *
     * @param taskList list for storing loaded data.
     */
    void loadData(AbstractTaskList taskList);

    /**
     * Save data.
     *
     * @param taskList list of tasks to save.
     */
    void saveData(AbstractTaskList taskList);
}
