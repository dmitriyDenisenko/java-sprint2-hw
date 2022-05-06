package backend.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task{
    private String name;
    private String description;
    private StatusTask status;
    private final int index;
    private TypeTask type = TypeTask.TASK;
    private Duration duration;
    private LocalDateTime startTime;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


    public Task(String name, String description, int index) {
        this.name = name;
        this.description = description;
        this.status = StatusTask.NEW;
        this.index = index;
    }

    public Task(String name, String description, int index, long durationInMinutes, String starTime){
        this.name = name;
        this.description = description;
        this.status = StatusTask.NEW;
        this.index = index;
        this.duration = Duration.ofMinutes(durationInMinutes);
        this.startTime = LocalDateTime.parse(starTime, formatter);
    }

    public int getIndex() {
        return index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
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


    public String getName() {
        return name;
    }

    protected void setType(TypeTask type){
        this.type = type;
    }

    protected TypeTask getType(){
        return type;
    }

    public String getEndTime(){
        if(startTime != null && duration != null){
            return startTime.plus(duration).format(formatter);
        } else {
            throw new Error("Error! Task start time and duration not set");
        }

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
        StringBuilder string = new StringBuilder(index + "," + type + "," + name + "," + status + "," + description);
        if(duration != null && startTime != null){
            string.append("," + startTime.format(formatter) + "," + duration + "," + getEndTime());
        }
        return  string.toString();
    }

    protected String getDescription() {
        return description;
    }
}
