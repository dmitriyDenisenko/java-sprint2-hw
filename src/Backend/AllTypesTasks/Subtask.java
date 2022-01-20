package Backend.AllTypesTasks;

import java.util.ArrayList;
import java.util.Objects;

public class Subtask extends Task {
    private Epic mainEpic;
    
    public Subtask(Epic epic, String name, String description) {
        super(name, description);
        this.mainEpic = epic;
        ArrayList<Subtask> subtasks = new ArrayList<>();
        subtasks.add(this);
        epic.setSubtasks(subtasks);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(mainEpic, subtask.mainEpic);
    }

    public Epic getMainEpic() {
        return mainEpic;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getName());
    }


}
