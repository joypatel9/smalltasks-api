package com.joypatel.smalltasks.task;

import com.joypatel.smalltasks.task.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestTaskRepository extends JpaRepository<Task, Integer> {
}
