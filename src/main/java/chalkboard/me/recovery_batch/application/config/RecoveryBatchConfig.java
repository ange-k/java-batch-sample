package chalkboard.me.recovery_batch.application.config;

import chalkboard.me.recovery_batch.application.job.RecoveryTaskProcessor;
import chalkboard.me.recovery_batch.application.job.RecoveryTaskReader;
import chalkboard.me.recovery_batch.application.job.RecoveryTaskWriter;
import chalkboard.me.recovery_batch.application.job.dto.ReservationRecoveryTaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecoveryBatchConfig {
    private static final String RECOVERY_STEP = "recoveryStep";
    private static final int CHUNK_SIZE = 20;

    private final StepBuilderFactory stepBuilderFactory;

    private final RecoveryTaskReader recoveryTaskReader;
    private final RecoveryTaskProcessor recoveryTaskProcessor;
    private final RecoveryTaskWriter recoveryTaskWriter;

    @Bean(name = RECOVERY_STEP)
    public Step recoveryStep() {
        return stepBuilderFactory
                .get(RECOVERY_STEP)
                .<ReservationRecoveryTaskDto, ReservationRecoveryTaskDto>chunk(CHUNK_SIZE)
                .reader(recoveryTaskReader)
                .processor(recoveryTaskProcessor)
                .writer(recoveryTaskWriter)
                .build();
    }
}
