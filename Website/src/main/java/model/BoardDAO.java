package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	
	String id = "HUEKA";
	String pw = "****";
	String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
	String driver = "oracle.jdbc.driver.OracleDriver";
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public void getCon() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	// 데이터 베이스에 접근할 수 있도록 설정
	Connection con;
	// 데이터 베이스의 테이블의 결과를 리턴 받아 자바에 저장해주는 객체 
	ResultSet rs;
	// 데이터 베이스에서 쿼리를 실행시켜주는 객체
	PreparedStatement pstmt;

	// 데이터 베이스의 커넥션들을 사용하도록 설정하는 메서드
	public void getCon() {
		// 커넥션 풀을 이용하여 데이터 베이스에 접근
		try {
			// 외부에서 데이터를 읽어들여야 하기에
			Context initctx = new InitialContext();
			// 톰캣 서버에 정보를 담아 놓는 곳으로 이동
			Context envctx = (Context) initctx.lookup("java:comp/env");
			// 데이터 소스 객체를 선언
			DataSource ds = (DataSource)envctx.lookup("jdbc/pool");
			// 데이터 소스를 기준으로 커넥션을 연결해주시오.
			con = ds.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	// 하나의 새로운 게시글이 넘어와서 저장되는 메소드
	public void insertBoard(BoardBean bean) {
		getCon();
		// 빈 클래스에 넘어오지 않았던 데이터들을 초기화 해줘야한다.
		// ref 글 그룹 reStep 글 스텝(순서) reLevel 글 레벨
		// 글 그룹 의미 = 쿼리를 실행시켜서 가장 큰 ref 값을 가져온 후 + 1을 더해주면 됨
		int ref = 0;
		// 새 글 이기에 1
		int reStep = 1; 
		int reLevel = 1;
		
		try {
			// 가장 큰 ref 값을 읽어오는 쿼리 준비
			String refSql = "SELECT MAX(REF) FROM BOARD";
			// 쿼리 설정 객체
			pstmt = con.prepareStatement(refSql);
			// 쿼리 실행 후 결과를 리턴
			rs = pstmt.executeQuery();
			if(rs.next()) {
				ref = rs.getInt(1) + 1;
			}
			// 실제로 게시글 전체 값을 테이블에 저장 시퀀스의 다음 값을 자동으로 매핑
			String sql = "INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,?,?,?,?,SYSDATE,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			// ?에 값을 매핑
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref);
			pstmt.setInt(6, reStep);
			pstmt.setInt(7, reLevel);
			pstmt.setString(8, bean.getContent());
			// 쿼리를 실행
			pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 모든 게시글들을 리턴해주는 메서드
	public ArrayList<BoardBean> getAllBoard(int start, int end) {
		// 리턴 객체 선언
		ArrayList<BoardBean> list = new ArrayList<>();
		getCon();
		try {
			// 쿼리 준비
			String sql = "SELECT * FROM ("
					+ "SELECT A.*, ROWNUM RNUM FROM ("
					+ "SELECT * FROM BOARD ORDER BY REF DESC, RE_STEP ASC) A)"
					+ " WHERE RNUM >= ? AND RNUM <= ?";
			// 쿼리를 실행할 객체 선언
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			// 쿼리 실행 후 결과 저장
			rs = pstmt.executeQuery();
			// 데이터 개수가 몇개인지 모르기에 반복문을 이용하여 데이터를 추출
			while(rs.next()) {
				// 데이터를 패키징 (가방 = BoardBean 클래스 이용)
				BoardBean bean = new BoardBean();
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				// date를 스트링으로 가져온다.
				bean.setRegDate(rs.getDate(6).toString()); 
				bean.setRef(rs.getInt(7));
				bean.setReStep(rs.getInt(8));
				bean.setReLevel(rs.getInt(9));
				bean.setReadCount(rs.getInt(10));
				bean.setContent(rs.getString(11));
				// 패키징한 데이터를 ArrayList에 저장
				list.add(bean);
			}
			con.close();	// 자원 반납
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// BoardInfo 하나의 게시글을 리턴하는 메소드
	public BoardBean getOneBoard(int num) {
		// 리턴 타입 선언
		BoardBean bean = new BoardBean();
		getCon();
		
		try {
			// 조회수 증가 쿼리
			String readSql = "UPDATE BOARD SET READCOUNT = READCOUNT + 1 WHERE NUM = ?";
			pstmt = con.prepareStatement(readSql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		
			// 쿼리 준비
			String sql = "SELECT * FROM BOARD WHERE NUM = ?";
			// 쿼리 실횅 객체
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			// 쿼리 실행 후 결과를 리턴
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setRegDate(rs.getDate(6).toString()); 
				bean.setRef(rs.getInt(7));
				bean.setReStep(rs.getInt(8));
				bean.setReLevel(rs.getInt(9));
				bean.setReadCount(rs.getInt(10));
				bean.setContent(rs.getString(11));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	// 답변글이 저장되는 메서드
	public void reWriteBoard(BoardBean bean) {
		// 부모글의 글그룹, 글스탭, 글 스텝 읽어들임
		int ref = bean.getRef();
		int reStep = bean.getReStep();
		int reLevel = bean.getReLevel();
		
		getCon();
		
		try {
			/* 핵심 코드*/
			// 부모보다 큰 reLevel의 값을 전부 1씩 증가 시켜줌
			String levelSql = "UPDATE BOARD SET RE_LEVEL = RE_LEVEL + 1 WHERE REF = ? AND RE_LEVEL > ?";
			// 쿼리 실행 객체 선언
			pstmt = con.prepareStatement(levelSql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, reLevel);
			// 쿼리 실행 
			pstmt.executeUpdate();
			// 답변글 데이터를 저장
			String sql = "INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,?,?,?,?,SYSDATE,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			// ?에 값을 대입
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref); // 부모의 ref 값을 넣어줌
			pstmt.setInt(6, reStep+1); // 답글이기에 부모글 reStep + 1을 넣어줌
			pstmt.setInt(7, reLevel+1);
			pstmt.setString(8, bean.getContent());
			
			// 쿼리를 실행하시오.
			pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// BoardUpdate용 Delete 시 하나의 게시글을 리턴하는 메소드 조회수 올라가면 안되는 update 모드
	public BoardBean getOneUpdateBoard(int num) {
		// 리턴 타입 선언
		BoardBean bean = new BoardBean();
		getCon();
		
		try {
			// 쿼리 준비
			String sql = "SELECT * FROM BOARD WHERE NUM = ?";
			// 쿼리 실행 객체
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			// 쿼리 실행 후 결과를 리턴
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setRegDate(rs.getDate(6).toString()); 
				bean.setRef(rs.getInt(7));
				bean.setReStep(rs.getInt(8));
				bean.setReLevel(rs.getInt(9));
				bean.setReadCount(rs.getInt(10));
				bean.setContent(rs.getString(11));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	// update와 delete 시 필요한 비밀번호 값을 리턴해주는 메서드
	public String getPass(int num) {
		// 리턴할 변수 객체 선언
		String pass = "";
		getCon();
		
		try {
			//쿼리 준비
			String sql = "SELECT PASSWORD FROM BOARD WHERE NUM = ?";
			// 쿼리 실행할 객체 선언
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			// 비밀번호 값을 저장
			if(rs.next()) {
				pass = rs.getString(1);
			}
			// 자원 반납
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pass;
	}
	
	// 하나의 게시글을 수정하는 메서드
 	public void updateBoard(BoardBean bean) {
 		getCon();
 		
 		try {
 			// 쿼리 준비
 			String sql = "UPDATE BOARD SET SUBJECT = ?, CONTENT = ? WHERE NUM = ?";
 			pstmt = con.prepareStatement(sql);
 			// ? 값을 대입
 			pstmt.setString(1, bean.getSubject());
 			pstmt.setString(2, bean.getContent());
 			pstmt.setInt(3, bean.getNum());
 			
 			pstmt.executeUpdate();
 			con.close();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}
 	// 하나의 게시글을 삭제하는 메서드
 	public void deleteBoard(int num) {
 		getCon();
 		try {
 			String sql = "DELETE FROM BOARD WHERE NUM = ?";
 			pstmt = con.prepareStatement(sql);
 			// ? 매핑
 			pstmt.setInt(1, num);
 			// 쿼리 실행
 			pstmt.executeUpdate();
 			// 자원반납
 			con.close();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}
 	 
 	// 전체글의 갯수를 리턴하는 메소드
 	public int getAllCount() {
 		getCon();
 		// 게시글 전체수를 저장하는 변수
 		int count = 0;
 		
 		try {
 			// 쿼리 준비
 			String sql = "SELECT COUNT(*) FROM BOARD";
 			// 쿼리를 실행할 객체 선언
 			pstmt = con.prepareStatement(sql);
 			// 쿼리 실행 후 결과를 리턴
 			rs = pstmt.executeQuery();
 			if(rs.next()) {
 				count = rs.getInt(1);
 			}
 			// 자원 반납
 			con.close();
 		} catch (Exception e) {
			e.printStackTrace();
		}
 		return count;
 	}
}
