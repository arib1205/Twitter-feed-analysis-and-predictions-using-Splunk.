package com.sentiments.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sentiments.analyzers.SentimentAnalyzer;
import com.sentiments.analyzers.TweetWithSentiment;
import com.sentiments.splunk.IndexProcessor;
import com.sentiments.twitter.TwitterSearch;
import com.sentiments.util.CSVUtil;

import twitter4j.Status;

public class SentimentsResource {

	private SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();

	private TwitterSearch twitterSearch = new TwitterSearch();

	public List<Result> sentiments(String searchKeywords) {
		List<Result> results = new ArrayList<>();
		if (searchKeywords == null || searchKeywords.length() == 0) {
			return results;
		}
		Set<String> keywords = new HashSet<String>();
		for (String keyword : searchKeywords.split(",")) {
			keywords.add(keyword.trim().toLowerCase());
		}
		if (keywords.size() > 3) {
			keywords = new HashSet<>(new ArrayList<>(keywords).subList(0, 3));
		}
		for (String keyword : keywords) {
			List<Status> statuses = twitterSearch.search(keyword);
			System.out.println("Found statuses ... " + statuses.size());
			List<TweetWithSentiment> sentiments = new ArrayList<>();
			for (Status status : statuses) {
				TweetWithSentiment tweetWithSentiment = sentimentAnalyzer.findSentiment(status.getText());
				if (tweetWithSentiment == null) {
					continue;
				}
				tweetWithSentiment.setCreatedAt(status.getCreatedAt());
				tweetWithSentiment.setUserName(status.getUser().getName());
				tweetWithSentiment.setPlace(status.getUser().getLocation());
				tweetWithSentiment.setResponse(tweetWithSentiment.getSentiment() < 2 ? false : true);
				tweetWithSentiment.setSource(status.getSource().substring(status.getSource().indexOf('>') + 1,
						status.getSource().lastIndexOf('<')));
				sentiments.add(tweetWithSentiment);
			}
			File file = new File(keyword + ".csv");
			CSVUtil csvUtil = new CSVUtil(file, sentiments);
			csvUtil.writeToCSV();
			IndexProcessor processor = new IndexProcessor();
			processor.createIndex(keyword);
			processor.uploadFile(file, keyword);
			file.delete();
			Result result = new Result(keyword, sentiments);
			results.add(result);
		}
		return results;
	}

	public TweetWithSentiment findNer(String text) {
		if (text == null || text.length() == 0) {
			return null;
		}
		TweetWithSentiment tweetWithSentiment = sentimentAnalyzer.findSentiment(text);
		return tweetWithSentiment;
	}
}
