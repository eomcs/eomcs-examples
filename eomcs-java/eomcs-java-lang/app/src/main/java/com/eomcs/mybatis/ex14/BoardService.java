package com.eomcs.mybatis.ex14;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {

  @Autowired BoardDao boardDao;

  @Transactional
  public void test1(Board b1, Board b2, Board b3, Board b4, Board b5) {
    boardDao.insert(b1);
    System.out.printf("test1(): %d번 게시물 입력!\n", b1.getNo());

    boardDao.insert(b2);
    System.out.printf("test1(): %d번 게시물 입력!\n", b2.getNo());

    test2(b3, b4);

    boardDao.insert(b5);
    System.out.printf("test1(): %d번 게시물 입력!\n", b5.getNo());
  }

  @Transactional(propagation=Propagation.REQUIRES_NEW)
  public void test2(Board b3, Board b4) {
    boardDao.insert(b3);
    System.out.printf("test2() ====> %d번 게시물 입력!\n", b3.getNo());

    boardDao.insert(b4);
    System.out.printf("test2() ====> %d번 게시물 입력!\n", b4.getNo());
  }
}
