package chalkboard.me.recovery_batch.application.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.scripting.thymeleaf.ThymeleafLanguageDriver;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile({"dev", "stg", "prod"})
public class DatabaseConfig {
    public static final String ORACLE_DATA_SOURCE = "oracleDataSource";
    public static final String ORACLE_SESSION_FACTORY = "oracleSqlSessionFactory";

    private final DatabaseProperties databaseProperties;

    /**
     * Oracle DataSourceの作成
     *
     * @return Oracle DataSource
     */
    @Primary
    @Bean(name = ORACLE_DATA_SOURCE)
    public DataSource oracleDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(DatabaseProperties.ORACLE_DRIVER)
                .url(databaseProperties.getOracleUrl())
                .username(databaseProperties.getOracleUsername())
                .password(databaseProperties.getOraclePassword())
                .build();
    }

    /**
     * Oracle Sessionの作成
     *
     * @param oracleDataSource Oracle DataSource
     * @return Oracle Sqlセッション
     * @throws Exception DBセッション生成例外
     */
    @Primary
    @Bean(name = ORACLE_SESSION_FACTORY)
    public SqlSessionFactory oracleSqlSessionFactory(
            @Qualifier(ORACLE_DATA_SOURCE) final DataSource oracleDataSource
    ) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(oracleDataSource);
        factoryBean.setDefaultScriptingLanguageDriver(ThymeleafLanguageDriver.class);
        SqlSessionFactory factory = factoryBean.getObject();
        return factory;
    }

}
