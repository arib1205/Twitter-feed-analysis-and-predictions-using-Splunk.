package com.sentiments.analyzers;

import java.util.Date;

public class TweetWithSentiment {

	private String line;
	private String cssClass;
	private String userName;
	private String source;
	private Date createdAt;
	private String place;
	private double sentiment;
	private boolean response;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public double getSentiment() {
		return sentiment;
	}

	public void setSentiment(double sentiment) {
		this.sentiment = sentiment;
	}

	public boolean isResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public TweetWithSentiment() {
	}

	public TweetWithSentiment(String line, String cssClass) {
		super();
		this.line = line;
		this.cssClass = cssClass;
	}

	public String getLine() {
		return line;
	}

	public String getCssClass() {
		return cssClass;
	}

	@Override
	public String toString() {
		return "TweetWithSentiment [line=" + line + ", cssClass=" + cssClass + "]";
	}

}
