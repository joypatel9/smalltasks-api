package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.task.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

interface TaskRepository extends JpaRepository<Task, Integer>, QuerydslPredicateExecutor<Task> {

}
