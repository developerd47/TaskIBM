package com.task.taskapp.repository;

import com.task.taskapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
//Our repository for 'Task' Model.
public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findByDueDateBefore(Date dueDate);

    //Write custom query to find task having due date is today and tomorrow.
    @Query("SELECT t FROM  Task t WHERE status='PENDING' and due_date BETWEEN CURRENT_DATE and CURRENT_DATE+1")
    public List<Task> getTwoDays();

    public List<Task> findByStatus(String status);
}
