package com.crude.tasks.mapper;

import com.crude.tasks.domain.Task;
import com.crude.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TaskMapperTest {

    TaskMapper taskMapper = new TaskMapper();

    @Test
    void shouldMapTaskDtoToTask() {
        // given
        TaskDto taskDto = new TaskDto(1L, "test title", "test content");

        // when
        Task task = taskMapper.mapToTask(taskDto);

        // then
        assertEquals(1L, task.getId());
        assertEquals("test title", task.getTitle());
        assertEquals("test content", task.getContent());
    }

    @Test
    void shouldMapTaskToTaskDto() {
        // given
        Task task = new Task(1L, "test task title", "test task content");

        // when
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        // then
        assertEquals(1L, taskDto.getId());
        assertEquals("test task title", taskDto.getTitle());
        assertEquals("test task content", taskDto.getContent());
    }

    @Test
    void shouldMapTaskListToTaskDtoList() {
        // given
        Task task1 = new Task(1L, "test task title1", "test task content1");
        List<Task> taskList = List.of(task1);

        // when
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(taskList);

        // then
        assertEquals(1, taskDtos.size());

        taskDtos.forEach(taskDto -> {
            assertEquals(1L, taskDto.getId());
            assertEquals("test task title1", taskDto.getTitle());
            assertEquals("test task content1", taskDto.getContent());
        });
    }

}
