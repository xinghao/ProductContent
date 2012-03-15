package com.airarena.scraper;

import java.util.Random;

public class FakedUseragents {

	public static final String[] webBrowserUseragent = {
		"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; en-us) AppleWebKit/533.18.1 (KHTML, like Gecko) Version/5.0.2 Safari/533.18.5",
		"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
		"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.2.17) Gecko/20110420 Firefox/3.6.17"
	};

	public static String getRandowmWebUseragent() {
		Random randomGenerator = new Random();
		return webBrowserUseragent[randomGenerator.nextInt(webBrowserUseragent.length)];		
	}
	
}
