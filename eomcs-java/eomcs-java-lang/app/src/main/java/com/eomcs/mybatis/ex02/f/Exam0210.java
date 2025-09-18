// SqlSession.selectOne() 사용법 - 결과 값을 Map 객체로 받기
package com.eomcs.mybatis.ex02.f;

import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Exam0210 {

  public static void main(String[] args) throws Exception {
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(
        "com/eomcs/mybatis/ex02/f/mybatis-config.xml")).openSession();

    // Map 객체로 받으려면 SQL 매퍼에서 다음과 같이 resultType을 설정해야 한다.
    //    <select id="selectBoard3" resultType="map" parameterType="int">
    //      select 
    //        board_id,
    //        title,    
    //        contents,
    //        created_date, 
    //        view_count  
    //      from x_board
    //      where board_id=#{no}
    //    </select>
    // 
    // - 컬럼 이름(이나 별명)으로 컬럼 값을 저장하기 때문에 
    //   map 객체에 컬럼 값을 꺼낼 때 컬럼 이름(이나 별명)을 사용해야 한다.
    // 
    Map<String,Object> map = sqlSession.selectOne("BoardMapper.selectBoard3");

    if (map == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    System.out.printf("%d,%s,%s,%s,%d\n",
        map.get("board_id"),
        map.get("title"),
        map.get("contents"),
        map.get("created_date"),
        map.get("view_count"));

    sqlSession.close();
  }

}


