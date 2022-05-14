package ua.edu.sumdu.j2se.kush.tasks;

public class Main {

    public static void main(String[] args) {

        Task task1 = new Task("Task 1", 10);
        Task task2 = new Task("Task 2", 20, 50, 2);
        System.out.println(task1);
        System.out.println(task2);
    }
}
