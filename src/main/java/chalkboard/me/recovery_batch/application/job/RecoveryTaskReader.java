package chalkboard.me.recovery_batch.application.job;

import chalkboard.me.recovery_batch.application.job.dto.ReservationRecoveryTaskDto;
import chalkboard.me.recovery_batch.application.config.DatabaseConfig;
import chalkboard.me.recovery_batch.infrastructure.mapper.ReservationRecoveryTasksMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Slf4j
public class RecoveryTaskReader extends MyBatisCursorItemReader<ReservationRecoveryTaskDto> {
    public RecoveryTaskReader(
            @Qualifier(DatabaseConfig.ORACLE_SESSION_FACTORY) final SqlSessionFactory sqlSessionFactory
    ){
        log.info("begin: reader");
        setSqlSessionFactory(sqlSessionFactory);
        setQueryId(ReservationRecoveryTasksMapper.class.getName() + ".findAll");
    }
}
