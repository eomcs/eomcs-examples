package com.eomcs.advanced.jpa.exam21;

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

// exam21 - Spring Data JPA 기초: Spring 컨텍스트 설정
//
// Spring Data JPA (Spring Boot 없이) 동작에 필요한 세 가지 핵심 빈:
//   1. DataSource          : DB 연결 풀 (HikariCP)
//   2. EntityManagerFactory: JPA 영속성 단위 (LocalContainerEntityManagerFactoryBean)
//   3. TransactionManager  : 트랜잭션 관리 (JpaTransactionManager)
//
// @EnableJpaRepositories: 지정 패키지에서 JpaRepository 구현체를 자동 생성
// @EnableTransactionManagement: @Transactional AOP 활성화
//
@Configuration
@EnableJpaRepositories(basePackages = "com.eomcs.advanced.jpa.exam21")
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

  // LocalContainerEntityManagerFactoryBean: Spring이 관리하는 EntityManagerFactory 래퍼
  //   - setPackagesToScan(): persistence.xml 대신 지정 패키지에서 @Entity를 자동 탐색
  //   - HibernateJpaVendorAdapter: Hibernate를 JPA 구현체로 등록
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
    emfb.setDataSource(dataSource);
    emfb.setPackagesToScan("com.eomcs.advanced.jpa.exam21");

    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    adapter.setShowSql(true);
    emfb.setJpaVendorAdapter(adapter);

    Properties props = new Properties();
    props.setProperty("hibernate.format_sql",       "true");
    props.setProperty("hibernate.use_sql_comments", "true");
    props.setProperty("hibernate.hbm2ddl.auto",     "none");
    props.setProperty("hibernate.dialect",          "org.hibernate.dialect.OracleDialect");
    emfb.setJpaProperties(props);
    return emfb;
  }

  // JpaTransactionManager: EntityManagerFactory와 연동하는 트랜잭션 관리자
  // Spring Data JPA 리포지토리가 @Transactional로 자동 트랜잭션을 적용할 때 이 빈을 사용한다
  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }
}
