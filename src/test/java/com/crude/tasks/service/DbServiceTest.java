package com.crude.tasks.service;

import com.crude.tasks.controller.TaskNotFoundException;
import com.crude.tasks.domain.Task;
import com.crude.tasks.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DbServiceTest {

    @Autowired
    private DbService dbService;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void beforeCleanUp() {
        taskRepository.deleteAll();
    }

    @AfterEach
    void afterCleanUp() {
        taskRepository.deleteAll();
    }

    @Test
    void getAllTaskShouldReturnEmptyList() {
        // given
        List<Task> tasks = new ArrayList<>();
        taskRepository.saveAll(tasks);

        // when
        List<Task> returnedTasks = dbService.getAllTasks();

        // then
        assertEquals(0, returnedTasks.size());
        assertTrue(tasks.isEmpty());
    }

    @Test
    void getTaskWithGivenIdShouldThrowException() {
        // given
        // when, then
        assertThrows(RuntimeException.class, () -> {
            Task tempTask = dbService.getTask(101L);
        });
    }

    @Test
    void shouldReturnTaskWithGivenId() {
        // given
        Task task1 = new Task(1L, "test title", "test description");
        task1 = taskRepository.save(task1);

        // when
        Task tempTask = dbService.getTask(task1.getId());

        // then
        assertEquals("test title", tempTask.getTitle());
        assertEquals(task1.getContent(), tempTask.getContent());
        assertNotNull(tempTask);
    }

    @Test
    void testSaveTask() {
        // given
        Task task1 = new Task(1L, "test title", "test description");

        // when
        dbService.saveTask(task1);
        List<Task> tasks = dbService.getAllTasks();
        Task savedTask = tasks.get(0);

        // then
        assertEquals(1, tasks.size());
        assertNotNull(savedTask.getId());
        assertEquals(task1.getTitle(), savedTask.getTitle());
        assertEquals(task1.getContent(), savedTask.getContent());
    }

    @Test
    void getTaskSecondMethodShouldThrowTaskNotFoundException() {
        // given
        // when, then
        assertThrows(TaskNotFoundException.class, () -> {
            Task tempTask = dbService.getTaskSecondMethod(111L);
        });
    }

    @Test
    void testDeleteTask() {
        // given
        Task task1 = new Task(1L, "test title", "test description");
        Task task2 = new Task(2L, "test title2", "test description2");
        dbService.saveTask(task1);
        dbService.saveTask(task2);
        List<Task> tasksBeforeDeletion = dbService.getAllTasks();

        // when
        dbService.deleteById(tasksBeforeDeletion.get(0).getId());
        List<Task> tasksAfterDeletion = dbService.getAllTasks();

        // then
        assertEquals(2, tasksBeforeDeletion.size());
        assertEquals(1, tasksAfterDeletion.size());
    }

}
