package com.crude.tasks.controller;

import com.crude.tasks.domain.Task;
import com.crude.tasks.domain.TaskDto;
import com.crude.tasks.mapper.TaskMapper;
import com.crude.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final DbService service;
    private final TaskMapper taskMapper;

    @GetMapping
    public List<TaskDto> getTasks() {
        List<Task> tasks = service.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }

    @GetMapping(value = "{taskId}")
    public TaskDto getTask(@PathVariable("taskId") Long taskId) {
        return taskMapper.mapToTaskDto(service.getTask(taskId));
    }

    @PostMapping
    public void createTask(TaskDto taskDto) {

    }

    @PutMapping
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Test content");
    }

    @DeleteMapping("{taskId}")
    public void deleteTask(@PathVariable("taskId") Long taskId) {

    }
}
