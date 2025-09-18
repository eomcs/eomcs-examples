package com.eomcs.reflect.ex06.f;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

public class BoardDaoGenerator implements FactoryBean<BoardDao>{
  @Autowired SqlSessionFactory sqlSessionFactory;

  @Override
  public BoardDao getObject() throws Exception {
    return (BoardDao) Proxy.newProxyInstance(
        this.getClass().getClassLoader(),
        new Class[] {BoardDao.class},
        new InvocationHandler() {
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Class<?> interfaceType = proxy.getClass().getInterfaces()[0];
            String namespace = interfaceType.getName();
            String sqlId = method.getName();
            String statementName = namespace + "." + sqlId;

            Class<?> returnType = method.getReturnType();

            SqlSession sqlSession = sqlSessionFactory.openSession();

            if (returnType == void.class || returnType == int.class) {
              if (args.length == 0) {
                return sqlSession.insert(statementName);
              } else {
                return sqlSession.insert(statementName, args[0]);
              }
            } else if (returnType == List.class) {
              if (args.length == 0) {
                return sqlSession.selectList(statementName);
              } else {
                return sqlSession.selectList(statementName, args[0]);
              }
            } else {
              if (args.length == 0) {
                return sqlSession.selectOne(statementName);
              } else {
                return sqlSession.selectOne(statementName, args[0]);
              }
            }
          }
        });
  }

  @Override
  public Class<?> getObjectType() {
    return BoardDao.class;
  }
}
