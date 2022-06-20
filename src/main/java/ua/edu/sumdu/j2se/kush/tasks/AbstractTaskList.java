package ua.edu.sumdu.j2se.kush.tasks;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * AbstractTaskList
 *
 * @see ArrayTaskList
 * @see LinkedTaskList
 * @see ListTypes
 * @author <a href="mailto:vitaly.kush@gmail.com">Vitalii Kush</a>
 */
public abstract class AbstractTaskList
        implements Iterable<Task>, Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractTaskList)) return false;
        AbstractTaskList obj = (AbstractTaskList) o;
        if (this.size != obj.size) return false;

        Iterator<Task> itr1 = iterator();
        Iterator<Task> itr2 = obj.iterator();
        while (itr1.hasNext()) {
            if (!Objects.equals(itr1.next(), itr2.next())) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + size;
        for (Task task : this) {
            result = 31 * result + (task == null ? 0 : task.hashCode());
        }
        return result;
    }

    public abstract Stream<Task> getStream();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Iterator<Task> itr = iterator(); itr.hasNext(); ) {
            sb.append(itr.next().toString());
            sb.append(itr.hasNext() ? ", " : "");
        }
        sb.append("]");
        return sb.toString();
    }
}
