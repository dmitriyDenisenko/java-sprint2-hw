package backend.managers;

import backend.exceptions.ManagerSaveException;
import backend.tasks.Epic;
import backend.tasks.Subtask;
import backend.tasks.Task;

import java.io.*;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {
    String path;

    public FileBackedTasksManager(String path) {
        this.path = path;
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask, int idEpic) {
        super.addSubtask(subtask, idEpic);
        save();
    }

    @Override
    public void updateTask(Task task, int id) {
        super.updateTask(task, id);
        save();
    }

    @Override
    public void updateEpic(Epic epic, int id) {
        super.updateEpic(epic, id);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask, int id) {
        super.updateSubtask(subtask, id);
        save();
    }

    @Override
    public void removeByIndex(int id) {
        super.removeByIndex(id);
        save();
    }

    @Override
    public void removeAll() {
        super.removeAll();
        save();
    }

    @Override
    public Task getAllTypeTaskById(int id) {
        Task task = super.getAllTypeTaskById(id);
        save();
        return task;
    }

    protected void save() {
        try {
            Writer fileWriter = new FileWriter(path);
            fileWriter.write("id,type,name,status,des,epic,start Time,duration,end Time\n");
            for (Task task : super.getTasks().values()) {
                fileWriter.write(task.toString());
                fileWriter.write("\n");
            }
            for (Epic epic : super.getEpics().values()) {
                fileWriter.write(epic.toString());
                fileWriter.write("\n");
                Map<Integer, Subtask> subtasks = epic.getSubtasks();
                if (!subtasks.isEmpty()) {
                    for (Subtask subtask : subtasks.values()) {
                        fileWriter.write(subtask.toString());
                        fileWriter.write("\n");
                    }
                }
            }
            if (!super.history().isEmpty()) {
                fileWriter.write("\n");
                for (Task task : super.history()) {
                    fileWriter.write(String.valueOf(task.getIndex()));
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new ManagerSaveException();
        }

    }

    static FileBackedTasksManager loadFromFile(File file) {
        FileReader reader = null;
        FileBackedTasksManager restored = new FileBackedTasksManager(
                file.getPath().substring(0, file.getPath().indexOf(".txt"))
                        + "Restored" + file.getPath().substring(file.getPath().indexOf(".txt") - 4));
        try {
            reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            boolean flag = false;
            while (br.ready()) {
                String line = br.readLine();
                String[] splitLine = line.split(",");
                if (splitLine.length == 6 || splitLine.length == 5) {
                    if (splitLine[1].equals("TASK")) {
                        restored.addTask(new Task(splitLine[2], splitLine[4], Integer.parseInt(splitLine[0])));
                    } else if (splitLine[1].equals("EPIC")) {
                        restored.addEpic(new Epic(splitLine[2], splitLine[4], Integer.parseInt(splitLine[0])));
                    } else if (splitLine[1].equals("SUBTASK")) {
                        Map<Integer, Epic> epics = restored.getEpics();
                        Epic epic = epics.get(Integer.parseInt(splitLine[5]));
                        Subtask sub = new Subtask(epic.getIndex(), splitLine[2], splitLine[4], Integer.parseInt(splitLine[0]));
                        restored.addSubtask(sub, epic.getIndex());
                    }
                } else if (line.isBlank()) {
                    flag = true;
                } else if (flag) {
                    for (String numb : splitLine) {
                        restored.getAllTypeTaskById(Integer.parseInt(numb));
                    }
                }
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return restored;
    }
}
