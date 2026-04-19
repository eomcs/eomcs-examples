package com.eomcs.advanced.jpa.exam30;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// exam30 - 읽기 전용 트랜잭션 & 성능 측정
//
// hibernate.generate_statistics = true:
//   Hibernate가 다음 지표를 수집한다.
//   - getQueryExecutionCount()     : 실행된 SQL 쿼리 수
//   - getEntityLoadCount()         : 로드된 엔티티 수
//   - getEntityUpdateCount()       : UPDATE된 엔티티 수
//   - getFlushCount()              : flush() 실행 횟수
//   - getSessionOpenCount()        : 열린 세션 수
//   - getConnectCount()            : 커넥션 획득 횟수
//   → 통계로 readOnly=true와 일반 트랜잭션의 차이를 수치로 비교한다.
//
@Configuration
@ComponentScan(basePackages = "com.eomcs.advanced.jpa.exam30")
@EnableJpaRepositories(basePackages = "com.eomcs.advanced.jpa.exam30")
@EnableTransactionManagement
public class JpaConfig {

  @Bean
  public DataSource dataSource() {
    String host    = System.getenv("DB_HOSTNAME");
    String port    = System.getenv("DB_PORT");
    String service = System.getenv("DB_SERVICE_NAME");

    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:oracle:thin:@//" + host + ":" + port + "/" + service);
    config.setUsername(System.getenv("DB_USERNAME"));
    config.setPassword(System.getenv("DB_PASSWORD"));
    config.setMaximumPoolSize(5);
    return new HikariDataSource(config);
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
    emfb.setDataSource(dataSource);
    emfb.setPackagesToScan("com.eomcs.advanced.jpa.exam30");

    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    adapter.setShowSql(true);
    emfb.setJpaVendorAdapter(adapter);

    Properties props = new Properties();
    props.setProperty("hibernate.format_sql",          "true");
    props.setProperty("hibernate.use_sql_comments",    "true");
    props.setProperty("hibernate.hbm2ddl.auto",        "none");
    props.setProperty("hibernate.dialect",             "org.hibernate.dialect.OracleDialect");
    // 통계 수집 활성화: readOnly vs 일반 트랜잭션 차이 측정
    props.setProperty("hibernate.generate_statistics", "true");
    emfb.setJpaProperties(props);
    return emfb;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }
}
