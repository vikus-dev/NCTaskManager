package ua.edu.sumdu.j2se.kush.tasks;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A list of tasks based on an array.
 */
public class ArrayTaskList extends AbstractTaskList implements Cloneable {

    /**
     * An array to store added tasks.
     */
    private Task[] taskList;

    /**
     * This constructor is to create an empty list.
     */
    public ArrayTaskList() {
        taskList = new Task[]{};
    }

    /**
     * This constructor specifies the initial capacity.
     *
     * @param initialCapacity this list capacity
     */
    public ArrayTaskList(int initialCapacity) {
        if (initialCapacity > 0) {
            taskList = new Task[initialCapacity];
        } else if (initialCapacity == 0) {
            taskList = new Task[]{};
        } else {
            throw new NegativeArraySizeException("Capacity must be a "
                    + "positive number.");
        }
    }

    @Override
    public ListTypes.types getListType() {
        return ListTypes.types.ARRAY;
    }

    @Override
    public Task getTask(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + ", size: " + size);
        }
        return taskList[index];
    }

    @Override
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
     * Truncates the {@link #taskList} to size taskList.size()
     */
    private void trimToSize() {
        Task[] tmp = new Task[size];
        System.arraycopy(taskList, 0, tmp, 0, size);
        taskList = tmp;
    }

    @Override
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
            if (task.equals(taskList[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            private int cursor;
            private int lastRet = -1;

            @Override
            public boolean hasNext() {
                return cursor != size;
            }

            @Override
            public Task next() {
                int i = cursor;
                if (cursor >= size) {
                    throw new NoSuchElementException();
                }
                Task[] tasks = ArrayTaskList.this.taskList;
                cursor = i + 1;
                return tasks[lastRet = i];
            }

            @Override
            public void remove() {
                if (lastRet < 0) {
                    throw new IllegalStateException(
                            "Method remove() should be called after next()");
                }

                ArrayTaskList.this.remove(ArrayTaskList.this.taskList[lastRet]);
                cursor = lastRet;
                lastRet = -1;
            }
        };
    }

    @Override
    public ArrayTaskList clone() {
        try {
            ArrayTaskList clone = (ArrayTaskList) super.clone();
            clone.taskList = new Task[size];
            clone.size = 0;
            for (Task task : this) {
                clone.add(task.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "ArrayTaskList" + super.toString();
    }
}
