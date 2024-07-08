package com.bakd.java_todo_webapp.models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "todo_items")

public class TodoItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column
    private String description;

    @Column(nullable = false)
    private Boolean isComplete;

//    private Instant createdAt;
//
//    private Instant updatedAt;

    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personID")
    private Person person;

    public Duration getTimeRemaining(){
        return deadline != null ? Duration.between(LocalDateTime.now(), deadline) : Duration.ZERO;
    }

    public boolean isDeadlinePassed(){
        return deadline != null && LocalDateTime.now().isAfter(deadline);
    }

    @Override
    public String toString() {
        return String.format("TodoItem{id = %d, description = '%s', isComplete = '%s', deadline = '%s'}",
                id, description, isComplete, deadline);  //createdAt, updatedAt
    }
}
