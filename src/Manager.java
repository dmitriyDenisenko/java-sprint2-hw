import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Manager {
    static HashMap<Integer, Task> tasks = new HashMap<>();
    static HashMap<Integer, Epic> epics = new HashMap<>();
    static HashMap<Integer, Subtask> Allsubtasks = new HashMap<>();
    static Scanner input = new Scanner(System.in);
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        System.out.println("Привет! Я трекер задач ;)");
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
                    System.out.println("Введите индефекатор эпика");
                    int id = input.nextInt();
                    if (epics.containsKey(id)) {
                        showAllSubtasks(id);
                    } else {
                        System.out.println("Такого эпика нет.");
                    }
                    break;
                case 4:
                    System.out.println("Введите индефекатор задачи");
                    int index = input.nextInt();
                    printTaskAllType(index);
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
                    System.out.println("До встречи ;)");
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
        switch (command) {
            case 1:
                Task task = new Task();
                addNewTask(task);
                return;
            case 2:
                Epic epic = new Epic();
                addNewEpic(epic);
                break;
            case 3:
                System.out.println("Введите индефекатор эпика");
                int id = input.nextInt();
                if (epics.containsKey(id)) {
                    Epic mainEpic = epics.get(id);
                    Subtask subtask = new Subtask(mainEpic);
                    addNewSubtask(subtask);
                } else {
                    System.out.println("Такого эпика не существует, для начала создайте его.");
                }
                break;
            default:
                System.out.println("Не верная команда.");
                return;
        }
    }

    public static void addNewTask(Task task) {
        System.out.println("Введите название задачи");
        task.inputSetName();
        System.out.println("Введите описание");
        task.inputSetDescription();
        tasks.put(task.generateIndex(), task);
        System.out.println("Индефекатор - " + task.index);
    }

    public static void addNewEpic(Epic epic) {
        System.out.println("Введите название Эпика");
        epic.inputSetName();
        System.out.println("Введите описание");
        epic.inputSetDescription();
        epics.put(epic.generateIndex(), epic);
        System.out.println("Индефекатор - " + epic.index);
    }

    public static void addNewSubtask(Subtask subtask) {
        System.out.println("Введите название Подзадачи");
        subtask.inputSetName();
        System.out.println("Введите описание");
        subtask.inputSetDescription();
        Allsubtasks.put(subtask.hashCode(), subtask);
        System.out.println("Индефекатор - " + subtask.index);
    }

    public static void showAllTasks() {
        for (int id : tasks.keySet()) {
            Task task = tasks.get(id);
            System.out.println("Название: " + task.getName() + "; ID: " + id + "; Status: " + task.getStatus());
        }
    }

    public static void showAllEpics() {
        for (int id : epics.keySet()) {
            Epic epic = epics.get(id);
            System.out.println("Название: " + epic.getName() + "; ID: " + id + "; Status: " + epic.getStatus());
        }
    }

    public static void showAllSubtasks(int id) {
        Epic epic = epics.get(id);
        ArrayList<Subtask> subtasks = epic.getSubtasks();
        if (!subtasks.isEmpty()) {
            for (int i = 0; i < subtasks.size(); i++) {
                Subtask subtask = subtasks.get(i);
                System.out.println("Название: " + subtask.name + "; id: " + subtask.hashCode() + "; status: "
                        + subtask.getStatus());
            }
        } else {
            System.out.println("У этого эпика нет подзадач");
        }

    }

    public static void printTaskAllType(int id) {
        if (tasks.containsKey(id)) {
            Task task = tasks.get(id);
            System.out.println("Название: " + task.getName() + "; ID: " + id + "; Status: " + task.getStatus()
                    + "; Описание:" + task.getDescription());
        } else if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            System.out.println("Название: " + epic.getName() + "; ID: " + id + "; Status: " + epic.getStatus()
                    + "; Описание:" + epic.getDescription());
        } else if (Allsubtasks.containsKey(id)) {
            Subtask subtask = Allsubtasks.get(id);
            System.out.println("Название: " + subtask.name + "; id: " + subtask.hashCode() + "; status: "
                    + subtask.getStatus() + "; Описание:" + subtask.getDescription());
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    public static void showMenuForUpdate() {
        System.out.println("Введите индефекатор задачи");
        int id = input.nextInt();
        if (tasks.containsKey(id)) {
            Task task = tasks.get(id);
            updateTask(task, id);
        } else if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            updateEpic(epic);
        } else if (Allsubtasks.containsKey(id)) {
            Subtask subtask = Allsubtasks.get(id);
            updateSubtask(subtask, id);
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    public static void updateTask(Task task, int id) {
        System.out.println("Введите новое название");
        task.inputSetName();
        System.out.println("Введите новое описание");
        task.inputSetDescription();
        System.out.println("Выбирите статус задачи:");
        System.out.println("1- IN_PROGRESS");
        System.out.println("2- DONE");
        String status;
        while (true) {
            int choose = input.nextInt();
            if (choose == 1) {
                status = "IN_PROGRESS";
                task.setStatus(status);
                break;
            } else if (choose == 2) {
                status = "DONE";
                task.setStatus(status);
                break;
            } else {
                System.out.println("Не верная команда, введите правильную команду");
            }
        }
    }

    public static void updateEpic(Epic epic) {
        System.out.println("Введите новое название");
        epic.inputSetName();
        System.out.println("Введите новое описание");
        epic.inputSetDescription();
    }

    public static void updateSubtask(Subtask subtask, int id) {
        System.out.println("Введите новое название");
        subtask.inputSetName();
        System.out.println("Введите новое описание");
        subtask.inputSetDescription();
        System.out.println("Выбирите статус задачи:");
        System.out.println("1- IN_PROGRESS");
        System.out.println("2- DONE");
        String status;
        while (true) {
            int choose = input.nextInt();
            if (choose == 1) {
                status = "IN_PROGRESS";
                subtask.setStatus(status);
                break;
            } else if (choose == 2) {
                status = "DONE";
                subtask.setStatus(status);
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
                tasks.clear();
                epics.clear();
                Allsubtasks.clear();
                break;
            } else if (command == 2) {
                System.out.println("Введите индефекатор задачи");
                int id = input.nextInt();
                removeByIndex(id);
                break;
            } else {
                System.out.println("Такой команды не существует");
            }
        }

    }

    public static void removeByIndex(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            ArrayList<Subtask> subtasks = epic.getSubtasks();
            for (int i = 0; i < subtasks.size(); i++) {
                Allsubtasks.remove(subtasks.get(i).index);
            }
            subtasks.clear();
            epic.setSubtasks(subtasks);
            epics.remove(id);
        } else if (Allsubtasks.containsKey(id)) {
            Subtask subtask = Allsubtasks.get(id);
            Epic epic = subtask.mainEpic;
            ArrayList<Subtask> subtasks = epic.getSubtasks();
            subtasks.remove(id);
            epic.setSubtasks(subtasks);
            Allsubtasks.remove(id);
        } else {
            System.out.println("Такой задачи нет");
        }
    }
}
