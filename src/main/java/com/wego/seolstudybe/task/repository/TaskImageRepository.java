package com.wego.seolstudybe.task.repository;

import com.wego.seolstudybe.task.entity.TaskImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskImageRepository extends JpaRepository<TaskImage, Integer> {
}
