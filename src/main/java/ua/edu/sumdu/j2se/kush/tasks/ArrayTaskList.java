package ua.edu.sumdu.j2se.kush.tasks;


public class ArrayTaskList {

    /**
     * An array to store added tasks.<br> Has a {@link #DEFAULT_CAPACITY}.
     */
    private Task[] taskList;

    /**
     * Default ArrayTaskList capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * The size of the ArrayTaskList (the number of elements it contains).
     */
    private int size;

    /**
     * The class constructor with {@link #DEFAULT_CAPACITY}.
     */
    public ArrayTaskList() {
        taskList = new Task[DEFAULT_CAPACITY];
    }

    /**
     * The class constructor that defines the capacity.
     *
     * @param initialCapacity this list capacity
     */
    public ArrayTaskList(int initialCapacity) {
        if (initialCapacity > 0) {
            taskList = new Task[initialCapacity];
        } else if (initialCapacity == 0) {
            taskList = new Task[]{};
        } else {
            throw new NegativeArraySizeException("The initial capacity of "
                    + "the task list cannot be a negative number.");
        }
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Returns a task from {@link #taskList} at specified position of this
     * list.
     *
     * @param index of element to return
     * @return the task at specified position of this list
     */
    public Task getTask(int index) {
        return taskList[index];
    }

    /**
     * Appends a task to the end of this list.
     *
     * @param task task to be added to this list.
     */
    public void add(Task task) {
        if (task != null) {
            ensureCapacity(size + 1);
            taskList[size++] = task;
        }
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity - taskList.length > 0) {
            grow(minCapacity);
        }
    }

    /**
     * Increases the capacity of the {@link #taskList}.
     *
     * @param minCapacity the minimum required capacity of this list.
     */
    private void grow(int minCapacity) {
        int newCapacity = minCapacity + (minCapacity / 2);
        Task[] tmp = new Task[newCapacity];
        if (size > 0) {
            System.arraycopy(taskList, 0, tmp, 0, size);
        }
        taskList = tmp;
    }

    /**
     * Truncates the {@link #taskList} to size taskList.size() * 1.5
     */
    private void trimToSize() {
        Task[] tmp = new Task[size];
        System.arraycopy(taskList, 0, tmp, 0, size);
        taskList = tmp;
    }

    /**
     * Removes specified task from this list and returns true if the task is in
     * this list, otherwise returns false.
     *
     * @param task task to be removed from this list, if present.
     * @return true if the task has been removed from this list.
     */
    public boolean remove(Task task) {
        int index = indexOf(task);

        if (index < 0) {
            return false;
        }

        int numberOfTasksToShift = size - index - 1;

        if (numberOfTasksToShift > 0) {
            System.arraycopy(taskList, index + 1, taskList,
                    index, numberOfTasksToShift);
        }

        taskList[--size] = null;

        // Make sure the taskList array is not too large
        // after deleting the task.
        if (taskList.length - size > (size >> 1)) {
            trimToSize();
        }

        return true;
    }

    /**
     * Returns the index of the first occurrence of the specified task, if the
     * task is in this list, otherwise -1.
     *
     * @param task task
     * @return index of the task or -1.
     */
    public int indexOf(Task task) {
        if (task == null) {
            return -1;
        }

        for (int i = 0; i < size; i++) {
            if (task.getTitle().equalsIgnoreCase(taskList[i].getTitle())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns a filtered list of tasks that contains tasks that must run at
     * least once in the specified time interval.
     *
     * @param from the start time.
     * @param to   the end time.
     * @return an ArrayTaskList object containing tasks matching the time
     *         filter.
     */
    public ArrayTaskList incoming(int from, int to) {
        ArrayTaskList filteredList = new ArrayTaskList();

        for (int i = 0; i < size; i++) {
            int nextTime = taskList[i].nextTimeAfter(from);

            if (from < nextTime && nextTime <= to) {
                filteredList.add(taskList[i]);
            }
        }

        return filteredList;
    }
}
