package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.task.entities.Task;
import com.joypatel.smalltasks.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    
    @Query(value = "FROM Task t WHERE t.originPincode = :pincode AND t.creator <> :currentUser AND t.status = :status")
    List<Task> findTasks(Integer pincode, User currentUser, Task.Status status);
}
