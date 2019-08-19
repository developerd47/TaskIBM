package com.task.taskapp.controller;

import com.task.taskapp.model.Task;
import com.task.taskapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//This is the main controller class, where we expose end-points.
@RestController
//Allow Frount-end to CORS
@CrossOrigin(origins = "*")
public class MainController {

    //For business logic we create TaskService class and Autowired it.
    @Autowired
    TaskService taskService;

    //end-point for create new task, Request Method Type is POST
    @PostMapping(value = "/createtask")
    public ResponseEntity createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    //end-point for getAllTask, Request Method type is GET
    @GetMapping("/getAllTask")
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    //end-point for getTaskById, Request Method type is GET. It fetch id from request Parameter.
    @GetMapping("/getTaskById")
    public Task getTaskById(@RequestParam(value = "id") long id) {
        return taskService.getTaskById(id);
    }

    //end-point for getTaskDueTodayTomorrow, Request Method type is GET
    @GetMapping("/getTaskDueTodayTomorrow")
    public List<Task> getTaskDueTodayTomorrow() {
        return taskService.getTaskDueTodayTomorrow();
    }

    //end-point for getCompletedTask, Request Method type is GET
    @GetMapping("/getCompletedTask")
    public List<Task> getCompletedTask() {
        return taskService.getCompletedTask();
    }

    //end-point for overdueTask, Request Method type is GET
    @GetMapping("/overdueTask")
    public List<Task> overDue() {
        return taskService.overDue();
    }

    //end-point to updateStatus , Request Method type is PUT
    @PutMapping("/updateStatus")
    public ResponseEntity changeTaskStatus(@RequestParam(value = "id") long id) {
        return taskService.changeTaskStatus(id);
    }
    //end-point to deleteTaskById , Request Method type is DELETE. It fetch id from request Parameter.
    @DeleteMapping("/deleteTaskById")
    public ResponseEntity<String> deleteTask(@RequestParam(value = "id") long id) {
        return taskService.deleteTask(id);
    }
}
