package ua.edu.sumdu.j2se.kush.tasks;

/**
 * A list of tasks based on a linked list.
 */
public class LinkedTaskList extends AbstractTaskList {

    /**
     * The first element of this list.
     *
     */
    private Node head;

    @Override
    public ListTypes.types getListType() {
        return ListTypes.types.LINKED;
    }

    @Override
    public Task getTask(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + ", size: " + size);
        }

        Node node = head;
        int idx = 0;

        while (idx != index) {
            node = node.next;
            idx++;
        }

        return node.data;
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("The task must be non-null.");
        }

        Node newNode = new Node(task);

        if (head != null) {
            newNode.next = head;
        }

        head = newNode;
        size++;
    }

    @Override
    public boolean remove(Task task) {
        if (size == 0 || task == null) {
            return false;
        }

        Node currentNode = head;
        Node previousNode = null;

        while (currentNode != null) {
            if (currentNode.data.equals(task)) {
                if (currentNode == head) {
                    head = currentNode.next;
                } else {
                    previousNode.next = currentNode.next;
                }
                size--;
                return true;
            }
            previousNode = currentNode;
            currentNode = currentNode.next;
        }

        return false;
    }

    private static class Node {
        private final Task data;
        private Node next;

        Node(Task data) {
            this.data = data;
        }
    }
}
