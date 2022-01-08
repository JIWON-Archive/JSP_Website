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
	
	// �����ͺ��̽��� �����Ҽ� �ֵ��� ���� 
	Connection con;
	// SQL�� �����ϱ� ���� ��� - ������ ���̽����� ������ ��������ִ� ��ü 
	PreparedStatement pstmt;
	// SQL�� ����� ������ ���� ��� - ������ ���̽��� ���̺��� ����� ���� �޾� �ڹٿ� ������ �ִ� ��ü
	ResultSet rs;
	
	// ������ ���̽��� ������ �� �ֵ��� �����ִ� �޼���
	public void getCon() {
		try {
			// ����̹� �ε�
			Class.forName(driver);
			// DBMS�� Connection ����
			con = DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// ������ ���̽��� �� ��� ȸ�� ������ �������ִ� �޼���
	public void insertMember(MemberBean mbean) {
		try {
			getCon();
			String sql = "INSERT INTO MEMBERSHIP VALUES(?,?,?,?,?,?,?)";
			// ������ ����ϵ��� ����
			pstmt = con.prepareStatement(sql);
			// ?�� �°� �����͸� ����
			pstmt.setString(1, mbean.getId());
			pstmt.setString(2, mbean.getPw());
			pstmt.setString(3, mbean.getEmail());
			pstmt.setString(4, mbean.getTel());
			pstmt.setString(5, mbean.getHobby());
			pstmt.setString(6, mbean.getJob());
			pstmt.setString(7, mbean.getInfo());
			// ����Ŭ���� ���� ����
			pstmt.executeUpdate();
			// �ڿ� �ݳ�
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// ��� ȸ�� ������ �����ϴ� �޼ҵ�
	public ArrayList<MemberBean> allSelectMember() {
		ArrayList<MemberBean> list = new ArrayList<>();
		try {
			// Ŀ�ؼ� ����
			getCon();
			// ���� �غ�
			String sql = "SELECT * FROM MEMBERSHIP";
			// ������ ��������ִ� ��ü ����
			pstmt = con.prepareStatement(sql);
			// ������ �����Ų ����� �����ؼ� �޾���
			rs = pstmt.executeQuery();
			// �ݺ����� ����ؼ� rs�� ����� �����͸� ������ ���ƾ��Ѵ�.
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
	
	// �� ����� ������ �����ϴ� �޼���
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
	
	// �� ȸ���� �н����� ���� �����ϴ� �޼��� �ۼ�
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
			// �ڿ� �ݳ�
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return password;
	}
	
	// �� ȸ���� ������ �����ϴ� �޼���
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
	
	// �� ȸ���� �����ϴ� �޼��� �ۼ�
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
