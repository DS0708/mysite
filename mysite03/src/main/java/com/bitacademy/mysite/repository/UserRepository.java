package com.bitacademy.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bitacademy.mysite.vo.UserVo;


public class UserRepository {
	public boolean insert(UserVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement auto_pstmt = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "insert into user values (null,?,?,password(?),?,now())";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,vo.getName());	
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3,vo.getPassword());	
			pstmt.setString(4, vo.getGender());
			
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
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();

			String sql = "delete from guestbook where no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			int count = pstmt.executeUpdate();
			
			result = count ==1;
			
		} catch (SQLException e) {
			System.out.println("Error"+e);
		} finally {
			try {
				if(conn!=null)	conn.close();
				if(pstmt!=null)	pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean delete(Long no, String password) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "delete from guestbook where no=? and password = password(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			pstmt.setString(2, password);
			int count = pstmt.executeUpdate();
			
			result = count ==1 ;
		} catch (SQLException e) {
			System.out.println("Error"+e);
		} finally {
			try {
				if(conn!=null)	conn.close();
				if(pstmt!=null)	pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}


	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select no, name from user where email = ? and password = password(?)";
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			rs = stmt.executeQuery();	

			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
				
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
	
	public UserVo findByNo(Long no) {
		UserVo result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select name, email, gender from user where no = ?";
			stmt = conn.prepareStatement(sql);
			
			stmt.setLong(1, no);
			
			rs = stmt.executeQuery();	

			if(rs.next()) {
				String name = rs.getString(1);
				String email = rs.getString(2);
				String gender = rs.getString(3);
				
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);
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


	@SuppressWarnings("resource")
	public boolean update(UserVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "";
			
			if("".equals(vo.getPassword())) {
				sql = "update user set name = ?, gender = ? where no = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, vo.getName());
				stmt.setString(2, vo.getGender());
				stmt.setLong(3, vo.getNo());
			}else {
				sql = "update user set name = ?, gender = ?, password = password(?) where no = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, vo.getName());
				stmt.setString(2, vo.getGender());
				stmt.setString(3, vo.getPassword());
				stmt.setLong(4, vo.getNo());
			}
			
			int count = stmt.executeUpdate();	

			result = count > 0 ;
			
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
