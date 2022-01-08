package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MemberDAO {
	String id = "HUEKA";
	String pw = "0814";
	String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
	String driver = "oracle.jdbc.driver.OracleDriver";
	
	// 데이터베이스에 접근할수 있도록 설정 
	Connection con;
	// SQL문 실행하기 위해 사용 - 데이터 베이스에서 쿼리를 실행시켜주는 객체 
	PreparedStatement pstmt;
	// SQL문 결과를 얻어오기 위해 사용 - 데이터 베이스의 테이블의 결과를 리턴 받아 자바에 저장해 주는 객체
	ResultSet rs;
	
	// 데이터 베이스에 접근할 수 있도록 도와주는 메서드
	public void getCon() {
		try {
			// 드라이버 로드
			Class.forName(driver);
			// DBMS와 Connection 연결
			con = DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 데이터 베이스에 한 사람 회원 정보를 저장해주는 메서드
	public void insertMember(MemberBean mbean) {
		try {
			getCon();
			String sql = "INSERT INTO MEMBERSHIP VALUES(?,?,?,?,?,?,?)";
			// 쿼리를 사용하도록 설정
			pstmt = con.prepareStatement(sql);
			// ?에 맞게 데이터를 맵핑
			pstmt.setString(1, mbean.getId());
			pstmt.setString(2, mbean.getPw());
			pstmt.setString(3, mbean.getEmail());
			pstmt.setString(4, mbean.getTel());
			pstmt.setString(5, mbean.getHobby());
			pstmt.setString(6, mbean.getJob());
			pstmt.setString(7, mbean.getInfo());
			// 오라클에서 쿼리 실행
			pstmt.executeUpdate();
			// 자원 반납
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 모든 회원 정보를 리턴하는 메소드
	public ArrayList<MemberBean> allSelectMember() {
		ArrayList<MemberBean> list = new ArrayList<>();
		try {
			// 커넥션 연결
			getCon();
			// 쿼리 준비
			String sql = "SELECT * FROM MEMBERSHIP";
			// 쿼리를 실행시켜주는 객체 선언
			pstmt = con.prepareStatement(sql);
			// 쿼리를 실행시킨 결과를 리턴해서 받아줌
			rs = pstmt.executeQuery();
			// 반복문을 사용해서 rs에 저장된 데이터를 추출해 놓아야한다.
			while(rs.next()) {
				MemberBean bean = new MemberBean();
				bean.setId(rs.getString(1));
				bean.setPw(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setTel(rs.getString(4));
				bean.setHobby(rs.getString(5));
				bean.setJob(rs.getString(6));
				bean.setInfo(rs.getString(7));
				list.add(bean);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 한 사람의 정보를 리턴하는 메서드
	public MemberBean oneSelectMember(String id) {
		MemberBean bean = new MemberBean();
		
		try {
			getCon();
			String sql = "SELECT * FROM MEMBERSHIP WHERE ID = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setId(rs.getString(1));
				bean.setPw(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setTel(rs.getString(4));
				bean.setHobby(rs.getString(5));
				bean.setJob(rs.getString(6));
				bean.setInfo(rs.getString(7));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	// 한 회원의 패스워드 값을 리턴하는 메서드 작성
	public String getPassword(String id) {
		String password= "";
		try {
			getCon();
			String sql = "SELECT PW FROM MEMBERSHIP WHERE ID = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				password = rs.getString(1);
			}
			// 자원 반납
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return password;
	}
	
	// 한 회원의 정보를 수정하는 메서드
	public void updateMember(MemberBean bean) {
		getCon();
		try {
			String sql = "UPDATE MEMBERSHIP SET EMAIL = ?, TEL = ? WHERE ID = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getEmail());
			pstmt.setString(2, bean.getTel());
			pstmt.setString(3, bean.getId());
			pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 한 회원을 삭제하는 메서드 작성
	public void deleteMember(String id) {
		getCon();
		
		try {
			String sql = "DELETE FROM MEMBERSHIP WHERE ID = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
