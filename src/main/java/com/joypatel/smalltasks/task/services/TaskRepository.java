package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.task.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
