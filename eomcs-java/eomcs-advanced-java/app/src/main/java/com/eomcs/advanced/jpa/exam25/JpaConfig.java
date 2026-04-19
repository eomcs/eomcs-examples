package com.eomcs.advanced.jpa.exam25;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// exam25 - Auditing & @EnableJpaAuditing
//
// @EnableJpaAuditing: JPA Auditing 기능 활성화
//   - auditorAwareRef: 현재 사용자를 반환하는 AuditorAware 빈 이름
//   - @CreatedDate / @LastModifiedDate → 자동으로 타임스탬프 설정
//   - @CreatedBy / @LastModifiedBy    → AuditorAware.getCurrentAuditor()로 사용자 자동 설정
//
@Configuration
@EnableJpaRepositories(basePackages = "com.eomcs.advanced.jpa.exam25")
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
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
    emfb.setPackagesToScan("com.eomcs.advanced.jpa.exam25");

    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    adapter.setShowSql(true);
    emfb.setJpaVendorAdapter(adapter);

    Properties props = new Properties();
    props.setProperty("hibernate.format_sql",       "true");
    props.setProperty("hibernate.use_sql_comments", "true");
    // exam25_customer 테이블은 shop_* 테이블에 없으므로 자동 생성/삭제
    props.setProperty("hibernate.hbm2ddl.auto",     "create-drop");
    props.setProperty("hibernate.dialect",          "org.hibernate.dialect.OracleDialect");
    emfb.setJpaProperties(props);
    return emfb;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }

  // AuditorAware: @CreatedBy / @LastModifiedBy에 채울 현재 사용자를 반환한다
  @Bean
  public AuditorAware<String> auditorAware() {
    return new AuditAwareImpl();
  }
}
