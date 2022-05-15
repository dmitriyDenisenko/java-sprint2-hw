package backend.managers;

import backend.api.KVTaskClient;
import backend.exceptions.ManagerSaveException;
import backend.tasks.Epic;
import backend.tasks.Subtask;
import backend.tasks.Task;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class HTTPTaskManager extends FileBackedTasksManager{
    private KVTaskClient client;
    private Gson gson;
    public HTTPTaskManager(String url) throws IOException, InterruptedException {
        super(url);
        gson = new Gson();
        client = new KVTaskClient(url);
    }

    private void save() throws IOException, InterruptedException {
        client.put("Tasks",gson.toJson(super.getTasks()));
        client.put("Subtask", gson.toJson(super.getAllSubtasks()));
        client.put("Epics", gson.toJson(super.getEpics()));
        client.put("History", gson.toJson(super.history()));
    }
}
