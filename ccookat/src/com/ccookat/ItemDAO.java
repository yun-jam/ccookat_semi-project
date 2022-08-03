package com.ccookat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ItemDAO {
	
	private Connection conn;
	
	public ItemDAO(Connection conn) {
		
		this.conn = conn;
	}
	
	
	//제품별 상세페이지 itemNum을 위한 메소드
	public int getMaxNum() {
		
		int maxNum = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = "select nvl(max(num),0) from item";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				maxNum = rs.getInt(1);
			}
			
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return maxNum;
		
	}	

	
	//상세페이지 게시글 작성 메소드 (관리자 권한)
	public void insertData(ItemDTO idto) {
		
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			sql = "insert into item (itemNum,itemName,itemCount,itemPrice,itemDiscount,";
			sql+= "itemType,itemContent,itemImage1,itemImage2,itemImage3,itemImage4,";
			sql+= "itemStock,itemHitCount) ";
			sql+= "values (?,?,0,?,?,?,?,?,?,?,?,?,0)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, idto.getItemNum());
			pstmt.setString(2, idto.getItemName());
			pstmt.setInt(3, idto.getItemPrice());
			pstmt.setInt(4, idto.getItemDiscount());
			pstmt.setString(5, idto.getItemType());
			pstmt.setString(6, idto.getItemContent());
			pstmt.setString(7, idto.getItemImage1());
			pstmt.setString(8, idto.getItemImage2());
			pstmt.setString(9, idto.getItemImage3());
			pstmt.setString(10, idto.getItemImage4());
			pstmt.setInt(11, idto.getItemStock());
		
			pstmt.executeUpdate();
			
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	
	//상세페이지 게시글 수정 메소드 (관리자 권한)
	public ItemDTO getReadData(int itemNum) {
		
		ItemDTO idto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = "select itemNum,itemName,itemCount,itemPrice,itemDiscount,";
			sql+= "itemType,itemContent,itemImage1,itemImage2,itemImage3,itemImage4,";
			sql+= "itemStock,itemHitCount) ";
			sql+= "from item where itemNum=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, itemNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				idto = new ItemDTO();
				
				idto.setItemNum(rs.getInt("itemNum"));
				idto.setItemName(rs.getString("itemName"));
				idto.setItemCount(rs.getInt("itemCount"));
				idto.setItemPrice(rs.getInt("itemPrice"));
				idto.setItemDiscount(rs.getInt("itemDiscount"));
				idto.setItemType(rs.getString("itemType"));
				idto.setItemContent(rs.getString("itemContent"));
				idto.setItemImage1(rs.getString("itemImage1"));
				idto.setItemImage2(rs.getString("itemImage2"));
				idto.setItemImage3(rs.getString("itemImage3"));
				idto.setItemImage4(rs.getString("itemImage4"));
				idto.setItemStock(rs.getInt("itemStock"));
				idto.setItemHitCount(rs.getInt("itemHitCount"));
				
			}
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return idto;
		
	}
	
	
	//상세페이지 게시글 삭제 메소드 (관리자 권한)
	public void deleteData(int itemNum) {
		
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			sql = "delete item where itemNum=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, itemNum);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	
	
	//카테고리별 이미지 게시판에 데이터 불러오기
	public List<ItemDTO> getLists(){
		
		List<ItemDTO> lists = new ArrayList<ItemDTO>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;	
		
		try {
			
			sql = "select * from (";
			sql+= "select rownum rnum, data.* from (";
			sql+= "select itemNum,itemName,itemPrice,itemDisccount,itemType,itemImage1 ";
			sql+= "from item order by itemNum desc) data ) ";
			
			pstmt = conn.prepareStatement(sql);
			
			while(rs.next()) {
				
				ItemDTO idto = new ItemDTO();
				
				idto.setItemNum(rs.getInt("itemNum"));
				idto.setItemName(rs.getString("itemName"));
				idto.setItemPrice(rs.getInt("itemPrice"));
				idto.setItemDiscount(rs.getInt("itemDiscount"));
				idto.setItemType(rs.getString("itemType"));
				idto.setItemImage1(rs.getString("itemImage1"));
				
				lists.add(idto);
				
			}

			rs.close();
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return lists;
		
	}
	
}
