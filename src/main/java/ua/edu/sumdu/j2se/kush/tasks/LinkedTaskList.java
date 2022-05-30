package ua.edu.sumdu.j2se.kush.tasks;


public class LinkedTaskList {

    /**
     * The first (head) element of this list.
     *
     * @see
     */
    private Node head;

    /**
     * The size of this list (the number of elements it contains).
     */
    private int size;

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Returns a task from this list at specified position.
     *
     * @param index of a task to return
     * @return the task at specified position of this list
     */
    public Task getTask(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Your index [" + index + "] is "
                    + "out of range [0 - " + (size - 1) + "]");
        }

        Node node = head;
        int idx = 0;

        while (idx != index) {
            node = node.next;
            idx++;
        }

        return node.data;
    }

    /**
     * Adds a task to the beginning of this list.
     *
     * @param task task to be added to this list.
     */
    public void add(Task task) {
        Node newNode = new Node(task);

        if (head != null) {
            newNode.next = head;
        }

        head = newNode;
        size++;
    }

    /**
     * Removes specified task from this list and returns true if the task is in
     * this list, otherwise returns false.
     *
     * @param task task to be removed from this list, if present.
     * @return true if the task has been removed from this list.
     */
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

    /**
     * Returns a filtered list of tasks that contains tasks that must run at
     * least once in the specified time interval.
     *
     * @param from the start time.
     * @param to   the end time.
     * @return an ArrayTaskList object containing tasks matching the time
     *         filter.
     */
    public LinkedTaskList incoming(int from, int to) {
        LinkedTaskList taskList = new LinkedTaskList();
        if (size == 0) {
            return taskList;
        }
        Node node = head;
        while (node != null) {
            int nextTime = node.data.nextTimeAfter(from);
            if (from < nextTime && nextTime <= to) {
                taskList.add(node.data);
            }
            node = node.next;
        }
        return taskList;
    }

    private static class Node {
        private final Task data;
        private Node next;

        Node(Task data) {
            this.data = data;
        }
    }
}
