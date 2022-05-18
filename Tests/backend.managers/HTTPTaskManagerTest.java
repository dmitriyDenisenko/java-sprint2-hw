package backend.managers;

import backend.api.KVServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class HTTPTaskManagerTest extends TaskManagerTest<HTTPTaskManager> {
    private KVServer server;

    @BeforeEach
    public void createManager() throws IOException, InterruptedException {
        super.manager = (HTTPTaskManager) Managers.getDefault();
        server.start();
    }

    @AfterEach
    public void stopServer() {
        server.stop();
    }

}
