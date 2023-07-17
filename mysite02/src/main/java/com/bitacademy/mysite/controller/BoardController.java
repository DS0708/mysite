package com.bitacademy.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitacademy.mysite.dao.BoardDao;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.mysite.vo.UserVo;


public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String actionName = request.getParameter("a");
		
		if("write".equals(actionName)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			Long user_no = authUser.getNo();
			
			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(content);
			vo.setUser_no(user_no);
			
			boolean check = new BoardDao().insert(vo);
			if(!check) System.out.println("insert error");
			
			response.sendRedirect(request.getContextPath()+"/board?c=1");
		}else if("writeform".equals(actionName)) {
			request.getRequestDispatcher("WEB-INF/views/board/writeform.jsp").forward(request, response);
		}else if("modifyform".equals(actionName)) {
			Long no = Long.parseLong(request.getParameter("no"));
			BoardVo vo = new BoardDao().findByNo(no);
			
			request.setAttribute("vo", vo);
			request.getRequestDispatcher("WEB-INF/views/board/modifyform.jsp").forward(request, response);
		}else if("modify".equals(actionName)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			Long no = Long.parseLong(request.getParameter("no"));
			
			new BoardDao().update(title,content,no);
			
			response.sendRedirect(request.getContextPath()+"/board?a=view&&no="+no);
		}
		else if("delete".equals(actionName)) {
			Long no = Long.parseLong(request.getParameter("no"));
			
			new BoardDao().delete(no);
			
			response.sendRedirect(request.getContextPath()+"/board");
		}else if("view".equals(actionName)) {
			Long no = Long.parseLong(request.getParameter("no"));
			BoardVo vo = new BoardDao().findByNo(no);
			
			new BoardDao().addHit(no);
			
			request.setAttribute("vo", vo);
			request.getRequestDispatcher("WEB-INF/views/board/view.jsp").forward(request, response);
		}
		else {
			List<BoardVo> list = new BoardDao().findAll();
			
			int c = 1;
			if(request.getParameter("c") != null) {
				c = Integer.parseInt(request.getParameter("c"));
			}
			
			request.setAttribute("list", list);
			request.getRequestDispatcher("/WEB-INF/views/board/list.jsp?c="+c).forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
