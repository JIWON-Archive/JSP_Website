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
	// 모든 아이템 조회
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
				// 데이터를 저장할 빈 클래스 생성
				bean.setNo(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setCategory(rs.getInt(3));
				bean.setPrice(rs.getInt(4));
				bean.setDeposit(rs.getInt(5));
				bean.setImg(rs.getString(6));
				bean.setInfo(rs.getString(7));
				// ArrayList 빈클래스에 저장
				list.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return list;
	}
	
	public ItemListBean getOneItem(int no) {
		// 리턴 타입 선언
		ItemListBean bean = new ItemListBean();
		getCon();
		
		try {
			String sql = "SELECT * FROM ITEM WHERE NO = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			// 결과를 리턴
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
	
	// 회원 정보가 있는 지 비교
	public int getMember(String id, String pw) {
		int result = 0; // 0이면 회원 없음
		getCon();
		
		try {
			String sql = "SELECT COUNT(*) FROM MEMBERSHIP WHERE ID = ? AND PW = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1); // 0 또는 1 값이 저장됨
			}
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 하나의 예약 정보를 저장하는 메소드
	public void setReserveItem(ItemReserveBean bean) {
		getCon();
		try {
			String sql = "INSERT INTO ITEMRESERVE VALUES(ITEMRESERVE_SEQ.NEXTVAL,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			// ?  값을 대입 reserveNo는 시퀀스로 받아옴
			pstmt.setInt(1, bean.getNo());
			pstmt.setString(2, bean.getId());
			pstmt.setInt(3, bean.getQty());
			pstmt.setInt(4, bean.getPrice());
			pstmt.setInt(5, bean.getDeposit());
			// 결과 리턴
			pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 회원의 예약 정보를 리턴하는 메소드
	public ArrayList<ReserveViewBean> getAllReserve(String id) {
		// 리턴 타입 설정
		ArrayList<ReserveViewBean> list = new ArrayList<>();
		// 데이터를 저장할 빈 클래스 설정
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
	
	// 하나의 예약 삭제
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