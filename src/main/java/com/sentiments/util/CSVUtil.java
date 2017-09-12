package com.sentiments.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sentiments.analyzers.TweetWithSentiment;

public class CSVUtil {
	private File file;
	private List<TweetWithSentiment> tweetWithSentiments;

	public CSVUtil(File file, List<TweetWithSentiment> tweetWithSentiments) {
		this.file = file;
		this.tweetWithSentiments = tweetWithSentiments;
	}

	public void writeToCSV() {
		try {
			if (tweetWithSentiments.isEmpty())
				return;
			List<String> headers = new ArrayList<String>();
			headers.add("tweet");
			headers.add("username");
			headers.add("source");
			headers.add("place");
			headers.add("sentiment");
			headers.add("response");
			headers.add("createdAt");
			FileWriter writer = new FileWriter(file);
			writerHeader(writer, headers);
			StringBuilder sb = new StringBuilder();
			for (TweetWithSentiment value : tweetWithSentiments) {
				sb.append("\n");
				String line = value.getLine().replaceAll(",", "-").replaceAll("(\\r|\\n|\\r\\n)+", "-");
				String userName = value.getUserName().replaceAll(",", "-");
				String source = value.getSource().replaceAll(",", "-");
				String place = value.getPlace().equals("") ? "-" : value.getPlace().replaceAll(",", " ");
				double sentiment = value.getSentiment();
				boolean response = value.isResponse();
				sb.append(line).append(',').append(userName);
				sb.append(',').append(source).append(',').append(place);
				sb.append(',').append(sentiment).append(',').append(response);
				Date createdAt = value.getCreatedAt();
				sb.append(',').append(createdAt);
			}
			sb.append("\n");
			writer.append(sb.toString());
			writer.close();

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void writerHeader(FileWriter writer, List<String> headers) {
		try {
			StringBuilder sb = new StringBuilder();
			for (String header : headers) {
				sb.append(header).append(',');
			}
			writer.write(sb.substring(0, sb.length() - 1));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
}
