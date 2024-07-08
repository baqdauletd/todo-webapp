package com.bakd.java_todo_webapp.repositories;

import com.bakd.java_todo_webapp.models.Person;
import com.bakd.java_todo_webapp.models.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface TodoItemRepository extends JpaRepository<TodoItem, Long>{
    List<TodoItem> findByPerson(Optional<Person> person);
}
