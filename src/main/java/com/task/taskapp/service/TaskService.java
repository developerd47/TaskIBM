package com.task.taskapp.service;

import com.google.gson.Gson;
import com.task.taskapp.model.Task;
import com.task.taskapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
//This is our Service class, where all our business logic resides.
public class TaskService {

    //Autowired the TaskRepository Interface, to perform different operation on DB
    @Autowired
    TaskRepository taskRepository;

    public ResponseEntity createTask(Task task) {
        //Set the initial status as "PENDING"
        task.setStatus("PENDING");
        //Save to DB
        taskRepository.save(task);
        String s = new Gson().toJson(task);
        //Return the Response Entity.
        return new ResponseEntity(s, HttpStatus.OK);
    }

    public List<Task> getTasks() {
        //Find all data
        return taskRepository.findAll();
    }

    public Task getTaskById(long id) {
        //Get task by ID
        return taskRepository.findById(id).get();
    }

    public List<Task> getTaskDueTodayTomorrow() {
        //call getTwoDays() Method which give data of item due for today and tomorrow
        return taskRepository.getTwoDays();
    }

    public List<Task> getCompletedTask() {
        //Find the task having status is "COMPLETED"
        return taskRepository.findByStatus("COMPLETED");
    }

    public List<Task> overDue() {
        //Return list of task cross the due date and they are still pending
        List<Task> tasks = taskRepository.findByDueDateBefore(Date.valueOf(LocalDate.now()));
        List<Task> taskList = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getStatus().equals("PENDING"))
                taskList.add(task);
        }

        return taskList;
    }

    //Change the status of task. If it got completed
    public ResponseEntity<String> changeTaskStatus(long id) {
        Task task = taskRepository.findById(id).get();
        task.setStatus("COMPLETED");
        taskRepository.save(task);
        return new ResponseEntity<>("Status Changed ", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteTask(long id) {
        //delete the item
        taskRepository.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);

    }


}
