package com.crude.tasks.controller;

import com.crude.tasks.domain.Task;
import com.crude.tasks.domain.TaskDto;
import com.crude.tasks.mapper.TaskMapper;
import com.crude.tasks.repository.TaskRepository;
import com.crude.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    void shouldReturnEmptyList() throws Exception {
        // given
        when(dbService.getAllTasks()).thenReturn(List.of());

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldReturnListWithTwoTasks() throws Exception {
        // given
        Task task1 = new Task(1L, "title1", "content1");
        Task task2 = new Task(2L, "title2", "content2");
        List<Task> taskList = new ArrayList<>(List.of(task1, task2));
        TaskDto taskDto1 = new TaskDto(1L, "titleDto1", "contentDto1");
        TaskDto taskDto2 = new TaskDto(2L, "titleDto2", "contentDto2");
        List<TaskDto> taskDtoList = new ArrayList<>(List.of(taskDto1, taskDto2));
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);
        when(dbService.getAllTasks()).thenReturn(taskList);

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("titleDto1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("contentDto1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void havingGivenIdShouldReturnTaskObject() throws Exception {
        // given
        Task task1 = new Task(1L, "title1", "content1");
        TaskDto taskDto1 = new TaskDto(1L, "titleDto1", "contentDto1");
        when(taskMapper.mapToTaskDto(task1)).thenReturn(taskDto1);
        when(dbService.getTask(task1.getId())).thenReturn(task1);

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/tasks/{taskId}", task1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("titleDto1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("contentDto1")));
    }


    @Test
    void shouldThrowTaskNotFoundException() throws TaskNotFoundException, Exception {
        // given
        Task task1 = new Task(1L, "title1", "content1");
        TaskDto taskDto1 = new TaskDto(1L, "titleDto1", "contentDto1");

        when(taskMapper.mapToTaskDto(task1)).thenReturn(taskDto1);
        when(dbService.getTaskSecondMethod(task1.getId())).thenThrow(TaskNotFoundException.class);

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/tasks/re/{taskId}", task1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void shouldCreateTrelloTask() throws Exception {
        // given
        Task savedTask = new Task(1L, "title1", "content1");
        TaskDto savedTaskDto1 = new TaskDto(1L, "titleDto1", "contentDto1");

        when(taskMapper.mapToTask(savedTaskDto1)).thenReturn(savedTask);
        when(dbService.saveTask(any(Task.class))).thenReturn(savedTask);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(savedTaskDto1);
        System.out.println("\n\n\t====>>>>" + jsonContent + "\n\n");

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("titleDto1")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("contentDto1")));
    }

    @Test
    void updateTask() {
        //to be done tomorrow
    }

    @Test
    void shouldSuccessfullyDeleteTask() throws Exception {
        // given
        // deleteById returns void so we use willDoNothing()
        Task task1 = new Task(1L, "title1", "content1");
        willDoNothing().given(dbService).deleteById(task1.getId());

        // when, then
        mockMvc.
                perform(MockMvcRequestBuilders.delete("/v1/tasks/{taskId}", task1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
