package backend.managers;

import org.junit.jupiter.api.BeforeEach;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{
    @BeforeEach
    public void createManager(){
        super.manager = (InMemoryTaskManager) Managers.getDefault();
    }

}
