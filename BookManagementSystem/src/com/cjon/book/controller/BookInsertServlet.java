package com.cjon.book.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjon.book.service.BookService;

@WebServlet("/bookInsert")
public class BookInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BookInsertServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. �엯�젰諛쏄퀬
		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		String date = request.getParameter("date");
		String page = request.getParameter("page");
		String price = request.getParameter("price");
		String author = request.getParameter("author");
		String translator = request.getParameter("translator");
		String supplement = request.getParameter("supplement");
		String publisher = request.getParameter("publisher");
		String imgbase64 = request.getParameter("imgbase64");

		String callback = request.getParameter("callback");

		// 2. 濡쒖쭅泥섎━
		BookService service = new BookService();
		boolean result = service.insert(isbn, title, date, page, price, author, translator, supplement, publisher,
				imgbase64);
		// 3. 異쒕젰泥섎━
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out = response.getWriter();
		out.println(callback + "(" + result + ")");
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
