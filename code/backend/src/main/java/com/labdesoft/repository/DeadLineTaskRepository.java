package com.labdesoft.repository;

import com.labdesoft.entity.DeadLineTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeadLineTaskRepository extends JpaRepository<DeadLineTask, Integer> {
}
