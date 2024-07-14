package com.github.balazs60.decline.service;

import com.github.balazs60.decline.model.UnSuccessfulTask;
import com.github.balazs60.decline.repositories.UnSuccessfulTaskRepository;
import org.springframework.stereotype.Service;

@Service
public class UnSuccessfulTaskService {

    private UnSuccessfulTaskRepository unSuccessfulTaskRepository;

    public UnSuccessfulTaskService(UnSuccessfulTaskRepository unSuccessfulTaskRepository) {
        this.unSuccessfulTaskRepository = unSuccessfulTaskRepository;
    }

    public void addUnsuccessfulTask(UnSuccessfulTask unSuccessfulTask){
        unSuccessfulTaskRepository.save(unSuccessfulTask);
    }
}
