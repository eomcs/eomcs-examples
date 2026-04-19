package com.eomcs.advanced.jpa.exam23;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
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

// exam23 - Querydsl 기초: Spring 컨텍스트 설정
//
// JPAQueryFactory 빈을 등록해 두면 Spring 컴포넌트에서 @Autowired로 주입받을 수 있다.
// EntityManager는 Spring이 트랜잭션 범위에 맞는 프록시를 자동으로 주입해준다.
//
@Configuration
@EnableJpaRepositories(basePackages = "com.eomcs.advanced.jpa.exam23")
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
    emfb.setPackagesToScan("com.eomcs.advanced.jpa.exam23");

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

  // JPAQueryFactory: Querydsl 쿼리를 작성하는 진입점
  // EntityManager 파라미터는 Spring이 트랜잭션 범위 프록시를 주입한다
  @Bean
  public JPAQueryFactory jpaQueryFactory(EntityManager em) {
    return new JPAQueryFactory(em);
  }
}
