package ua.edu.sumdu.j2se.kush.tasks;

/**
 * AbstractTaskList
 * @see ArrayTaskList
 * @see LinkedTaskList
 * @see ListTypes
 */
public abstract class AbstractTaskList {

    /**
     * The size of this list (the number of elements it contains).
     */
    protected int size;

    /**
     * Returns the type of this list.
     *
     * @return listType the type of the list.
     */
    protected abstract ListTypes.types getListType();

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Returns a task at specified position of this list.
     *
     * @param index of element to return
     * @return the task at specified position of this list.
     */
    public abstract Task getTask(int index);

    /**
     * Appends a task to this list.
     *
     * @param task task to be added to this list.
     */
    public abstract void add(Task task);

    /**
     * Removes specified task from this list and returns true if the task is in
     * this list, otherwise returns false.
     *
     * @param task task to be removed from this list, if present.
     * @return true if the task has been removed from this list.
     */
    public abstract boolean remove(Task task);

    public AbstractTaskList incoming(int from, int to) {
        AbstractTaskList list = TaskListFactory.createTaskList(getListType());
        for (int i = 0; i < size; i++) {
            Task task = this.getTask(i);
            int nextTime = task.nextTimeAfter(from);
            if (from < nextTime && nextTime < to) {
                list.add(task);
            }
        }

        return list;
    }
}
