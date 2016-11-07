package com.cjon.book.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjon.book.service.BookService;

/**
 * Servlet implementation class BookListServlet
 */
@WebServlet("/bookList")
public class BookListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. �엯�젰諛쏄퀬
		String keyword = request.getParameter("keyword"); // 梨낆뿉 ���븳 keyword瑜� 諛쏅뒗遺�遺�
		String callback = request.getParameter("callback"); // JSONP泥섎━瑜� �쐞�빐�꽌 �궗�슜
		// 2. 濡쒖쭅泥섎━�븯怨�(DB泥섎━�룷�븿)
		// Servlet�� �엯�젰�쓣 諛쏄퀬 異쒕젰�뿉���븳 吏��젙�쓣 �떞�떦. 濡쒖쭅泥섎━�뒗 �븯吏� �븡�븘�슂!!
		// 濡쒖쭅泥섎━�븯�뒗 媛앹껜瑜� �슦由ш� �씪諛섏쟻�쑝濡� Service媛앹껜�씪怨� 遺덈윭�슂! �씠�냸�쓣 留뚮뱾�뼱�꽌 �씪�쓣 �떆耳쒖꽌
		// 寃곌낵瑜� 諛쏆븘�삤�뒗 援ъ“濡� 留뚮뱾�뼱 蹂댁븘�슂!
		// 濡쒖쭅泥섎━瑜� �븯湲� �쐞�빐�꽌 �씪�떒 Service媛앹껜瑜� �븯�굹 �깮�꽦�빀�땲�떎.
		
		BookService service = new BookService();		
		String result = service.getList(keyword);
		// 寃곌낵濡� 媛��졇�삱嫄�..DB 泥섎━�븳 �썑 �굹�삩 梨낆뿉 ���븳 JSON data		
		// 3. 異쒕젰泥섎━
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out = response.getWriter();
		out.println(callback + "(" + result + ")");
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}









