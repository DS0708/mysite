package com.bitacademy.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.mysite.vo.GuestBookVo;

public class BoardDao {
	
	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select a.no, a.title, b.name, a.hit, a.reg_date\n"
					+ "from board a, user b\n"
					+ "where a.user_no = b.no "
					+ "order by a.no desc";
			stmt = conn.prepareStatement(sql);
			
			rs = stmt.executeQuery();	

			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String user_name = rs.getString(3);
				Long hit = rs.getLong(4);
				String reg_date = rs.getString(5);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setUser_name(user_name);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				
				result.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("Error"+e);
		} finally {
			try {
				if(rs!=null)	rs.close();
				if(conn!=null)	conn.close();
				if(stmt!=null)	stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}



	public boolean insert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement auto_pstmt = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "insert into board \n"
					+ "values(null,?,?,0,now(),0,0,0,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,vo.getTitle());	
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUser_no());
			
			int count = pstmt.executeUpdate();
			
			result = count ==1 ;
			
		} catch (SQLException e) {
			System.out.println("Error"+e);
		} finally {
			try {
				if(conn!=null)	conn.close();
				if(auto_pstmt!=null)	auto_pstmt.close();
				if(pstmt!=null)	pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}



	public BoardVo findByNo(Long no) {
		BoardVo result = new BoardVo();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select  title, contents, user_no\n"
					+ "from board\n"
					+ "where no = ? ;";
			stmt = conn.prepareStatement(sql);
			
			stmt.setLong(1, no);
			
			rs = stmt.executeQuery();	

			if(rs.next()) {
				String title = rs.getString(1);
				String contents = rs.getString(2);
				Long user_no = rs.getLong(3);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setUser_no(user_no);
				
				result = vo;
			}
			
		} catch (SQLException e) {
			System.out.println("Error"+e);
		} finally {
			try {
				if(rs!=null)	rs.close();
				if(conn!=null)	conn.close();
				if(stmt!=null)	stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}



	public boolean update(String title, String content, Long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement auto_pstmt = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "update board\n"
					+ "set title = ?, contents = ? \n"
					+ "where no = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setLong(3, no);
			
			int count = pstmt.executeUpdate();
			
			result = count ==1 ;
			
		} catch (SQLException e) {
			System.out.println("Error"+e);
		} finally {
			try {
				if(conn!=null)	conn.close();
				if(auto_pstmt!=null)	auto_pstmt.close();
				if(pstmt!=null)	pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}



	public boolean delete(Long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement auto_pstmt = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "delete from board\n"
					+ "where no = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			int count = pstmt.executeUpdate();
			
			result = count ==1 ;
			
		} catch (SQLException e) {
			System.out.println("Error"+e);
		} finally {
			try {
				if(conn!=null)	conn.close();
				if(auto_pstmt!=null)	auto_pstmt.close();
				if(pstmt!=null)	pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean addHit(Long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement auto_pstmt = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "update board\n"
					+ "set hit = hit+1\n"
					+ "where no = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			int count = pstmt.executeUpdate();
			
			result = count ==1 ;
			
		} catch (SQLException e) {
			System.out.println("Error"+e);
		} finally {
			try {
				if(conn!=null)	conn.close();
				if(auto_pstmt!=null)	auto_pstmt.close();
				if(pstmt!=null)	pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private Connection getConnection() throws SQLException {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		
		//String url = "jdbc:mariadb://192.168.0.153:3306/webdb?charset=utf8";
		String url = "jdbc:mariadb://192.168.0.12:3306/webdb?charset=utf8";
	
		
		return DriverManager.getConnection(url,"webdb","webdb");
		
	}



}
