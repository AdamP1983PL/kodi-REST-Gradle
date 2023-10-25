package com.crude.tasks.controller;

import com.crude.tasks.domain.Task;
import com.crude.tasks.domain.TaskDto;
import com.crude.tasks.mapper.TaskMapper;
import com.crude.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final DbService service;
    private final TaskMapper taskMapper;

    @GetMapping
//    public List<TaskDto> getTasks() {
//        List<Task> tasks = service.getAllTasks();
//        return taskMapper.mapToTaskDtoList(tasks);
//    }
    public ResponseEntity<List<TaskDto>> getTasks() {
        List<Task> tasks = service.getAllTasks();
        return ResponseEntity.ok(taskMapper.mapToTaskDtoList(tasks));
    }


    @GetMapping(value = "{taskId}")
    public TaskDto getTask(@PathVariable("taskId") Long taskId) {
        return taskMapper.mapToTaskDto(service.getTask(taskId));
    }

//    @GetMapping(value = "/optional/{taskId}")
//    public TaskDto getOptionalTask(@PathVariable(name = "taskId") Long taskId) throws TaskNotFoundException{
//        return taskMapper.mapToTaskDto(service.getOptionalTask(taskId).orElseThrow(TaskNotFoundException::new));
//    }

    @GetMapping(value = "/re/{taskId}")
    public ResponseEntity<TaskDto> getTaskRE(@PathVariable(name = "taskId") Long taskId) throws TaskNotFoundException {
        return ResponseEntity.ok(taskMapper.mapToTaskDto(service.getTaskSecondMethod(taskId)));
    }


//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void createTask(@RequestBody TaskDto taskDto) {
//        Task task = taskMapper.mapToTask(taskDto);
//        service.saveTask(task);
//    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTask(@RequestBody TaskDto taskDto) {
        service.saveTask(taskMapper.mapToTask(taskDto));
        return ResponseEntity.ok().build();
    }

//    @PutMapping
//    public TaskDto updateTask(TaskDto taskDto) {
//        return new TaskDto(1L, "Edited test title", "Test content");
//    }

    @PutMapping
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        Task savedTask = service.saveTask(task);
        return ResponseEntity.ok(taskMapper.mapToTaskDto(savedTask));
    }

    @DeleteMapping(value = "{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable(name = "taskId") Long taskId) {
        service.deleteById(taskId);
        return ResponseEntity.ok().build();
    }
}
