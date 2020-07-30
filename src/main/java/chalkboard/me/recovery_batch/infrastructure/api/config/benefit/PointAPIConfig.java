package chalkboard.me.recovery_batch.infrastructure.api.config.benefit;

import chalkboard.me.recovery_batch.annotation.spotbugs.SuppressFBWarnings;
import chalkboard.me.recovery_batch.infrastructure.api.config.BaseWebclientConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@Configuration
@ConfigurationProperties(prefix = "extension.api.benefit")
@SuppressFBWarnings(value={"RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE"}, justification="Lombokの自動生成コードに対する警告")
public class PointAPIConfig extends BaseWebclientConfiguration {
    private String hostName;
    private Integer connectionTimeOut;
    private Integer socketTimeOut;

    private String cancelPath;

    public WebClient getBenefitConfig() {
        return WebClient.builder()
                .clientConnector(getDefaultClientConnector().apply(connectionTimeOut, socketTimeOut))
                .baseUrl(hostName)
                .build();
    }
}
