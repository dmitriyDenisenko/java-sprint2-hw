package backend;

import backend.allTypesTasks.Epic;
import backend.allTypesTasks.Subtask;
import backend.allTypesTasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> AllSubtasks = new HashMap<>();
    private int serialNumber = 0;

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, Subtask> getAllSubtasks() {
        return AllSubtasks;
    }

    public ArrayList<Subtask> getSubtasksEpic(Epic epic) {
        return epic.getSubtasks();
    }

    public int getForAddSerialNumber() {
        return serialNumber++;
    }


    public void addTask(Task task) {
        tasks.put(task.getIndex(), task);
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getIndex(), epic);
    }

    public void addSubtask(Subtask subtask) {
        AllSubtasks.put(subtask.getIndex(), subtask);
    }

    public void updateTask(Task task) {
        int id = task.getIndex();
        tasks.put(id, task);
    }

    public void updateEpic(Epic epic) {
        int id = epic.getIndex();
        epics.put(id, epic);
    }

    public void updateSubtask(Subtask subtask) {
        int id = subtask.getIndex();
        tasks.put(id, subtask);
    }

    public void removeByIndex(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            ArrayList<Subtask> subtasks = epic.getSubtasks();
            for (int i = 0; i < subtasks.size(); i++) {
                AllSubtasks.remove(subtasks.get(i).getIndex());
            }
            subtasks.clear();
            epics.remove(id);
        } else if (AllSubtasks.containsKey(id)) {
            Subtask subtask = AllSubtasks.get(id);
            Epic epic = subtask.getMainEpic();
            ArrayList<Subtask> subtasks = epic.getSubtasks();
            subtasks.remove(subtask);
            epic.setSubtasks(subtasks);
            AllSubtasks.remove(id);
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    public void removeAll() {
        tasks.clear();
        epics.clear();
        AllSubtasks.clear();
    }
}
