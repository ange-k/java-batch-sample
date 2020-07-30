package chalkboard.me.recovery_batch.application.service;

import chalkboard.me.recovery_batch.domain.read.recoverytask.RecoveryType;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskStrategyFactory {
    private Map<RecoveryType,TaskExecutionService> typeTaskExecutionServiceMap;

    @Autowired
    public TaskStrategyFactory(Set<TaskExecutionService> taskExecutionServices) {
        createStrategy(taskExecutionServices);
    }

    public TaskExecutionService findStrategy(RecoveryType type) {
        return typeTaskExecutionServiceMap.get(type);
    }

    private void createStrategy(Set<TaskExecutionService> taskExecutionServiceSet) {
        typeTaskExecutionServiceMap = taskExecutionServiceSet.stream()
                .collect(Collectors.toMap(TaskExecutionService::getRecoveryType, taskExecutionService -> taskExecutionService));
    }
}
