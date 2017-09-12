package com.sentiments.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EntryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SentimentsResource resource = new SentimentsResource();
		List<Result> sentiments = resource.sentiments(request.getParameter("searchKeywords"));
		Gson gson = new Gson();
		Type type = new TypeToken<List<Result>>() {
		}.getType();
		String jsonObject = gson.toJson(sentiments.subList(0, sentiments.size() > 20 ? 20 : sentiments.size()), type);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonObject);
		out.flush();
	}

}
