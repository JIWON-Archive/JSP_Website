package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RentDAO {
	private String id = "HUEKA";
	private String pw = "0814";
	private String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
	private String driver = "oracle.jdbc.driver.OracleDriver";

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
	// ��� ������ ��ȸ
	public ArrayList<ItemListBean> getAllList() {
		ArrayList<ItemListBean> list = new ArrayList<ItemListBean>();
//		ItemListBean bean = new ItemListBean();
		getCon();
		
		try {
			String sql = "SELECT * FROM ITEM";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				ItemListBean bean = new ItemListBean();
				// �����͸� ������ �� Ŭ���� ����
				bean.setNo(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setCategory(rs.getInt(3));
				bean.setPrice(rs.getInt(4));
				bean.setDeposit(rs.getInt(5));
				bean.setImg(rs.getString(6));
				bean.setInfo(rs.getString(7));
				// ArrayList ��Ŭ������ ����
				list.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return list;
	}
	
	public ItemListBean getOneItem(int no) {
		// ���� Ÿ�� ����
		ItemListBean bean = new ItemListBean();
		getCon();
		
		try {
			String sql = "SELECT * FROM ITEM WHERE NO = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			// ����� ����
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setNo(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setCategory(rs.getInt(3));
				bean.setPrice(rs.getInt(4));
				bean.setDeposit(rs.getInt(5));
				bean.setImg(rs.getString(6));
				bean.setInfo(rs.getString(7));
			}
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	// ȸ�� ������ �ִ� �� ��
	public int getMember(String id, String pw) {
		int result = 0; // 0�̸� ȸ�� ����
		getCon();
		
		try {
			String sql = "SELECT COUNT(*) FROM MEMBERSHIP WHERE ID = ? AND PW = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1); // 0 �Ǵ� 1 ���� �����
			}
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// �ϳ��� ���� ������ �����ϴ� �޼ҵ�
	public void setReserveItem(ItemReserveBean bean) {
		getCon();
		try {
			String sql = "INSERT INTO ITEMRESERVE VALUES(ITEMRESERVE_SEQ.NEXTVAL,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			// ?  ���� ���� reserveNo�� �������� �޾ƿ�
			pstmt.setInt(1, bean.getNo());
			pstmt.setString(2, bean.getId());
			pstmt.setInt(3, bean.getQty());
			pstmt.setInt(4, bean.getPrice());
			pstmt.setInt(5, bean.getDeposit());
			// ��� ����
			pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// ȸ���� ���� ������ �����ϴ� �޼ҵ�
	public ArrayList<ReserveViewBean> getAllReserve(String id) {
		// ���� Ÿ�� ����
		ArrayList<ReserveViewBean> list = new ArrayList<>();
		// �����͸� ������ �� Ŭ���� ����
		ReserveViewBean bean = null;
		
		getCon();
		
		try {
			String sql = "SELECT * FROM ITEM NATURAL JOIN ITEMRESERVE WHERE ID = ? ORDER BY RESERVENO DESC";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1,id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				bean = new ReserveViewBean();
				bean.setNo(rs.getInt(1));
				bean.setPrice(rs.getInt(2));
				bean.setDeposit(rs.getInt(3));
				bean.setName(rs.getString(4));
				bean.setImg(rs.getString(6));
				bean.setReserveNo(rs.getInt(8));
				bean.setQty(rs.getInt(10));
				list.add(bean);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// �ϳ��� ���� ����
	public void itemReserveDelete(String id, int no, int reserveNo) {
		ItemListBean bean = new ItemListBean();
		
		getCon();
		
		try {
			String sql = "DELETE FROM ITEMRESERVE WHERE ID = ? AND NO = ? AND RESERVENO = ?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setInt(2, no);	
			pstmt.setInt(3, reserveNo);
			
			pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}