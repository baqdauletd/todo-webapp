package com.bakd.java_todo_webapp.controller;

import com.bakd.java_todo_webapp.models.Category;
import com.bakd.java_todo_webapp.models.Person;
import com.bakd.java_todo_webapp.models.Priority;
import com.bakd.java_todo_webapp.models.TodoItem;
import com.bakd.java_todo_webapp.security.PersonDetails;
import com.bakd.java_todo_webapp.services.PersonDetailsService;
import com.bakd.java_todo_webapp.services.TodoItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class TodoFormController {
    @Autowired
    private TodoItemService todoItemService;

    @Autowired
    private PersonDetailsService personDetailsService;

    @GetMapping("/create-todo")
    public String showCreateForm(TodoItem todoItem, Model model) {
        model.addAttribute("categories", todoItemService.getAllCategories() );
        model.addAttribute("priorities", Priority.values());
        return "new-todo-item";
    }

    @PostMapping("/todo")
    public String createTodoItem(@Valid TodoItem todoItem, BindingResult result, Model model, @RequestParam String newCategory) {
        if(!newCategory.isEmpty()){
            Category category = new Category();
            category.setName(newCategory);
            todoItem.setCategory(todoItemService.saveCategory(category));
        }

        TodoItem item = new TodoItem();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Optional<Person> personOptional = personDetailsService.findByUsername(personDetails.getUsername());

        if(personOptional.isEmpty()){
            return "redirect:/login";
        }

        Person person = personOptional.get();
        todoItem.setPerson(person);

//        todoItemService.getTimeRemaining(item);
//        item.setDescription(todoItem.getDescription());
//        item.setIsComplete(todoItem.getIsComplete());
//        item.setDeadline(todoItem.getDeadline());
//        item.setPersonID(todoItem.getId());

        todoItemService.save(todoItem);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id, Model model){
        TodoItem todoItem = todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));

        todoItemService.delete(todoItem);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public  String showUpdateForm(@PathVariable("id") Long id, Model model){
        TodoItem todoItem = todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));

        model.addAttribute("todo", todoItem);
        model.addAttribute("categories", todoItemService.getAllCategories() );
        model.addAttribute("priorities", Priority.values());
        return "edit-todo-item";
    }

    @PostMapping("/todo/{id}")
    public String updateTodoItem(@PathVariable("id") Long id, @Valid TodoItem todoItem, BindingResult result, Model model, @RequestParam String newCategory){
        TodoItem item = todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));

        if(!newCategory.isEmpty()){
            Category category = new Category();
            category.setName(newCategory);
            todoItem.setCategory(todoItemService.saveCategory(category));
        } else {
            item.setCategory(todoItem.getCategory());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Optional<Person> personOptional = personDetailsService.findByUsername(personDetails.getUsername());

        if(personOptional.isEmpty()){
            return "redirect:/login";
        }

        Person person = personOptional.get();
        todoItem.setPerson(person);

//        item.setIsComplete(todoItem.getIsComplete());
//        item.setDescription(todoItem.getDescription());
//        item.setDeadline(todoItem.getDeadline());
        todoItemService.save(todoItem);
        return "redirect:/";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());

        return "redirect:/";
    }


}
