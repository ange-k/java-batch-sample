package chalkboard.me.recovery_batch.application.job.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ReservationRecoveryTaskDto implements Serializable {
    private long id;
    private String type;
    private String status;
    @Nullable
    private String hogeId;
    @Nullable
    private String parameter;
    private int count;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String updateUser;
}
