package ua.edu.sumdu.j2se.kush.tasks;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        Task t1 = new Task("task 1", LocalDateTime.now().plusDays(1));
        Task t2 = new Task("task 2", LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3).plusMinutes(180),
                3600);
        Task t3 = new Task("task 3", LocalDateTime.now().minusDays(7));
        t2.setActive(true);
        AbstractTaskList taskList = new LinkedTaskList();
        taskList.add(t1);
        taskList.add(t2);
        taskList.add(t3);

        // Test LinkedTaskList serialization
        System.out.print("Serialization of list type " + taskList.getListType()
                + "... ");
        try {
            FileOutputStream fos = new FileOutputStream("testfile.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(taskList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("done.");
        System.out.print("Deserialization of list type "
                + taskList.getListType() + "... ");
        AbstractTaskList list = null;

        try {
            FileInputStream fis = new FileInputStream("testfile.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (AbstractTaskList) ois.readObject();
            System.out.println("done\nResult of deserialization: ");
            System.out.println("\t" + list);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
