<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% response.setHeader("Access-Control-Allow-Origin", "*");%>
{
  "status": "success",
  "data": ${Integer.parseInt(param.a) + Integer.parseInt(param.b)}
}







