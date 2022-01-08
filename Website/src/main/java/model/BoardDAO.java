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
	String pw = "0814";
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
	// ������ ���̽��� ������ �� �ֵ��� ����
	Connection con;
	// ������ ���̽��� ���̺��� ����� ���� �޾� �ڹٿ� �������ִ� ��ü 
	ResultSet rs;
	// ������ ���̽����� ������ ��������ִ� ��ü
	PreparedStatement pstmt;

	// ������ ���̽��� Ŀ�ؼǵ��� ����ϵ��� �����ϴ� �޼���
	public void getCon() {
		// Ŀ�ؼ� Ǯ�� �̿��Ͽ� ������ ���̽��� ����
		try {
			// �ܺο��� �����͸� �о�鿩�� �ϱ⿡
			Context initctx = new InitialContext();
			// ��Ĺ ������ ������ ��� ���� ������ �̵�
			Context envctx = (Context) initctx.lookup("java:comp/env");
			// ������ �ҽ� ��ü�� ����
			DataSource ds = (DataSource)envctx.lookup("jdbc/pool");
			// ������ �ҽ��� �������� Ŀ�ؼ��� �������ֽÿ�.
			con = ds.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	// �ϳ��� ���ο� �Խñ��� �Ѿ�ͼ� ����Ǵ� �޼ҵ�
	public void insertBoard(BoardBean bean) {
		getCon();
		// �� Ŭ������ �Ѿ���� �ʾҴ� �����͵��� �ʱ�ȭ ������Ѵ�.
		// ref �� �׷� reStep �� ����(����) reLevel �� ����
		// �� �׷� �ǹ� = ������ ������Ѽ� ���� ū ref ���� ������ �� + 1�� �����ָ� ��
		int ref = 0;
		// �� �� �̱⿡ 1
		int reStep = 1; 
		int reLevel = 1;
		
		try {
			// ���� ū ref ���� �о���� ���� �غ�
			String refSql = "SELECT MAX(REF) FROM BOARD";
			// ���� ���� ��ü
			pstmt = con.prepareStatement(refSql);
			// ���� ���� �� ����� ����
			rs = pstmt.executeQuery();
			if(rs.next()) {
				ref = rs.getInt(1) + 1;
			}
			// ������ �Խñ� ��ü ���� ���̺� ���� �������� ���� ���� �ڵ����� ����
			String sql = "INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,?,?,?,?,SYSDATE,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			// ?�� ���� ����
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref);
			pstmt.setInt(6, reStep);
			pstmt.setInt(7, reLevel);
			pstmt.setString(8, bean.getContent());
			// ������ ����
			pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// ��� �Խñ۵��� �������ִ� �޼���
	public ArrayList<BoardBean> getAllBoard(int start, int end) {
		// ���� ��ü ����
		ArrayList<BoardBean> list = new ArrayList<>();
		getCon();
		try {
			// ���� �غ�
			String sql = "SELECT * FROM ("
					+ "SELECT A.*, ROWNUM RNUM FROM ("
					+ "SELECT * FROM BOARD ORDER BY REF DESC, RE_STEP ASC) A)"
					+ " WHERE RNUM >= ? AND RNUM <= ?";
			// ������ ������ ��ü ����
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			// ���� ���� �� ��� ����
			rs = pstmt.executeQuery();
			// ������ ������ ����� �𸣱⿡ �ݺ����� �̿��Ͽ� �����͸� ����
			while(rs.next()) {
				// �����͸� ��Ű¡ (���� = BoardBean Ŭ���� �̿�)
				BoardBean bean = new BoardBean();
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				// date�� ��Ʈ������ �����´�.
				bean.setRegDate(rs.getDate(6).toString()); 
				bean.setRef(rs.getInt(7));
				bean.setReStep(rs.getInt(8));
				bean.setReLevel(rs.getInt(9));
				bean.setReadCount(rs.getInt(10));
				bean.setContent(rs.getString(11));
				// ��Ű¡�� �����͸� ArrayList�� ����
				list.add(bean);
			}
			con.close();	// �ڿ� �ݳ�
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// BoardInfo �ϳ��� �Խñ��� �����ϴ� �޼ҵ�
	public BoardBean getOneBoard(int num) {
		// ���� Ÿ�� ����
		BoardBean bean = new BoardBean();
		getCon();
		
		try {
			// ��ȸ�� ���� ����
			String readSql = "UPDATE BOARD SET READCOUNT = READCOUNT + 1 WHERE NUM = ?";
			pstmt = con.prepareStatement(readSql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		
			// ���� �غ�
			String sql = "SELECT * FROM BOARD WHERE NUM = ?";
			// ���� ��ȷ ��ü
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			// ���� ���� �� ����� ����
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
	
	// �亯���� ����Ǵ� �޼���
	public void reWriteBoard(BoardBean bean) {
		// �θ���� �۱׷�, �۽���, �� ���� �о����
		int ref = bean.getRef();
		int reStep = bean.getReStep();
		int reLevel = bean.getReLevel();
		
		getCon();
		
		try {
			/* �ٽ� �ڵ�*/
			// �θ𺸴� ū reLevel�� ���� ���� 1�� ���� ������
			String levelSql = "UPDATE BOARD SET RE_LEVEL = RE_LEVEL + 1 WHERE REF = ? AND RE_LEVEL > ?";
			// ���� ���� ��ü ����
			pstmt = con.prepareStatement(levelSql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, reLevel);
			// ���� ���� 
			pstmt.executeUpdate();
			// �亯�� �����͸� ����
			String sql = "INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,?,?,?,?,SYSDATE,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			// ?�� ���� ����
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref); // �θ��� ref ���� �־���
			pstmt.setInt(6, reStep+1); // ����̱⿡ �θ�� reStep + 1�� �־���
			pstmt.setInt(7, reLevel+1);
			pstmt.setString(8, bean.getContent());
			
			// ������ �����Ͻÿ�.
			pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// BoardUpdate�� Delete �� �ϳ��� �Խñ��� �����ϴ� �޼ҵ� ��ȸ�� �ö󰡸� �ȵǴ� update ���
	public BoardBean getOneUpdateBoard(int num) {
		// ���� Ÿ�� ����
		BoardBean bean = new BoardBean();
		getCon();
		
		try {
			// ���� �غ�
			String sql = "SELECT * FROM BOARD WHERE NUM = ?";
			// ���� ���� ��ü
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			// ���� ���� �� ����� ����
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
	
	// update�� delete �� �ʿ��� ��й�ȣ ���� �������ִ� �޼���
	public String getPass(int num) {
		// ������ ���� ��ü ����
		String pass = "";
		getCon();
		
		try {
			//���� �غ�
			String sql = "SELECT PASSWORD FROM BOARD WHERE NUM = ?";
			// ���� ������ ��ü ����
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			// ��й�ȣ ���� ����
			if(rs.next()) {
				pass = rs.getString(1);
			}
			// �ڿ� �ݳ�
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pass;
	}
	
	// �ϳ��� �Խñ��� �����ϴ� �޼���
 	public void updateBoard(BoardBean bean) {
 		getCon();
 		
 		try {
 			// ���� �غ�
 			String sql = "UPDATE BOARD SET SUBJECT = ?, CONTENT = ? WHERE NUM = ?";
 			pstmt = con.prepareStatement(sql);
 			// ? ���� ����
 			pstmt.setString(1, bean.getSubject());
 			pstmt.setString(2, bean.getContent());
 			pstmt.setInt(3, bean.getNum());
 			
 			pstmt.executeUpdate();
 			con.close();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}
 	// �ϳ��� �Խñ��� �����ϴ� �޼���
 	public void deleteBoard(int num) {
 		getCon();
 		try {
 			String sql = "DELETE FROM BOARD WHERE NUM = ?";
 			pstmt = con.prepareStatement(sql);
 			// ? ����
 			pstmt.setInt(1, num);
 			// ���� ����
 			pstmt.executeUpdate();
 			// �ڿ��ݳ�
 			con.close();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}
 	 
 	// ��ü���� ������ �����ϴ� �޼ҵ�
 	public int getAllCount() {
 		getCon();
 		// �Խñ� ��ü���� �����ϴ� ����
 		int count = 0;
 		
 		try {
 			// ���� �غ�
 			String sql = "SELECT COUNT(*) FROM BOARD";
 			// ������ ������ ��ü ����
 			pstmt = con.prepareStatement(sql);
 			// ���� ���� �� ����� ����
 			rs = pstmt.executeQuery();
 			if(rs.next()) {
 				count = rs.getInt(1);
 			}
 			// �ڿ� �ݳ�
 			con.close();
 		} catch (Exception e) {
			e.printStackTrace();
		}
 		return count;
 	}
}
