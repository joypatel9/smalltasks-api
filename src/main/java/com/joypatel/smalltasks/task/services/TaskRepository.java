package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.task.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query(value = "SELECT t.* FROM t_task t WHERE t.id < :beyondId AND t.origin_pincode = :pincode AND t.creator_id <> :currentUserId AND t.status = :status ORDER BY t.id DESC LIMIT :itemCount", nativeQuery = true)
    List<Task> findPreviousTasks(Integer pincode, Integer currentUserId, int beyondId, String status, int itemCount);

    @Query(value = "SELECT t.* FROM t_task t WHERE t.id > :beyondId AND t.origin_pincode = :pincode AND t.creator_id <> :currentUserId AND t.status = :status ORDER BY t.id LIMIT :itemCount", nativeQuery = true)
    List<Task> findNextTasks(Integer pincode, Integer currentUserId, int beyondId, String status, int itemCount);

}
