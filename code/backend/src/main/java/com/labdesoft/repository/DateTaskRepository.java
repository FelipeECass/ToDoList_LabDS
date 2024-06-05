package com.labdesoft.repository;

import com.labdesoft.entity.DateTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateTaskRepository extends JpaRepository<DateTask, Integer> {
}
