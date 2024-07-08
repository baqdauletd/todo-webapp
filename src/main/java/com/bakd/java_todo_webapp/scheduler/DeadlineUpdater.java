package com.bakd.java_todo_webapp.scheduler;

import com.bakd.java_todo_webapp.models.TodoItem;
import com.bakd.java_todo_webapp.services.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class DeadlineUpdater {
    @Autowired
    private TodoItemService todoItemService;

    @Scheduled(fixedRate = 60000)
    public void updateDeadlines(){
        Iterable<TodoItem> todoItems = todoItemService.getForAuthenticated();

//        for (TodoItem item : todoItems){
//            Duration timeRemaining = item.getTimeRemaining();
//            boolean deadlinePassed = item.isDeadlinePassed();
//
////            System.out.println();
//        }
    }
}
