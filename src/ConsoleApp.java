import backend.Managers;
import backend.TaskManager;
import backend.tasks.Epic;
import backend.tasks.StatusTask;
import backend.tasks.Subtask;
import backend.tasks.Task;
import backend.InMemoryTaskManager;

import java.util.HashMap;
import java.util.Scanner;

public class ConsoleApp {
    static Scanner input = new Scanner(System.in);
    static TaskManager manager = Managers.getDefault();
    static int serialNumber = 0;

    public static void main(String[] args) {
        System.out.println("Привет! Я консольная версия трекера задач :)");
        while (true) {
            printMenu();
            int command = input.nextInt();
            switch (command) {
                case 1:
                    showAllTasks();
                    break;
                case 2:
                    showAllEpics();
                    break;
                case 3:
                    showAllSubtasks();
                    break;
                case 4:
                    printTaskAllType();
                    break;
                case 5:
                    showMenuForAdding();
                    break;
                case 6:
                    showMenuForUpdate();
                    break;
                case 7:
                    showDeleteMenu();
                    break;
                case 0:
                    System.out.println("Лондон гудбай...");
                    return;
                default:
                    System.out.println("Такой команды нет");
            }

        }

    }

    public static void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1- Получение списка всех задач");
        System.out.println("2- Получение списка всех эпиков");
        System.out.println("3- Получение списка всех подзадач эпика");
        System.out.println("4- Получение задачи по идентификатору");
        System.out.println("5- Добавление новой задачи, эпика или подзадачи");
        System.out.println("6- Обновление задачи любого типа");
        System.out.println("7- Удаление задачи");
        System.out.println("0- Выйти");
    }

    public static void showMenuForAdding() {
        System.out.println("Что вы хотите добавить?");
        System.out.println("1- Задача");
        System.out.println("2- Эпик");
        System.out.println("3- Подзадача");
        int command = input.nextInt();
        input.nextLine();
        switch (command) {
            case 1:
                AddTaskOrEpic(command);
                return;
            case 2:
                AddTaskOrEpic(command);
                break;
            case 3:
                System.out.println("Введите индефекатор эпика");
                int id = input.nextInt();
                input.nextLine();
                HashMap<Integer, Epic> epics = manager.getEpics();
                if (epics.containsKey(id)) {
                    AddSubtask(id);
                } else {
                    System.out.println("Такого эпика не существует, для начала создайте его.");
                }
                break;
            default:
                System.out.println("Не верная команда.");
                return;
        }
    }

    public static void AddTaskOrEpic(int command) {
        System.out.println("Введите название задачи");
        String name = input.nextLine();
        System.out.println("Введите описание");
        String description = input.nextLine();
        switch (command) {
            case 1:
                Task task = new Task(name, description, getForAddSerialNumber());
                System.out.println("Индефекатор - " + task.getIndex());
                manager.addTask(task);
                break;
            case 2:
                Epic epic = new Epic(name, description, getForAddSerialNumber());
                System.out.println("Индефекатор - " + epic.getIndex());
                manager.addEpic(epic);
                break;
        }
    }

    public static void AddSubtask(int id) {
        System.out.println("Введите название задачи");
        String name = input.nextLine();
        System.out.println("Введите описание");
        String description = input.nextLine();
        HashMap<Integer, Epic> epics = manager.getEpics();
        Epic mainEpic = epics.get(id);
        Subtask subtask = new Subtask(mainEpic, name, description, getForAddSerialNumber());
        System.out.println("Индефекатор - " + subtask.getIndex());
        manager.addSubtask(subtask, id);
    }

    public static void showAllTasks() {
        HashMap<Integer, Task> tasks = manager.getTasks();
        for (int id : tasks.keySet()) {
            Task task = tasks.get(id);
            System.out.println("Название: " + task.getName() + "; ID: " + id + "; Status: " + task.getStatus());
        }
    }

    public static void showAllEpics() {
        HashMap<Integer, Epic> epics = manager.getEpics();
        for (int id : epics.keySet()) {
            Epic epic = epics.get(id);
            System.out.println("Название: " + epic.getName() + "; ID: " + id + "; Status: " + epic.getStatus());
        }
    }

    public static void showAllSubtasks() {
        System.out.println("Введите индефекатор эпика");
        int id = input.nextInt();
        HashMap<Integer, Epic> epics = manager.getEpics();
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            HashMap<Integer, Subtask> subtasks = manager.getSubtasksEpic(epic);
            if (!subtasks.isEmpty()) {
                for (Subtask subtask: subtasks.values()) {
                    System.out.println("Название: " + subtask.getName() + "; id: " + subtask.getIndex() + "; status: "
                            + subtask.getStatus() + "; mainEpic " + subtask.getMainEpic().getName());
                }
            } else {
                System.out.println("У этого эпика нет подзадач");
            }
        } else {
            System.out.println("Такого эпика не существует.");
        }
    }

    public static void printTaskAllType() {
        System.out.println("Введите индефекатор задачи");
        int id = input.nextInt();
        Task task = manager.getAllTypeTaskById(id);
        System.out.println("Название: " + task.getName() + "; ID: " + id + "; Status: " + task.getStatus());
    }

    public static void showMenuForUpdate() {
        System.out.println("Введите индефекатор задачи");
        int id = input.nextInt();
        input.nextLine();
        HashMap<Integer, Task> tasks = manager.getTasks();
        HashMap<Integer, Epic> epics = manager.getEpics();
        HashMap<Integer, Subtask> AllSubtasks = manager.getAllSubtasks();
        if (tasks.containsKey(id)) {
            Task task = tasks.get(id);
            addUpdateTask(task, task.getIndex());
        } else if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            addUpdateEpic(epic, epic.getIndex());
        } else if (AllSubtasks.containsKey(id)) {
            Subtask subtask = AllSubtasks.get(id);
            addUpdateSubtask(subtask, subtask.getIndex());
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    public static void addUpdateTask(Task task, int id) {
        System.out.println("Введите новое название");
        task.setName(input.nextLine());
        System.out.println("Введите новое описание");
        task.setDescription(input.nextLine());
        System.out.println("Выбирите статус задачи:");
        System.out.println("1- IN_PROGRESS");
        System.out.println("2- DONE");
        String status;
        while (true) {
            int choose = input.nextInt();
            if (choose == 1) {
                task.setStatus(StatusTask.IN_PROGRESS);
                manager.updateTask(task, id);
                break;
            } else if (choose == 2) {
                task.setStatus(StatusTask.DONE);
                manager.updateTask(task, id);
                break;
            } else {
                System.out.println("Не верная команда, введите правильную команду");
            }
        }
    }

    public static void addUpdateEpic(Epic epic, int id) {
        System.out.println("Введите новое название");
        epic.setName(input.nextLine());
        System.out.println("Введите новое описание");
        epic.setDescription((input.nextLine()));
        manager.updateEpic(epic, id);
    }

    public static void addUpdateSubtask(Subtask subtask, int id) {
        System.out.println("Введите новое название");
        subtask.setName(input.nextLine());
        System.out.println("Введите новое описание");
        subtask.setDescription(input.nextLine());
        System.out.println("Выбирите статус задачи:");
        System.out.println("1- IN_PROGRESS");
        System.out.println("2- DONE");
        while (true) {
            int choose = input.nextInt();
            if (choose == 1) {
                subtask.setStatus(StatusTask.IN_PROGRESS);
                manager.updateSubtask(subtask, id);
                break;
            } else if (choose == 2){
                subtask.setStatus(StatusTask.DONE);
                manager.updateSubtask(subtask,id);
                break;
            } else {
                System.out.println("Не верная команда, введите правильную команду");
            }
        }
    }

    public static void showDeleteMenu() {
        while (true) {
            System.out.println("Выбирите тип удаления:");
            System.out.println("1- Удалить всё");
            System.out.println("2- Удаление по индификатору");
            int command = input.nextInt();
            if (command == 1) {
                manager.removeAll();
                break;
            } else if (command == 2) {
                System.out.println("Введите индефекатор задачи");
                int id = input.nextInt();
                manager.removeByIndex(id);
                break;
            } else {
                System.out.println("Такой команды не существует");
            }
        }

    }
    public static int getForAddSerialNumber() {
        return serialNumber++;
    }
}
