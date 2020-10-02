package sberbank.internship.dkomshina.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class StartTaskEvent extends ApplicationEvent {

    private Long taskId;

    public StartTaskEvent(Object source, Long taskId) {
        super(source);

        this.taskId = taskId;
    }
}
