package backend.tasks;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private StatusTask status;
    private final int index;
    private TypeTask type = TypeTask.TASK;

    public Task(String name, String description, int index) {
        this.name = name;
        this.description = description;
        this.status = StatusTask.NEW;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    public StatusTask getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    protected void setType(TypeTask type){
        this.type = type;
    }

    protected TypeTask getType(){
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description)
                && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status);
    }


    @Override
    public String toString(){
        return index + "," + type + "," + name + "," + status + "," + description;
    }
}
