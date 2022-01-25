package backend;

import backend.alltypestasks.Epic;
import backend.alltypestasks.Subtask;
import backend.alltypestasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private int serialNumber = 0;

    public void addTask(Task task) {
        tasks.put(task.getIndex(), task);
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getIndex(), epic);
    }

    public void addSubtask(Subtask subtask, int idEpic) {
        if(epics.containsKey(idEpic)){
            Epic epic = epics.get(idEpic);
            epic.setSubtask(subtask);
        } else{
            System.out.println("Error. Подзадача не существует.");
        }
    }

    public void updateTask(Task task, int id) {
        if(tasks.containsKey(id)){
            tasks.put(id, task);
        }else{
            System.out.println("Error. Задачи не существует.");
        }

    }

    public void updateEpic(Epic epic, int id) {
        if(epics.containsKey(id)){
            Epic oldEpic = epics.get(id);
            epic.setSubtasks(oldEpic.getSubtasks()); //new epic now knows its list of subtasks
            updateMainEpicForSubtasks(epic); //Subtasks now know the new epic
            epics.put(id, epic);
        }else{
            System.out.println("Error. Такого эпика нет");
        }

    }

    public void updateSubtask(Subtask subtask, int id) {
        Epic epic = findEpicSubtask(id);
        if(epic != null){
            HashMap<Integer,Subtask> subtasks = epic.getSubtasks();
            Subtask oldSubtask = subtasks.get(id); // old Subtask have info about mainEpic
            subtask.setMainEpic(oldSubtask.getMainEpic()); //now new subtask have info about mainEpic
            subtasks.put(id, subtask); //replace old subtask
        }
    }

    private void updateMainEpicForSubtasks(Epic epic){
        for(Subtask subtask: epic.getSubtasks().values()){
            subtask.setMainEpic(epic);
        }
    }

    private Epic findEpicSubtask(int id){
        for(Epic epic: epics.values()){
            if(epic.getSubtasks().containsKey(id)){
                return epic;
            }
        }
        System.out.println("Error! Такой подзадачи нет.");
        return null;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, Subtask> getAllSubtasks() {
        HashMap<Integer, Subtask> allSubtasks = new HashMap<>();
        for(Epic epic: epics.values()){ //all epics
            for(Subtask subtask: epic.getSubtasks().values()){ //all subtasks in epic
                allSubtasks.put(subtask.getIndex(), subtask);
            }
        }
        return allSubtasks;
    }

    public HashMap<Integer, Subtask> getSubtasksEpic(Epic epic) {
        return epic.getSubtasks();
    }

    public int getForAddSerialNumber() {
        return serialNumber++;
    }


    public void removeByIndex(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else if (epics.containsKey(id)) {
            epics.remove(id);
        } else if (getAllSubtasks().containsKey(id)) {
            Epic epic = findEpicSubtask(id);
            HashMap<Integer, Subtask> subtasks = epic.getSubtasks();
            subtasks.remove(id);
            epic.setSubtasks(subtasks);
            epics.put(epic.getIndex(), epic);
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    public void removeAll() {
        tasks.clear();
        epics.clear();
    }
}
