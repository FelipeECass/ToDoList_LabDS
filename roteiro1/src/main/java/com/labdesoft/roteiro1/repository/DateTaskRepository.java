package com.labdesoft.roteiro1.repository;

import com.labdesoft.roteiro1.entity.DateTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateTaskRepository extends JpaRepository<DateTask, Integer> {
}
