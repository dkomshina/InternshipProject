package sberbank.internship.dkomshina.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class StopTaskEvent extends ApplicationEvent {

    private Long taskId;

    public StopTaskEvent(Object source, Long taskId) {
        super(source);
        this.taskId = taskId;
    }
}
