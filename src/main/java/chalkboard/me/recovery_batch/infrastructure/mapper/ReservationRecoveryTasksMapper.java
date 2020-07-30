package chalkboard.me.recovery_batch.infrastructure.mapper;

import chalkboard.me.recovery_batch.application.job.dto.ReservationRecoveryTaskDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ReservationRecoveryTasksMapper {
    @Select("sql/selectRecoveryTasks.sql")
    List<ReservationRecoveryTaskDto> findAll();

    @Update("sql/updateRecoveryTask.sql")
    void update(@Param("task") ReservationRecoveryTaskDto task);
}
