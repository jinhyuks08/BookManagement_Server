package com.cjon.book.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjon.book.service.BookService;

@WebServlet("/bookcinsert")
public class BookCommentIns extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BookCommentIns() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bisbn = request.getParameter("bisbn");
		String ctitle = request.getParameter("ctitle");
		String cauthor = request.getParameter("cauthor");
		String ctext = request.getParameter("ctext");
		
		String callback = request.getParameter("callback");

		BookService service = new BookService();
		boolean result = service.cinsert(bisbn, ctitle, cauthor, ctext);
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out = response.getWriter();
		out.println(callback + "(" + result + ")");
		out.flush();
		out.close();
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
