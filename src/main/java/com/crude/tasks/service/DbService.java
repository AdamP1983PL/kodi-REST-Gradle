package com.crude.tasks.service;

import com.crude.tasks.domain.Task;
import com.crude.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbService {

    private final TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTask(long id){
        Optional<Task> tempTask = repository.findById(id);

        Task task;
        if(tempTask.isPresent()){
            task = tempTask.get();
        } else {
            throw new RuntimeException("There is no Task for id: " + id);
        }

        return task;
    }


}
