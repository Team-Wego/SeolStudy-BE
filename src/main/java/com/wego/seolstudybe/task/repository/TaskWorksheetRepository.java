package com.wego.seolstudybe.task.repository;

import com.wego.seolstudybe.task.entity.Task;
import com.wego.seolstudybe.task.entity.TaskWorksheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskWorksheetRepository extends JpaRepository<TaskWorksheet, Integer> {
    void deleteByTask(Task task);
}
