package com.cjon.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cjon.book.common.DBTemplate;

public class BookDAO {

	public String select(String keyword) {
		// Database泥섎━媛� �굹���슂!
		// �씪諛섏쟻�쑝濡� Database泥섎━瑜� �돺寃� �븯湲� �쐞�빐�꽌
		// Tomcat媛숈� 寃쎌슦�뒗 DBCP�씪�뒗嫄� �젣怨듯빐 以섏슂!
		// 異붽��쟻�쑝濡� 媛꾨떒�븳 �씪�씠釉뚮윭由щ�� �씠�슜�빐�꽌 DB泥섎━瑜� �빐 蹂쇨볼�삁�슂!!
		// 1. Driver Loading ( �꽕�젙�뿉 �엳�꽕.. )
		// 2. Connection �깮�꽦
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			String sql = "select bisbn, bimgbase64, btitle, bauthor, bprice " + "from book where btitle like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("img", rs.getString("bimgbase64"));
				obj.put("title", rs.getString("btitle"));
				obj.put("author", rs.getString("bauthor"));
				obj.put("price", rs.getString("bprice"));
				arr.add(obj);
			}
			result = arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}
		return result;
	}

	public boolean update(String isbn, String title, String author, String price) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		System.out.println(title);
		System.out.println(author);
		boolean result = false;
		try {
			String sql = "update book set btitle=?, bauthor=?, bprice=? where bisbn=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			pstmt.setInt(3, Integer.parseInt(price));
			pstmt.setString(4, isbn);

			int count = pstmt.executeUpdate();
			// 寃곌낵媛믪� �쁺�뼢�쓣 諛쏆� �젅肄붾뱶�쓽 �닔
			if (count == 1) {
				result = true;
				// �젙�긽泥섎━�씠湲� �븣臾몄뿉 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}
		return result;
	}

	public String detail(String keyword) {

		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;

		try {
			String sql = "select bisbn, btitle, bdate, bpage, btranslator, bsupplement, bpublisher from book where bisbn=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, keyword);
			rs = pstmt.executeQuery();

			JSONObject obj = null;
			while (rs.next()) {
				obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("title", rs.getString("btitle"));
				obj.put("date", rs.getString("bdate"));
				obj.put("page", rs.getString("bpage"));
				obj.put("translator", rs.getString("btranslator"));
				obj.put("supplement", rs.getString("bsupplement"));
				obj.put("publisher", rs.getString("bpublisher"));
			}
			result = obj.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}
		return result;
	}

	public boolean insert(String isbn, String title, String date, String page, String price, String author,
			String translator, String supplement, String publisher, String imgbase64) {

		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			String sql = "insert into book(bisbn, btitle, bdate, bpage, bprice, bauthor, "
				       + "btranslator, bsupplement, bpublisher, bimgbase64) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			pstmt.setString(2, title);
			pstmt.setString(3, date);
			pstmt.setString(4, page);
			pstmt.setInt(5, Integer.parseInt(price));
			pstmt.setString(6, author);
			pstmt.setString(7, translator);
			pstmt.setString(8, supplement);
			pstmt.setString(9, publisher);
			pstmt.setString(10, imgbase64);

			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if (count == 1) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}

		return result;
	}
	
	public boolean login(String uid, String upw) {
		
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		

		try {
			String sql = "Select upw from user where uid =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, uid);		
			rs = pstmt.executeQuery();
			String upwch = null;
			
			while (rs.next()) {
				upwch = rs.getString("upw");
			}
			
			if(upw.equals(upwch) == true){
				result = true;
			}else if(upw.equals(upwch) == false){
				result = false;
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}
		return result;
	}
	
	public boolean user(String uid, String upw, String uname, String ucount) {

		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			String sql = "insert into user(uid, upw, uname, ucount) values(?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, uid);
			pstmt.setString(2, upw);
			pstmt.setString(3, uname);
			pstmt.setString(4, ucount);
			
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if (count == 1) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				result = false;
				DBTemplate.rollback(con);
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}

		return result;
	}
	
	public String clist(String isbn) {

		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;

		try {
			String sql = "select cid, cdate, ctext from book_comment where bisbn=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			rs = pstmt.executeQuery();

			JSONObject obj = null;
			while (rs.next()) {
				obj = new JSONObject();
				obj.put("id", rs.getString("cid"));
				obj.put("text", rs.getString("ctext"));
				obj.put("date", rs.getString("cdate"));
			}
			result = obj.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}
		return result;
	}
	
	public boolean cinsert(String bisbn, String ctitle, String cauthor, String cdate, String ctext) {

		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			String sql = "insert into book_comment (bisbn,ctitle,cauthor,cdate,ctext) value (?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bisbn);
			pstmt.setString(2, ctitle);
			pstmt.setString(3, cauthor);
			pstmt.setString(4, cdate);
			pstmt.setString(5, ctext);
			rs = pstmt.executeQuery();

			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if (count == 1) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				result = false;
				DBTemplate.rollback(con);
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		}

		return result;
	}
}
