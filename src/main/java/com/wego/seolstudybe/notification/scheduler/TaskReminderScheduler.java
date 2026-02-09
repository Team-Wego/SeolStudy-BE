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
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskReminderScheduler {

    private final TaskRepository taskRepository;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void sendTaskReminder() {
        LocalDate today = LocalDate.now();
        List<Task> uncompletedTasks = taskRepository.findByDateAndIsCheckedFalse(today);

        if (uncompletedTasks.isEmpty()) {
            log.info("오늘 미완료 과제가 없습니다.");
            return;
        }

        // 멘티별로 그룹핑
        Map<Integer, List<Task>> tasksByMentee = uncompletedTasks.stream()
                .collect(Collectors.groupingBy(task -> task.getMentee().getId()));

        tasksByMentee.forEach((menteeId, tasks) -> {
            String title = "과제 리마인더";
            String body = "아직 완료하지 않은 과제가 " + tasks.size() + "개 있습니다.";

            notificationService.notify(
                    (long) menteeId,
                    NotificationType.TASK_REMINDER,
                    title,
                    body,
                    Map.of("taskCount", String.valueOf(tasks.size()), "date", today.toString())
            );

            log.info("과제 리마인더 전송: menteeId={}, taskCount={}", menteeId, tasks.size());
        });
    }
}
