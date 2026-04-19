package com.eomcs.advanced.jpa.exam27;

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

// exam27 - 지연 로딩(Lazy) vs 즉시 로딩(Eager)
//
// FetchType.LAZY  (기본값 @OneToMany): 실제 접근 시 SELECT 실행
// FetchType.EAGER (기본값 @ManyToOne): 부모 로드 시 항상 JOIN하여 함께 로드
//
// OSIV(Open Session In View):
//   웹 환경에서 HTTP 요청 전체 동안 세션을 열어 두는 패턴.
//   → View 렌더링 시 LAZY 로딩 허용
//   → 단점: DB 커넥션을 오래 점유, 예상치 못한 쿼리 발생 위험
//   비웹 환경(이 예제)에서는 @Transactional 범위가 세션 범위와 일치한다.
//
@Configuration
@ComponentScan(basePackages = "com.eomcs.advanced.jpa.exam27")
@EnableJpaRepositories(basePackages = "com.eomcs.advanced.jpa.exam27")
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
    emfb.setPackagesToScan("com.eomcs.advanced.jpa.exam27");

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

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }
}
