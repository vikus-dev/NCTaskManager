package ua.edu.sumdu.j2se.kush.tasks.utils;

import ua.edu.sumdu.j2se.kush.tasks.model.AbstractTaskList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class JsonFileStorage implements Storage {
    private final File file;

    public JsonFileStorage(String filePath) {
        file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (Writer writer = new FileWriter(file)) {
                writer.write("[]");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void loadData(AbstractTaskList taskList) {
        try {
            TaskIO.readText(taskList, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveData(AbstractTaskList taskList) {
        try {
            TaskIO.writeText(taskList, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
