package com.eomcs.advanced.jpa.exam29;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// exam29 - 배치 처리 & Bulk 연산
//
// JDBC 배치 설정:
//   hibernate.jdbc.batch_size = 50
//     → save()를 반복해도 JDBC addBatch() → executeBatch()로 묶어서 전송
//     → 50건마다 한 번의 네트워크 왕복으로 처리 (N번 → N/50번)
//
//   hibernate.order_inserts = true
//   hibernate.order_updates = true
//     → 같은 테이블의 INSERT/UPDATE를 묶어서 배치 전송
//     → 엔티티 타입이 섞여도 효율적으로 배치 가능
//
// Bulk 연산(@Modifying JPQL):
//   1차 캐시를 거치지 않고 DB에 직접 실행 → 매우 빠름
//   단점: 1차 캐시와 불일치 발생 → clearAutomatically = true 필수
//
@Configuration
@EnableJpaRepositories(basePackages = "com.eomcs.advanced.jpa.exam29")
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
    emfb.setPackagesToScan("com.eomcs.advanced.jpa.exam29");

    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    adapter.setShowSql(true);
    emfb.setJpaVendorAdapter(adapter);

    Properties props = new Properties();
    props.setProperty("hibernate.format_sql",       "true");
    props.setProperty("hibernate.use_sql_comments", "true");
    props.setProperty("hibernate.hbm2ddl.auto",     "none");
    props.setProperty("hibernate.dialect",          "org.hibernate.dialect.OracleDialect");
    // JDBC 배치 처리 설정
    props.setProperty("hibernate.jdbc.batch_size",  "50");
    props.setProperty("hibernate.order_inserts",    "true");
    props.setProperty("hibernate.order_updates",    "true");
    // 통계 활성화
    props.setProperty("hibernate.generate_statistics", "true");
    emfb.setJpaProperties(props);
    return emfb;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }
}
