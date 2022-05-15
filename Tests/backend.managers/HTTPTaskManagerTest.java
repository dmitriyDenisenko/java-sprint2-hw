package backend.managers;

import backend.api.KVServer;
import backend.api.KVTaskClient;
import backend.tasks.Epic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
public class HTTPTaskManagerTest extends TaskManagerTest<HTTPTaskManager>{
    private KVServer server;

    @BeforeEach
    public void createManager() throws IOException, InterruptedException {
        super.manager = (HTTPTaskManager) Managers.getDefault();
        server.start();
    }

    @AfterEach
    public void stopServer(){
        server.stop();
    }

}
