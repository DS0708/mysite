<%@page import="com.bitacademy.mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	/* UserVo vo = new UserVo(); 
vo = (UserVo)session.getAttribute("authUser"); */
%>
<div id="navigation">
			<ul>
				<li><a href="<%=request.getContextPath() %>">신동성</a></li>
				<li><a href="<%=request.getContextPath() %>/guestbook">방명록</a></li>
				<li><a href="<%=request.getContextPath() %>/board">게시판</a></li>
			</ul>
		</div>