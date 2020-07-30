package chalkboard.me.recovery_batch.application.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Profile;

@ConstructorBinding
@ConfigurationProperties(prefix = "extension.db")
@RequiredArgsConstructor
@Getter
@Profile({"dev", "stg", "prod"})
public class DatabaseProperties {
    public static final String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
    private final String oracleUrl;
    private final String oracleUsername;
    private final String oraclePassword;
}
