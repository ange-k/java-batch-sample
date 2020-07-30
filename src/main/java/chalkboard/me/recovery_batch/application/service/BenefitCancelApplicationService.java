package chalkboard.me.recovery_batch.application.service;

import chalkboard.me.recovery_batch.annotation.spotbugs.SuppressFBWarnings;
import chalkboard.me.recovery_batch.domain.benefit.CancelResult;
import chalkboard.me.recovery_batch.domain.read.recoverytask.RecoveryType;
import java.util.Optional;
import chalkboard.me.recovery_batch.domain.read.ReservationRecoveryTask;
import chalkboard.me.recovery_batch.infrastructure.api.benefit.PointAPIRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressFBWarnings(value={"NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"}, justification="誤検知")
public class BenefitCancelApplicationService implements TaskExecutionService{
    private final PointAPIRepository pointAPIRepository;
    public boolean execute(ReservationRecoveryTask task) {
        log.info("begin: BenefitCancelApplicationService_execute");

        Optional<CancelResult> response = pointAPIRepository.cancel(task.getHogeId().get())
                .map(Optional::of)
                .onErrorResume((throwable -> {
                    log.error("何らかの例外", throwable);
                    return Mono.just(Optional.empty());
                }))
                .block();

        return response.isPresent();
    }

    @Override
    public RecoveryType getRecoveryType() {
        return RecoveryType.POINT_ROLLBACK;
    }
}
