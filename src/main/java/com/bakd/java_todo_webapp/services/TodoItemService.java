package com.bakd.java_todo_webapp.services;

import com.bakd.java_todo_webapp.models.Person;
import com.bakd.java_todo_webapp.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.bakd.java_todo_webapp.models.TodoItem;
import com.bakd.java_todo_webapp.repositories.TodoItemRepository;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class TodoItemService {
    @Autowired
    private TodoItemRepository todoItemRepository;
    private PeopleRepository peopleRepository;

    @Autowired
    private PersonDetailsService personDetailsService;

//    public Iterable<TodoItem> getAll(){
//        return todoItemRepository.findAll();
//    }

    public List<TodoItem> getForAuthenticated(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Person> person = personDetailsService.findByUsername(userDetails.getUsername());
        return todoItemRepository.findByPerson(person);
    }

    public Optional<TodoItem> getById(Long id){
        return todoItemRepository.findById(id);
    }

    public TodoItem save(TodoItem todoItem){
//        if(todoItem.getId() == null){
//            todoItem.setCreatedAt(Instant.now());
//        }

        //todoItem.setUpdatedAt(Instant.now());
        //todoItem.setDeadline(todoItem.getDeadline());
        return todoItemRepository.save(todoItem);
    }


    public void delete(TodoItem todoItem){
        todoItemRepository.delete(todoItem);
    }
}
