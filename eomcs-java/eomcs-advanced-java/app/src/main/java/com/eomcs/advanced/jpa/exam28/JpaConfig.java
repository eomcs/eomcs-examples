package com.eomcs.advanced.jpa.exam28;

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

// exam28 - 2차 캐시 (Ehcache 3 + JCache)
//
// 2차 캐시(L2C) 설정 키:
//   hibernate.cache.use_second_level_cache = true  : 엔티티 캐시 활성화
//   hibernate.cache.use_query_cache         = true  : 쿼리 결과 캐시 활성화
//   hibernate.cache.region.factory_class    = jcache : JCache 프로바이더 사용
//   hibernate.javax.cache.provider          = EhcacheCachingProvider 클래스명
//   hibernate.javax.cache.uri               = ehcache.xml 경로
//   hibernate.generate_statistics           = true  : 캐시 히트/미스 통계 출력
//
// @Cache(usage = READ_WRITE)로 표시된 엔티티만 L2C에 저장된다.
//
@Configuration
@EnableJpaRepositories(basePackages = "com.eomcs.advanced.jpa.exam28")
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
    emfb.setPackagesToScan("com.eomcs.advanced.jpa.exam28");

    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    adapter.setShowSql(true);
    emfb.setJpaVendorAdapter(adapter);

    Properties props = new Properties();
    props.setProperty("hibernate.format_sql",                  "true");
    props.setProperty("hibernate.use_sql_comments",            "true");
    props.setProperty("hibernate.hbm2ddl.auto",                "none");
    props.setProperty("hibernate.dialect",                     "org.hibernate.dialect.OracleDialect");
    // 2차 캐시 설정
    props.setProperty("hibernate.cache.use_second_level_cache","true");
    props.setProperty("hibernate.cache.use_query_cache",       "true");
    props.setProperty("hibernate.cache.region.factory_class",  "jcache");
    props.setProperty("hibernate.javax.cache.provider",
        "org.ehcache.jsr107.EhcacheCachingProvider");
    props.setProperty("hibernate.javax.cache.uri",
        "ehcache.xml");
    // 통계 활성화: 캐시 히트/미스 확인
    props.setProperty("hibernate.generate_statistics",         "true");
    emfb.setJpaProperties(props);
    return emfb;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }
}
