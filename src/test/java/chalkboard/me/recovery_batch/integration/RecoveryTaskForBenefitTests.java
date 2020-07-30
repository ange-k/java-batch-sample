package chalkboard.me.recovery_batch.integration;

import chalkboard.me.recovery_batch.config.WireMockInitializer;
import chalkboard.me.recovery_batch.domain.read.recoverytask.RecoveryStatus;
import com.github.tomakehurst.wiremock.WireMockServer;
import javax.sql.DataSource;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.CsvDataFileLoader;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;

import static org.junit.Assert.fail;

@ActiveProfiles("test")
@SpringBootTest
@SpringBatchTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {WireMockInitializer.class})
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@DisplayName("リカバリバッチ 統合テスト")
public class RecoveryTaskForBenefitTests {
    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private DataSourceDatabaseTester dataSourceDatabaseTester; // dbunit
    private CsvDataFileLoader csvDataFileLoader;

    private final String testDataPath = "classpath:chalkboard/me/recovery_batch/integration/benefit/";

    @BeforeEach
    public void setup() {
        dataSourceDatabaseTester = new DataSourceDatabaseTester(dataSource);
        dataSourceDatabaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        csvDataFileLoader = new CsvDataFileLoader();
        try{
            dataSourceDatabaseTester.setDataSet(csvDataFileLoader.loadDataSet(ResourceUtils.getURL(testDataPath)));
            dataSourceDatabaseTester.onSetup();
        } catch (Exception e) {
            fail();
        }
    }

    @AfterEach
    public void afterEach() {
        this.wireMockServer.resetAll();
    }

    @Test
    public void 正常系_リカバリを実行する() {
        int beforeCountCreated = jdbcTemplate.queryForObject(taskStatusCountSQL(RecoveryStatus.CREATED), Integer.class);
        int beforeCountRunning = jdbcTemplate.queryForObject(taskStatusCountSQL(RecoveryStatus.RUNNING), Integer.class);
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("recoveryStep");

        Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());

        int afterCountCreated = jdbcTemplate.queryForObject(taskStatusCountSQL(RecoveryStatus.CREATED), Integer.class);
        int afterCountCompleted = jdbcTemplate.queryForObject(taskStatusCompletedCountSQL(), Integer.class);

        Assert.assertEquals(beforeCountCreated, 1); // (CREATED OR RUNNNING) AND COUNT19以下しか取得しない
        Assert.assertEquals(beforeCountRunning, 2);
        Assert.assertEquals(afterCountCreated, 0);  // batchが実行されたらCREATEDはなくなる
        Assert.assertEquals(afterCountCompleted, 4); // もとから完了していたもの + ↑の実行結果で増えたもの. COUNT20以上は実行されない.
    }


    private String taskStatusCountSQL(RecoveryStatus status) {
        return "SELECT COUNT(ID) FROM RECOVERY_TASKS WHERE COUNT < 20 AND STATUS = '"
                + status.name() + "';";
    }

    private String taskStatusCompletedCountSQL() {
        return "SELECT COUNT(ID) FROM RECOVERY_TASKS WHERE STATUS = 'COMPLETION'";
    }
}
