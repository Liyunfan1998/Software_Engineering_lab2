package lab3; /**
 * Created by luke1998 on 2018/4/15.
 */

import java.time.LocalDateTime;

public class ToDo_item implements Comparable<ToDo_item>, Cloneable {
    LocalDateTime startTime;
    LocalDateTime endTime;
    String things2Do;

    public ToDo_item(LocalDateTime startTime, LocalDateTime endTime, String things2Do) {
        if (startTime == null || endTime == null || things2Do == null) {
            System.out.println("[ERROR] Null !\nAutomatic generate !");
            this.setStartTime(LocalDateTime.now());
            this.setEndTime(LocalDateTime.now());
            this.things2Do = "[ERROR] Null !\nAutomatic generate !";
            return;
        }
        if (startTime.isAfter(endTime)) {
            System.out.println("[Warning] startTime should be before endTime !");
        }
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.things2Do = things2Do;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getThings2Do() {
        return things2Do;
    }

    public void setThings2Do(String things2Do) {
        this.things2Do = things2Do;
    }

    @Override
    public int compareTo(ToDo_item o) {
        return this.getStartTime().compareTo(o.getStartTime());
    }

    @Override
    public ToDo_item clone() {
        ToDo_item toDoItem = new ToDo_item(this.getStartTime(), this.getEndTime(), this.getThings2Do());
        return toDoItem;
    }

    public boolean isEqual(ToDo_item toDo_item) {
        return this.getStartTime().isEqual(toDo_item.getStartTime())
                && this.getEndTime().isEqual(toDo_item.getEndTime())
                && this.getThings2Do().equalsIgnoreCase(toDo_item.getThings2Do());
    }

    @Override
    public boolean equals(Object o) {
        ToDo_item toDo_item = (ToDo_item) o;
        return this.getStartTime().isEqual(toDo_item.getStartTime())
                && this.getEndTime().isEqual(toDo_item.getEndTime())
                && this.getThings2Do().equalsIgnoreCase(toDo_item.getThings2Do());
    }
}
