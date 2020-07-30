package chalkboard.me.recovery_batch.infrastructure.api.benefit;

import chalkboard.me.recovery_batch.domain.benefit.CancelResult;
import chalkboard.me.recovery_batch.infrastructure.api.config.benefit.PointAPIConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PointAPIRepository {
    private final PointAPIConfig pointAPIConfig;

    public Mono<CancelResult> cancel(String hogeId) {
        CancelPointRequest request = new CancelPointRequest(hogeId);
        return pointAPIConfig.getBenefitConfig()
                .patch().uri(pointAPIConfig.getCancelPath())
                .body(Mono.just(request), CancelPointRequest.class)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> {
                    throw new RuntimeException("ステータスコードエラー");
                })
                .bodyToMono(CancelResult.class);
    }
}
