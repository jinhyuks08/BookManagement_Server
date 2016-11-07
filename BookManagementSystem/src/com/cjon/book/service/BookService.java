package com.cjon.book.service;

import com.cjon.book.dao.BookDAO;

public class BookService {

	// 由ъ뒪�듃瑜� 媛��졇�삤�뒗 �씪�쓣 �븯�뒗 method
	public String getList(String keyword) {
		// �씪諛섏쟻�씤 濡쒖쭅泥섎━ �굹���슂!!
		
		// 異붽��쟻�쑝濡� DB泥섎━媛� �굹�삱 �닔 �엳�뼱�슂!
		// DB泥섎━�뒗 Service�뿉�꽌 泥섎━�뒗�븯�뒗寃� �븘�땲�씪..�떎瑜� 媛앹껜瑜� �씠�슜�빐�꽌
		// Database泥섎━瑜� �븯寃� �릺二�!!
		BookDAO dao = new BookDAO();
		String result = dao.select(keyword);	
		
		return result;
	}

	public boolean update(String isbn, String title, String author, String price) {
		// TODO Auto-generated method stub
		BookDAO dao = new BookDAO();
		boolean result = dao.update(isbn, title, author, price);	
		return result;
	}
	
	 public String detail(String keyword){
		  BookDAO dao = new BookDAO();
		  String result = dao.detail(keyword);
		  return result;
	}
	 
	 public boolean insert(String isbn, String title, String date, String page, String price, 
			 			  String author, String translator, String supplement, String publisher, String imgbase64){
		  BookDAO dao = new BookDAO();
		  boolean result = dao.insert(isbn, title, date, page, price, author, translator, supplement, publisher, imgbase64);
		  return result;
	}
	 
	 public boolean login(String uid, String upw){
		 BookDAO dao = new BookDAO();
		 boolean result = dao.login(uid, upw);
		 return result;
	 }
	 
	 public boolean user(String uid, String upw, String uname, String ucount){
		 BookDAO dao = new BookDAO();
		 boolean result = dao.user(uid, upw, uname, ucount);
		 return result;
	 }
	 
	 public String clist(String isbn){
		 BookDAO dao = new BookDAO();
		 String result = dao.clist(isbn);
		 return result;
	 }
	 
	 public boolean cinsert(String bisbn, String ctitle, String cauthor, String ctext){
		 BookDAO dao = new BookDAO();
		 boolean result = dao.cinsert(bisbn, ctitle, cauthor, ctext);
		 return result;
	 }
}