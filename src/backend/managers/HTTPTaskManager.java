package backend.managers;

import backend.api.KVTaskClient;
import com.google.gson.Gson;

import java.io.IOException;

public class HTTPTaskManager extends FileBackedTasksManager {
    private KVTaskClient client;
    private Gson gson;

    public HTTPTaskManager(String url) throws IOException, InterruptedException {
        super(url);
        gson = new Gson();
        client = new KVTaskClient(url);
    }

    @Override
    protected void save() {
        try {
            client.put("Tasks", gson.toJson(super.getTasks()));
            client.put("Subtask", gson.toJson(super.getAllSubtasks()));
            client.put("Epics", gson.toJson(super.getEpics()));
            client.put("History", gson.toJson(super.history()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
