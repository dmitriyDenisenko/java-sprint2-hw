package backend.managers;

import backend.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    private HistoryManager manager;
    @BeforeEach
    public void createHistoryManager(){
        manager = Managers.getDefaultHistory();
    }

    @Test
    public void shouldBeEmptyList(){
        assertTrue(manager.getHistory().isEmpty());
    }

    @Test
    public void shouldBeOneTaskWhenAddTwoCloneTasks(){
        Task cloneTask = new Task("Clone Task", "des", 1);
        manager.add(cloneTask);
        manager.add(cloneTask);
        assertEquals(1,manager.getHistory().size());
    }

    @Test
    public void shouldBe2TaskAfterRemoveBeginningEndMiddle(){
        manager.add(new Task("First Task", "des", 1));
        manager.add(new Task("Second Task", "des", 2));
        manager.add(new Task("Third Task", "des", 3));
        manager.add(new Task("Fourth Task", "des", 4));
        manager.add(new Task("Fifth Task", "des", 5));
        manager.remove(1);
        manager.remove(3);
        manager.remove(5);
        assertEquals(2, manager.getHistory().size());
    }


}