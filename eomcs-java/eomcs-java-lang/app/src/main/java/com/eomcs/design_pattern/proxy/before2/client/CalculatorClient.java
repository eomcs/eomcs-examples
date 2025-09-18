package com.eomcs.design_pattern.proxy.before2.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

// 클라이언트 요청을 서버에 전달하고 서버의 작업 결과를 클라이언트에게 리턴하는 일을 한다.
// => 즉 중계인 역할을 수행한다.
// => 이렇게 원격에서 객체를 사용할 수 있도록 중계인의 역할을 수행하는 객체를
//    "Object Request Broker(ORB)"라 부른다.
// => 객체가 있는 서버 측에서 요청과 응답을 대행하는 ORB를 "스켈레톤(skeleton)"이라 부른다.
// => 객체를 사용하는 클라이언트 측에서 요청과 응답을 대행하는 ORB를 "스텁(stub)"이라 부른다.
//
public class CalculatorClient {

  public int compute(int a, int b, String op) throws Exception {
    try (Socket s = new Socket("localhost", 8888);
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        DataInputStream in = new DataInputStream(s.getInputStream())) {

      out.writeInt(a);
      out.writeInt(b);
      out.writeUTF(op);
      
      if (in.readUTF().equalsIgnoreCase("OK")) {
        return in.readInt();
      } else {
        throw new RuntimeException(in.readUTF());
      }
    }
  }
}
