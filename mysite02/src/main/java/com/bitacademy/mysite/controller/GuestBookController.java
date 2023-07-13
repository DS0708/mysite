package com.bitacademy.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.mysite.dao.GuestBookDao;
import com.bitacademy.mysite.vo.GuestBookVo;

public class GuestBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String actionName = request.getParameter("a");
		
		//System.out.println(actionName);
		
		if("insert".equals(actionName)){
			//System.out.println("insert");
			
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String text = request.getParameter("content");
			
			GuestBookVo vo = new GuestBookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setText(text);
			
			new GuestBookDao().insert(vo);
			
			response.sendRedirect(request.getContextPath()+"/guestbook");
		}else if("delete".equals(actionName)){
			Long no = Long.parseLong(request.getParameter("no"));
			String password = request.getParameter("password");
			
			new GuestBookDao().delete(no,password);
			
			response.sendRedirect(request.getContextPath()+"/guestbook");
		}
		else {
			System.out.println("else");
			List<GuestBookVo> list = new GuestBookDao().findAll();
			
			request.setAttribute("list", list);
			request.getRequestDispatcher("WEB-INF/views/guestbook/list.jsp")
				.forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
