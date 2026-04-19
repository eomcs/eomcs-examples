package com.eomcs.advanced.jpa.exam26;

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

// exam26 - N+1 문제 & 해결
//
// N+1 문제: 1번의 쿼리로 N개의 엔티티를 가져온 뒤,
//   각 엔티티의 연관 데이터를 로드하기 위해 N번의 추가 쿼리가 실행되는 현상
//
// showSql=true로 실행 시 콘솔에서 쿼리 횟수를 직접 확인할 수 있다.
//
@Configuration
@EnableJpaRepositories(basePackages = "com.eomcs.advanced.jpa.exam26")
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
    emfb.setPackagesToScan("com.eomcs.advanced.jpa.exam26");

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
