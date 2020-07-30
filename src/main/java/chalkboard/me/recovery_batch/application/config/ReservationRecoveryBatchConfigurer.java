package chalkboard.me.recovery_batch.application.config;

import java.time.Clock;
import java.time.ZoneId;
import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class ReservationRecoveryBatchConfigurer extends DefaultBatchConfigurer {
    private static final String TIMEZONE = "Asia/Tokyo";

    /**
     * データソースセット部分を空処理で上書きすることでMapRepositoryが使われるようになる
     *
     * @param dataSource
     */
    @Override
    public void setDataSource(DataSource dataSource) {
        // override to do not set datasource even if a datasource exist.
        // initialize will use a Map based JobRepository (instead of database)
    }

    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.of(TIMEZONE));
    }
}
