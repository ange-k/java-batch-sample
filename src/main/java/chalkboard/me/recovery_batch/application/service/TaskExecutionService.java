package chalkboard.me.recovery_batch.application.service;

import chalkboard.me.recovery_batch.domain.read.recoverytask.RecoveryType;
import chalkboard.me.recovery_batch.domain.read.ReservationRecoveryTask;

public interface TaskExecutionService {
    boolean execute(ReservationRecoveryTask reservationRecoveryTask);

    RecoveryType getRecoveryType();
}
