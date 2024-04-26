package com.labdesoft.roteiro1.repository;

import com.labdesoft.roteiro1.entity.DeadLineTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeadLineTaskRepository extends JpaRepository<DeadLineTask, Integer> {
}
