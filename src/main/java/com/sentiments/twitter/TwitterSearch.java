package com.sentiments.twitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterSearch {

	public List<Status> search(String keyword) {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("twitter4j.properties").getFile());
			input = new FileInputStream(file);
			prop.load(input);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(prop.getProperty("oauth.consumerKey"))
				.setOAuthConsumerSecret(prop.getProperty("oauth.consumerSecret"))
				.setOAuthAccessToken(prop.getProperty("oauth.accessToken"))
				.setOAuthAccessTokenSecret(prop.getProperty("oauth.accessTokenSecret"));
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		Query query = new Query(keyword + " -filter:retweets -filter:links -filter:replies -filter:images");
		query.setCount(100);
		query.setLocale("en");
		query.setLang("en");
		List<Status> statuses = new ArrayList<Status>();
		QueryResult queryResult;
		try {
			do {
				queryResult = twitter.search(query);
				statuses.addAll(queryResult.getTweets());
			} while ((query = queryResult.nextQuery()) != null);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		System.out.print("=======================\n" + statuses.size());
		return statuses;

	}

	public static void main(String[] args) {
		TwitterSearch twitterSearch = new TwitterSearch();
		List<Status> statuses = twitterSearch.search("openshift");

		for (Status status : statuses) {
			System.out.println(status.getText());
		}
	}
}
