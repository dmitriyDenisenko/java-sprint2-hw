package backend.managers;

import backend.tasks.Epic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager>{
    @BeforeEach
    public void createManager(){
        super.manager = Managers.getDefaultFileBackedManager();
    }

    @Test
    public void shouldBeEmptyWhenSaveEmptyAndLoadEmptyTasks(){
        manager.removeAll();
        FileBackedTasksManager load = FileBackedTasksManager.loadFromFile(
                new File("oldHistory.txt"));
        assertTrue( load.getTasks().isEmpty() && load.getEpics().isEmpty());
    }

    @Test
    public void shouldBeOneEpicWithoutSubtaskInLoadFile(){
        manager.removeAll();
        Epic universal = new Epic("Name", "desc", 1);
        manager.addEpic(universal);
        FileBackedTasksManager load = FileBackedTasksManager.loadFromFile(
                new File("oldHistory.txt"));
        assertEquals(universal, load.getAllTypeTaskById(1));
    }


    @Test
    public void shouldBeEmptyWhenSaveEmptyAndLoadEmptyHistory(){
        manager.removeAll();
        FileBackedTasksManager load = FileBackedTasksManager.loadFromFile(
                new File("oldHistory.txt"));
        assertEquals(0, load.history().size());
    }



}