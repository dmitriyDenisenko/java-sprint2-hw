
import backend.managers.Managers;
import backend.managers.TaskManager;
import backend.tasks.Epic;
import backend.tasks.Subtask;
import backend.tasks.Task;

import java.util.List;
import java.util.Map;

public class ConsoleApp {
    static TaskManager manager = Managers.getDefault();
    static int serialNumber = 0;

    public static void main(String[] args) {
        addTaskOrEpic(1, "Первая задача", "Купить доллары");
        addTaskOrEpic(1, "Вторая задача", "Купить евро");
        addTaskOrEpic(2, "Первый Эпик", "Список продуктов");
        addSubtask(2, "Первая подзадача", "Молоко");
        addSubtask(2, "Вторая подзадача", "Хлеб");
        addSubtask(2, "Третья подзадача", "Гречка");
        addTaskOrEpic(2, "Второй (пустой) Эпик", "Устроиться в яндекс");
        printTaskAllType(6);
        printTaskAllType(2);
        printTaskAllType(1);
        printTaskAllType(3);
        printTaskAllType(4);
        printTaskAllType(5);
        printTaskAllType(0);
        printTaskAllType(4);
        printTaskAllType(2);
        printTaskAllType(0);
        printTaskAllType(3);
        printTaskAllType(5);
        printTaskAllType(6);
        printTaskAllType(1);
        System.out.println("-------------------------");
        printHistory();
        System.out.println("-------------------------");
        System.out.println("Удаляем задачу с индексом 0");
        removeById(0);
        System.out.println("-------------------------");
        printHistory();
        System.out.println("-------------------------");
        System.out.println("Удаляем эпик с 3 подзадачами");
        removeById(2);
        System.out.println("-------------------------");
        printHistory();
        System.out.println("-------------------------");
        manager.addTask(new Task("asdf", "asdf", 999));
        removeById(999);
        manager.removeAll();
        printHistory();
    }

    public static void addTaskOrEpic(int command, String nameTask, String descriptionTask) {
        String name = nameTask;
        String description = descriptionTask;
        switch (command) {
            case 1:
                Task task = new Task(name, description, getForAddSerialNumber());
                System.out.println("Индефекатор: " + task.getIndex() + "; Название: " + name + "; Описание: "
                        + descriptionTask);
                manager.addTask(task);
                break;
            case 2:
                Epic epic = new Epic(name, description, getForAddSerialNumber());
                System.out.println("Индефекатор: " + epic.getIndex() + "; Название: " + name + "; Описание: "
                        + descriptionTask);
                manager.addEpic(epic);
                break;
        }
    }

    public static void addSubtask(int id, String nameTask, String descriptionTask) {
        String name = nameTask;
        String description = descriptionTask;
        Map<Integer, Epic> epics = manager.getEpics();
        Epic mainEpic = epics.get(id);
        Subtask subtask = new Subtask(mainEpic, name, description, getForAddSerialNumber());
        System.out.println("Индефекатор: " + subtask.getIndex() + "; Название: " + name + "; Описание: " + descriptionTask);
        manager.addSubtask(subtask, id);
    }

    public static void printTaskAllType(int id) {
        Task task = manager.getAllTypeTaskById(id);
    }

    public static void removeById(int id) {
        manager.removeByIndex(id);

    }

    public static int getForAddSerialNumber() {
        return serialNumber++;
    }

    public static void printHistory() {
        List<Task> allHistory = manager.history();
        for (Task task : allHistory) {
            System.out.println("Название: " + task.getName() + "; ID: " + task.getIndex() + "; Status: " + task.getStatus());
        }
    }
}
