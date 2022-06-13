package ua.edu.sumdu.j2se.kush.tasks;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * A list of tasks based on an array.
 */
public class ArrayTaskList extends AbstractTaskList implements Cloneable {

    /**
     * An array to store added tasks.
     */
    private Task[] list;

    /**
     * This constructor is to create an empty list.
     */
    public ArrayTaskList() {
        list = new Task[]{};
    }

    /**
     * This constructor specifies the initial capacity.
     *
     * @param initialCapacity this list capacity
     */
    public ArrayTaskList(int initialCapacity) {
        if (initialCapacity > 0) {
            list = new Task[initialCapacity];
        } else if (initialCapacity == 0) {
            list = new Task[]{};
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
        return list[index];
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            ensureCapacity(size + 1);
            list[size++] = task;
        }
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity - list.length > 0) {
            grow(minCapacity);
        }
    }

    /**
     * Increases the capacity of the {@link #list}.
     *
     * @param minCapacity the minimum required capacity of this list.
     */
    private void grow(int minCapacity) {
        int newCapacity = minCapacity + (minCapacity / 2);
        Task[] tmp = new Task[newCapacity];
        if (size > 0) {
            System.arraycopy(list, 0, tmp, 0, size);
        }
        list = tmp;
    }

    /**
     * Truncates the {@link #list} to size taskList.size()
     */
    private void trimToSize() {
        Task[] tmp = new Task[size];
        System.arraycopy(list, 0, tmp, 0, size);
        list = tmp;
    }

    @Override
    public boolean remove(Task task) {
        int index = indexOf(task);

        if (index < 0) {
            return false;
        }

        int numberOfTasksToShift = size - index - 1;

        if (numberOfTasksToShift > 0) {
            System.arraycopy(list, index + 1, list,
                    index, numberOfTasksToShift);
        }

        list[--size] = null;

        // Make sure the taskList array is not too large
        // after deleting the task.
        if (list.length - size > (size >> 1)) {
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
            if (task.equals(list[i])) {
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
                Task[] tasks = ArrayTaskList.this.list;
                cursor = i + 1;
                return tasks[lastRet = i];
            }

            @Override
            public void remove() {
                if (lastRet < 0) {
                    throw new IllegalStateException(
                            "Method remove() should be called after next()");
                }

                ArrayTaskList.this.remove(ArrayTaskList.this.list[lastRet]);
                cursor = lastRet;
                lastRet = -1;
            }
        };
    }

    @Override
    public ArrayTaskList clone() {
        try {
            ArrayTaskList clone = (ArrayTaskList) super.clone();
            clone.list = new Task[size];
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
    public Stream<Task> getStream() {
        return Arrays.stream(this.list);
    }

    @Override
    public String toString() {
        return "ArrayTaskList" + super.toString();
    }
}
