package com.bitacademy.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitacademy.mysite.dao.UserDao;
import com.bitacademy.mysite.vo.UserVo;


public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String actionName = request.getParameter("a");
		
		if("joinform".equals(actionName)) {
			request
				.getRequestDispatcher("WEB-INF/views/user/joinform.jsp")
				.forward(request, response);
		}else if("joinsuccess".equals(actionName)) {
			request
				.getRequestDispatcher("WEB-INF/views/user/joinsuccess.jsp")
				.forward(request, response);
		}else if("join".equals(actionName)) {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			UserVo vo = new UserVo();
			vo.setName(name);
			vo.setEmail(email);
			vo.setPassword(password);
			vo.setGender(gender);
			
			UserDao dao = new UserDao();
			dao.insert(vo);
			
			response.sendRedirect(request.getContextPath() + "/user?a=joinsuccess");
		}else if("loginform".equals(actionName)) {
			request
			.getRequestDispatcher("WEB-INF/views/user/loginform.jsp")
			.forward(request, response);
		}else if("login".equals(actionName)) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			UserVo authUser = new UserDao().findByEmailAndPassword(email,password);
			
			if(authUser == null) {
				//response.sendRedirect(request.getContextPath()+"/user?a=loginform");	
				
				request.setAttribute("result", "fail");
				request.getRequestDispatcher("WEB-INF/views/user/loginform.jsp")
					.forward(request, response);	//String 형변환 필요 
				return ;
			}
			
			//로그인 처리 
			HttpSession session = request.getSession();
			session.setAttribute("authUser", authUser);
			
			response.sendRedirect(request.getContextPath());
		}else if("logout".equals(actionName)) {
			HttpSession session = request.getSession();
			
			session.removeAttribute("authUser");
			session.invalidate();
			
			response.sendRedirect(request.getContextPath());
		}else if("updateform".equals(actionName)) {
			// Access Control (Security)
			/////////////////////////////////////////////////////////////
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			if(authUser == null) {		//로그인을 안하고 들어오는 이상한 접근임
				response.sendRedirect(request.getContextPath());
				return;
			}
			///////////////////////////////////////////////////////////
			
			//비밀번호가 빈 문자열이면 gender 와 name만 바꾸고 아닐땐 다 바꾸게 쿼리 
			
			UserVo vo = new UserDao().findByNo(authUser.getNo());
			
			request.setAttribute("vo",vo);
			request
			.getRequestDispatcher("WEB-INF/views/user/updateform.jsp")
			.forward(request, response);
		}else if("update".equals(actionName)) {
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			authUser.setName(name);
			authUser.setPassword(password);
			authUser.setGender(gender);
			
			
			UserDao dao = new UserDao();
			
			boolean check = dao.update(authUser);
			
			if(!check) System.out.println("update error");
			
			request.setAttribute("vo",authUser);
			request
			.getRequestDispatcher("WEB-INF/views/user/updateform.jsp")
			.forward(request, response);
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
