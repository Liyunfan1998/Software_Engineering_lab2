/**
 * Created by luke1998 on 2018/4/15.
 */

import java.time.LocalDateTime;

public class ToDo_item {
    LocalDateTime startTime;
    LocalDateTime endTime;
    String things2Do;

    public ToDo_item(LocalDateTime startTime, LocalDateTime endTime, String things2Do) {
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
}
