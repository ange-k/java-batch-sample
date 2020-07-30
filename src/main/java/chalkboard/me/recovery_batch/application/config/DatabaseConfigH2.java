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
@Profile({"local", "test"})
public class DatabaseConfigH2 {
    public static final String ORACLE_DATA_SOURCE = "oracleDataSource";
    public static final String ORACLE_SESSION_FACTORY = "oracleSqlSessionFactory";

    /**
     * Oracle DataSourceの作成
     *
     * @return Oracle DataSource
     */
    @Primary
    @Bean(name = ORACLE_DATA_SOURCE)
    public DataSource oracleDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:develop;MODE=Oracle;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                .username("sa")
                .password("")
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
