package ua.edu.sumdu.j2se.kush.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * The TaskIO class containing methods for writing a list of tasks to a file
 * and vice versa.
 *
 * @author <a href="mailto:vitaly.kush@gmail.com">Vitalii Kush</a>
 */
public class TaskIO {

    /**
     * Writes a list of tasks to an output stream.
     *
     * @param tasks the list of tasks.
     * @param out   the output stream.
     */
    public static void write(AbstractTaskList tasks, OutputStream out) {
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeInt(tasks.size());
            for (Task t : tasks) {
                oos.writeInt(t.getTitle().length());
                oos.writeUTF(t.getTitle());
                oos.writeBoolean(t.isActive());
                oos.writeInt(t.getRepeatInterval());
                if (t.isRepeated()) {
                    oos.writeLong(localDateTimeToMillis(t.getStartTime()));
                    oos.writeLong(localDateTimeToMillis(t.getEndTime()));
                } else {
                    oos.writeLong(localDateTimeToMillis(t.getTime()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads tasks from the input stream and writes them to the list.
     *
     * @param tasks the list of tasks.
     * @param in    the input stream.
     */
    public static void read(AbstractTaskList tasks, InputStream in) {
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            Task t;
            int size = ois.readInt();
            for (int i = 0; i < size; i++) {
                int titleSize = ois.readInt();
                String title = ois.readUTF();
                boolean isActive = ois.readBoolean();
                int repeatInterval = ois.readInt();
                if (repeatInterval > 0) {
                    LocalDateTime start = millisToLocalDateTime(ois.readLong());
                    LocalDateTime end = millisToLocalDateTime(ois.readLong());
                    t = new Task(title, start, end, repeatInterval);
                } else {
                    LocalDateTime time = millisToLocalDateTime(ois.readLong());
                    t = new Task(title, time);
                }
                t.setActive(isActive);
                tasks.add(t);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes tasks from the task list to the specified file.
     *
     * @param tasks the list of tasks.
     * @param file  the file to write binary stream.
     * @throws IOException
     */
    public static void writeBinary(AbstractTaskList tasks, File file)
            throws IOException {

        try (OutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(file))) {
            write(tasks, outputStream);
        }
    }

    /**
     * Reads binary stream from the file and writes tasks to the list.
     *
     * @param tasks the list to write tasks.
     * @param file  the binary file to read.
     * @throws IOException
     */
    public static void readBinary(AbstractTaskList tasks, File file)
            throws IOException {
        try (InputStream inputStream = new BufferedInputStream(
                new FileInputStream(file))) {
            read(tasks, inputStream);
        }
    }

    /**
     * Writes the list of tasks into the text output in JSON format.
     *
     * @param tasks the list of tasks.
     * @param out   the output.
     */
    public static void write(AbstractTaskList tasks, Writer out) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Task[] taskList = new Task[tasks.size()];
        int i = 0;
        for (Task task : tasks) {
            taskList[i++] = task;
        }
        try (BufferedWriter writer = new BufferedWriter(out)) {
            writer.write(gson.toJson(taskList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads JSON from input and writes it to the specified task list.
     * @param tasks the task list.
     * @param in the input to read.
     */
    public static void read(AbstractTaskList tasks, Reader in) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        try (BufferedReader reader = new BufferedReader(in)) {
            Task[] taskList = gson.fromJson(reader.readLine(), new TypeToken<Task[]>() {
            }.getType());
            for (Task task : taskList) {
                tasks.add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes task list as JSON to file.
     *
     * @param tasks the task list.
     * @param file  the file to store JSON.
     * @throws IOException
     */
    public static void writeText(AbstractTaskList tasks, File file)
            throws IOException {

        try (FileWriter writer = new FileWriter(file)) {
            write(tasks, writer);
        }
    }

    /**
     * Reads JSON from a file and writes it to the specified list of tasks.
     *
     * @param tasks the list of tasks.
     * @param file  the file containing JSON.
     * @throws IOException
     */
    public static void readText(AbstractTaskList tasks, File file)
            throws IOException {

        read(tasks, new FileReader(file));
    }

    /**
     * Returns LocalDateTime from milliseconds.
     *
     * @param millis milliseconds
     * @return LocalDateTime
     */
    private static LocalDateTime millisToLocalDateTime(long millis) {
        return Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Returns milliseconds from LocalDateTime.
     *
     * @param localDateTime DateTime.
     * @return milliseconds.
     */
    private static long localDateTimeToMillis(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli();
    }
}
