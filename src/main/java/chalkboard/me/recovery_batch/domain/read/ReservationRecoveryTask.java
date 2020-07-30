package chalkboard.me.recovery_batch.domain.read;

import chalkboard.me.recovery_batch.domain.read.recoverytask.RecoveryStatus;
import chalkboard.me.recovery_batch.domain.read.recoverytask.RecoveryType;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ReservationRecoveryTask {
    private long id;
    private RecoveryType type;
    private RecoveryStatus status;
    private Optional<String> hogeId;
    private Optional<String> parameter;
    private int count;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String updateUser;
}
