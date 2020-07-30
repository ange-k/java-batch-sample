package chalkboard.me.recovery_batch.application.job;

import chalkboard.me.recovery_batch.application.job.dto.ReservationRecoveryTaskDto;
import chalkboard.me.recovery_batch.domain.read.recoverytask.RecoveryStatus;
import chalkboard.me.recovery_batch.domain.read.recoverytask.RecoveryType;
import java.time.LocalDateTime;
import java.util.Optional;
import chalkboard.me.recovery_batch.application.service.TaskExecutionService;
import chalkboard.me.recovery_batch.application.service.TaskStrategyFactory;
import chalkboard.me.recovery_batch.domain.read.ReservationRecoveryTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
@RequiredArgsConstructor
@Slf4j
public class RecoveryTaskProcessor implements ItemProcessor<ReservationRecoveryTaskDto, ReservationRecoveryTaskDto> {

    private final TaskStrategyFactory taskStrategyFactory;

    @Override
    public ReservationRecoveryTaskDto process(ReservationRecoveryTaskDto reservationRecoveryTaskDto) throws Exception {
        log.info("begin: processer");
        ReservationRecoveryTask task = ReservationRecoveryTask.builder()
                .id(reservationRecoveryTaskDto.getId())
                .type(RecoveryType.valueOf(reservationRecoveryTaskDto.getType()))
                .status(RecoveryStatus.valueOf(reservationRecoveryTaskDto.getStatus()))
                .hogeId(Optional.ofNullable(reservationRecoveryTaskDto.getHogeId()))
                .parameter(Optional.ofNullable(reservationRecoveryTaskDto.getParameter()))
                .count(reservationRecoveryTaskDto.getCount())
                .createdAt(reservationRecoveryTaskDto.getCreatedAt())
                .updatedAt(reservationRecoveryTaskDto.getUpdatedAt())
                .updateUser(reservationRecoveryTaskDto.getUpdateUser())
                .build();

        try {
            TaskExecutionService service = taskStrategyFactory.findStrategy(task.getType());
            boolean serviceResult = service.execute(task);
            return taskResultBuild(task, serviceResult);
        } catch (Exception e) {
            log.error("規定外の例外", e);
            return taskResultBuild(task, RecoveryStatus.FAULTED);
        }
    }

    private  ReservationRecoveryTaskDto taskResultBuild(ReservationRecoveryTask task, boolean serviceResult) {
        return taskResultBuild(task, serviceResult ? RecoveryStatus.COMPLETION : RecoveryStatus.RUNNING);
    }

    private ReservationRecoveryTaskDto taskResultBuild(ReservationRecoveryTask task, RecoveryStatus status) {
        return ReservationRecoveryTaskDto.builder()
                .id(task.getId())
                .type(task.getType().name())
                .status(status.name())
                .hogeId(task.getHogeId().orElse(null))
                .parameter(task.getParameter().orElse(null))
                .count(task.getCount() + 1)
                .createdAt(task.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .updateUser("recovery-batch")
                .build();
    }
}
