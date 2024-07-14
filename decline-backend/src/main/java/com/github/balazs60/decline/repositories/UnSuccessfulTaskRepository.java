package com.github.balazs60.decline.repositories;

import com.github.balazs60.decline.model.UnSuccessfulTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnSuccessfulTaskRepository extends JpaRepository<UnSuccessfulTask,Long> {
}
