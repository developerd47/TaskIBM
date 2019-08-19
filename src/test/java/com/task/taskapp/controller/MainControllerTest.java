package com.task.taskapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.task.taskapp.model.Task;
import com.task.taskapp.service.TaskService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskService taskService;

    @Test
    public void createTask() throws Exception{

        Task topic = new Task(10,"T1","Dec1", Date.valueOf("2019-08-25"),"PENDING");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String objectAsjsonString = gson.toJson(topic);
        Mockito.when(taskService.createTask(Mockito.any())).thenReturn(new ResponseEntity<>(objectAsjsonString, HttpStatus.CREATED));
        MvcResult mvcResult = this.mockMvc.perform(post("/createtask")
                .accept(MediaType.APPLICATION_JSON).content(objectAsjsonString)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Assert.assertEquals(objectAsjsonString,response.getContentAsString());
    }

    @Test
    public void getTasks() throws Exception{
        List<Task> allTask = new ArrayList<Task>();
        allTask.add(new Task(10,"T1","Dec1", Date.valueOf("2019-08-25"),"PENDING"));
        allTask.add(new Task(11,"T2","Dec2", Date.valueOf("2019-08-18"),"PENDING"));
        allTask.add(new Task(12,"T3","Dec3", Date.valueOf("2019-08-16"),"COMPLETED"));
        allTask.add(new Task(13,"T4","Dec4", Date.valueOf("2019-08-19"),"PENDING"));
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String objectAsjsonString = gson.toJson(allTask);
        Mockito.when(taskService.getTasks()).thenReturn(allTask);
        MvcResult mvcResult = this.mockMvc.perform(get("/getAllTask")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        JSONAssert.assertEquals(objectAsjsonString,response.getContentAsString(),true);
    }

    @Test
    public void getTaskById() throws Exception{
        Task task =new Task(11,"T2","Dec2", Date.valueOf("2019-08-18"),"PENDING");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String objectAsjsonString = gson.toJson(task);
        Mockito.when(taskService.getTaskById(Mockito.anyLong())).thenReturn(task);
        MvcResult mvcResult = this.mockMvc.perform(get("/getTaskById").param("id","11")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        JSONAssert.assertEquals(objectAsjsonString,response.getContentAsString(),true);
    }

    @Test
    public void getTaskDueTodayTomorrow() throws Exception {
        List<Task> allTask = new ArrayList<Task>();
        allTask.add(new Task(11,"T2","Dec2", Date.valueOf("2019-08-18"),"PENDING"));
        allTask.add(new Task(13,"T4","Dec4", Date.valueOf("2019-08-19"),"PENDING"));
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String objectAsjsonString = gson.toJson(allTask);
        Mockito.when(taskService.getTaskDueTodayTomorrow()).thenReturn(allTask);
        MvcResult mvcResult = this.mockMvc.perform(get("/getTaskDueTodayTomorrow")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        JSONAssert.assertEquals(objectAsjsonString,response.getContentAsString(),true);

    }

    @Test
    public void getCompletedTask() throws Exception{
        List<Task> allTask = new ArrayList<Task>();
        allTask.add(new Task(12,"T3","Dec3", Date.valueOf("2019-08-16"),"COMPLETED"));
        allTask.add(new Task(13,"T4","Dec4", Date.valueOf("2019-08-19"),"COMPLETED"));
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String objectAsjsonString = gson.toJson(allTask);
        Mockito.when(taskService.getCompletedTask()).thenReturn(allTask);
        MvcResult mvcResult = this.mockMvc.perform(get("/getCompletedTask")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        JSONAssert.assertEquals(objectAsjsonString,response.getContentAsString(),true);
    }

    @Test
    public void overDue() throws Exception{
        List<Task> allTask = new ArrayList<Task>();
        allTask.add(new Task(10,"T1","Dec1", Date.valueOf("2019-08-12"),"PENDING"));
        allTask.add(new Task(11,"T2","Dec2", Date.valueOf("2019-08-10"),"PENDING"));
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String objectAsjsonString = gson.toJson(allTask);
        Mockito.when(taskService.overDue()).thenReturn(allTask);
        MvcResult mvcResult = this.mockMvc.perform(get("/overdueTask")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        JSONAssert.assertEquals(objectAsjsonString,response.getContentAsString(),true);
    }

    @Test
    public void changeTaskStatus() throws Exception{
        Mockito.when(taskService.changeTaskStatus(Mockito.anyLong())).thenReturn(new ResponseEntity<String>("Status Changed",HttpStatus.OK));
        MvcResult mvcResult = this.mockMvc.perform(put("/updateStatus").param("id","11")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Assert.assertEquals("Status Changed",response.getContentAsString());
    }

    @Test
    public void deleteTask() throws Exception {

        Mockito.when(taskService.deleteTask(Mockito.anyLong())).thenReturn(new ResponseEntity<String>("Deleted",HttpStatus.OK));
        MvcResult mvcResult = this.mockMvc.perform(delete("/deleteTaskById").param("id","11")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Assert.assertEquals("Deleted",response.getContentAsString());

    }
}