package ua.edu.sumdu.j2se.kush.tasks;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A list of tasks based on a linked list.
 */
public class LinkedTaskList extends AbstractTaskList {

    /**
     * The first element of this list.
     */
    private Node head;

    /**
     * The last element of this list
     */
    private Node tail;

    @Override
    public ListTypes.types getListType() {
        return ListTypes.types.LINKED;
    }

    @Override
    public Task getTask(int index) {
        return node(index).data;
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
            x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
                i++;
            }
        } else {
            x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
                i--;
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

        if (head == null) {
            head = x;
            tail = head;
        } else {
            x.prev = tail == head ? head : tail;
            tail.next = x;
            tail = x;
        }
        size++;
    }

    @Override
    public boolean remove(Task task) {
        if (size == 0 || task == null) {
            return false;
        }

        Node x = head;
        while (x != null) {
            if (x.data.equals(task)) {
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
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            tail = prev;
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
            Node next = head;
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
                return lastReturned.data;
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
    
    private static class Node {
        private final Task data;
        private Node next;
        private Node prev;

        Node(Task data) {
            this.data = data;
        }
    }
}
