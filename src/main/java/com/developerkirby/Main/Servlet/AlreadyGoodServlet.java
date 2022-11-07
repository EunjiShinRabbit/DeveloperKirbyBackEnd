package com.developerkirby.Main.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.developerkirby.Main.Common;
import com.developerkirby.Main.DAO.GoodDAO;


@WebServlet("/AlreadyGoodServlet")
public class AlreadyGoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Common.corsResSet(response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// 한글 깨짐 방지를 위해서 설정
		request.setCharacterEncoding("utf-8");
		// CORS 접근 허용
		Common.corsResSet(response);
		// 요청 메시지 받기
		StringBuffer sb = Common.reqStringBuff(request);
		// 요청 받은 메시지 JSON 파싱
		JSONObject jsonObj = Common.getJsonObj(sb);
		
		String reqCmd = (String)jsonObj.get("cmd");
		int reqWriteNum = Integer.valueOf(jsonObj.get("writeNum").toString());
		int reqMemberNum = Integer.valueOf(jsonObj.get("memberNum").toString());
		
		PrintWriter out = response. getWriter();
		JSONObject resJson = new JSONObject();
		if(!reqCmd.equals("AlreadyGoodInfo")) {
		resJson.put("result", "NOK");
			out.print(resJson);
		} 
		GoodDAO dao = new GoodDAO();
		int alreadyGood = dao.alreadyGood(reqWriteNum, reqMemberNum);
		resJson.put("result", "OK");
		resJson.put("cnt", alreadyGood);
		out.print(resJson);
	}
}
