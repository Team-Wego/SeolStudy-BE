package com.wego.seolstudybe.notification.scheduler;

import com.wego.seolstudybe.notification.entity.enums.NotificationType;
import com.wego.seolstudybe.notification.service.NotificationService;
import com.wego.seolstudybe.task.entity.Task;
import com.wego.seolstudybe.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskReminderScheduler {

    private final TaskRepository taskRepository;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void sendTaskReminder() {
        sendTaskReminderForDate(LocalDate.now());
    }

    public void sendTaskReminderForDate(LocalDate date) {
        List<Task> uncompletedTasks = taskRepository.findByDateAndIsCheckedFalse(date);

        if (uncompletedTasks.isEmpty()) {
            log.info("{} 미완료 과제가 없습니다.", date);
            return;
        }

        // 과제별 개별 알림 전송
        for (Task task : uncompletedTasks) {
            int menteeId = task.getMentee().getId();
            String title = "과제 리마인더";
            String body = "미완료 과제: " + task.getTitle();

            notificationService.notify(
                    (long) menteeId,
                    NotificationType.TASK_REMINDER,
                    title,
                    body,
                    Map.of("taskId", String.valueOf(task.getId()), "date", date.toString())
            );

            log.info("과제 리마인더 전송: menteeId={}, taskId={}, title={}", menteeId, task.getId(), task.getTitle());
        }
    }
}
