// 스프링 IoC 컨테이너를 설정하는 클래스
package com.eomcs.spring.ioc.ex13.g;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan("com.eomcs.spring.ioc.ex13.g")

// AOP 활성화시키기
// => XML 설정에서 <aop:aspectj-autoproxy/> 태그를 선언하는 것과 같다.
// 
@EnableAspectJAutoProxy

public class AppConfig {
}
