package backend.managers;

import backend.exceptions.ManagerSaveException;
import backend.tasks.Epic;
import backend.tasks.Subtask;
import backend.tasks.Task;
import backend.tasks.TypeTask;

import javax.swing.filechooser.FileView;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager{
    private int sizeOfTasks = 0;
    private int sizeOfEpics = 0;
    private int getSizeOfSubtasks = 0;

    static public void main(String[] args) throws IOException {
        FileBackedTasksManager m = new FileBackedTasksManager();
        Epic epic = new Epic("Купить машину","Полный перечень", 1);
        m.addEpic(epic);
        m.addTask(new Task("Устроиться на работу","Нужно пройти весь яндекс Практикум", 2));
        Subtask sub = new Subtask(epic, "Накопить денег", "опять же устроившись на работу", 3);
        m.addSubtask(sub,1);
        m.getAllTypeTaskById(3);
        FileBackedTasksManager newManager = FileBackedTasksManager.loadFromFile(new File("oldHistory.txt"));
        System.out.println(newManager.getAllTypeTaskById(1));


    }


    @Override
    public void addTask(Task task){
        super.addTask(task);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addEpic(Epic epic){
        super.addEpic(epic);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSubtask(Subtask subtask, int idEpic) {
        super.addSubtask(subtask,idEpic);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTask(Task task, int id){
        super.updateTask(task, id);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEpic(Epic epic, int id){
        super.updateEpic(epic,id);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSubtask(Subtask subtask, int id){
        super.updateSubtask(subtask,id);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeByIndex(int id) {
        super.removeByIndex(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAll(){
        super.removeAll();
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task getAllTypeTaskById(int id){
        Task task = super.getAllTypeTaskById(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
        return task;
    }

    private void save() throws ManagerSaveException {
            try{
                Writer fileWriter = new FileWriter("oldHistory.txt");
                fileWriter.write("id,type,name,status,des,epic\n");
                for(Task task : super.getTasks().values()){
                    fileWriter.write(task.toString());
                    fileWriter.write("\n");
                }
                for (Epic epic : super.getEpics().values()){
                    fileWriter.write(epic.toString());
                    fileWriter.write("\n");
                    HashMap<Integer, Subtask> subtasks = epic.getSubtasks();
                    if(!subtasks.isEmpty()){
                        for(Subtask subtask : subtasks.values()){
                            fileWriter.write(subtask.toString());
                            fileWriter.write("\n");
                        }
                    }
                }
                if(!super.history().isEmpty()){
                    fileWriter.write("\n");
                    for(Task task : super.history()){
                        fileWriter.write(String.valueOf(task.getIndex()));
                    }
                }
                fileWriter.close();
            } catch (IOException e) {
                throw new ManagerSaveException();
            }

    }

    static FileBackedTasksManager loadFromFile(File file) throws IOException {
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        FileBackedTasksManager restored = new FileBackedTasksManager();
        boolean flag = false;
        while (br.ready()) {
            String line = br.readLine();
            String[] splitLine = line.split(",");
            if(splitLine.length == 6 || splitLine.length == 5){
                if(splitLine[1].equals("TASK")){
                    restored.addTask(new Task(splitLine[2], splitLine[4], Integer.parseInt(splitLine[0])));
                } else if(splitLine[1].equals("EPIC")) {
                    restored.addEpic(new Epic(splitLine[2], splitLine[4], Integer.parseInt(splitLine[0])));
                } else if(splitLine[1].equals("SUBTASK")){
                    Map<Integer, Epic> epics = restored.getEpics();
                    Epic epic = epics.get(Integer.parseInt(splitLine[5]));
                    Subtask sub =  new Subtask(epic,splitLine[2],splitLine[4],Integer.parseInt(splitLine[0]));
                    restored.addSubtask(sub,epic.getIndex());
                }
            } else if(line.isBlank()){
                flag = true;
            } else if(flag){
                for(String numb : splitLine){
                    restored.getAllTypeTaskById(Integer.parseInt(numb));
                }
            }
        }
        br.close();
        return restored;
    }
}
