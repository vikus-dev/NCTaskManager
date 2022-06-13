package ua.edu.sumdu.j2se.kush.tasks;

import java.util.*;
import java.util.stream.Stream;

/**
 * A list of tasks based on a linked list.
 */
public class LinkedTaskList extends AbstractTaskList implements Cloneable {

    /**
     * The first element of this list.
     */
    private Node first;

    /**
     * The last element of this list
     */
    private Node last;

    @Override
    public ListTypes.types getListType() {
        return ListTypes.types.LINKED;
    }

    @Override
    public Task getTask(int index) {
        return node(index).element;
    }

    /**
     * Returns node at specified position.
     *
     * @param index position of the node.
     * @return node at specified position.
     */
    private Node node(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + ", size: " + size);
        }
        Node x;
        if (index < size / 2) {
            x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
        }
        return x;
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("The task cannot be null.");
        }

        Node x = new Node(task);

        if (first == null) {
            first = x;
            last = first;
        } else {
            x.prev = last == first ? first : last;
            last.next = x;
            last = x;
        }
        size++;
    }

    @Override
    public boolean remove(Task task) {
        if (size == 0 || task == null) {
            return false;
        }

        Node x = first;
        while (x != null) {
            if (x.element.equals(task)) {
                unlink(x);
                return true;
            }
            x = x.next;
        }

        return false;
    }

    /**
     * Removes (unbinds) the specified node from this list.
     *
     * @param x node to unlink.
     */
    private void unlink(Node x) {
        final Node next = x.next;
        final Node prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        size--;
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            int cursor;
            Node next = first;
            Node lastReturned;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public Task next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                lastReturned = next;
                next = next.next;
                cursor++;
                return lastReturned.element;
            }

            @Override
            public void remove() {
                if (lastReturned == null) {
                    throw new IllegalStateException(
                            "Method remove() should be called after next()");
                }
                unlink(lastReturned);
                lastReturned = null;
                cursor--;
            }
        };
    }

    @Override
    public LinkedTaskList clone() {
        try {
            LinkedTaskList clone = (LinkedTaskList) super.clone();
            clone.size = 0;
            clone.first = clone.last = null;
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
        List<Task> list = new ArrayList<>();
        for (Task task : this) {
            list.add(task);
        }
        return list.stream();
    }

    @Override
    public String toString() {
        return "LinkedTaskList" + super.toString();
    }

    private static class Node {
        private final Task element;
        private Node next;
        private Node prev;

        Node(Task element) {
            this.element = element;
        }
    }
}
