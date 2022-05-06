package backend.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Formatter;

public class TaskComparator implements Comparator<Task> {
    @Override
    public int compare(Task first, Task second){
        if(first.toString().length() > second.toString().length()){
            return 1;
        } else if(first.toString().length() < second.toString().length()){
            return -1;
        } else{
            LocalDateTime firstTime = first.getStartTime();
            LocalDateTime secondTime = second.getStartTime();
            if(firstTime.isAfter(secondTime)){
                return -1;
            } else if(firstTime.isBefore(secondTime)){
                return 1;
            } else {
                return 0;
            }
        }
    }
}
