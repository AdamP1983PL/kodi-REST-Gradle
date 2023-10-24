package com.crude.tasks.controller;

import com.crude.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    @GetMapping
    public List<TaskDto> getTasks() {
        return new ArrayList<>();
    }

    @GetMapping("/{taskId}")
    public TaskDto getTask(@PathVariable("taskId") Long taskId) {
        return null;
    }

    @PostMapping
    public void createTask(@RequestBody TaskDto taskDto) {

    }

    @PutMapping
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Test content");
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable("taskId") Long taskId) {

    }
}
