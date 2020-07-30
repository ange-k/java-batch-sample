package chalkboard.me.recovery_batch.application.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecoveryBatchJobConfig {
    private static final String BENEFT_JOB = "benefit";

    private final JobBuilderFactory jobBuilderFactory;
    private final Step recoveryStep;

    @Bean(name = BENEFT_JOB)
    public Job benefitJob() {
        return jobBuilderFactory.get(BENEFT_JOB)
                .incrementer(new RunIdIncrementer())
                .flow(recoveryStep)
                .end()
                .build();
    }
}
