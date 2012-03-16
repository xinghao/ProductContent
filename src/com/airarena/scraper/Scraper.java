package com.airarena.scraper;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;


public class Scraper {
	
	public String useragent;
	protected static final int timeout = 10000;
	
	public static String replaceBeginning(String source, String c) {
		if (source.startsWith(c)) {
			return source.substring(c.length(), source.length());
		}
		return source;
	}
	public static Element getNextElement(Element sourceElement, String targetTagName) {
		Element targetElement = getNextElement(sourceElement);
		if (targetElement != null && targetElement.tagName().equalsIgnoreCase(targetTagName.toLowerCase())) {
			return targetElement;
		}
		return null;
	}
	
	public static Element getNextElement(Element sourceElement) {
		
		Elements firstLevelTags = sourceElement.parent().children();
		if (firstLevelTags == null) return null;
	
		for (int i = 0 ; i <  firstLevelTags.size() ; ++ i) {
			Element fTag = firstLevelTags.get(i);
			if (fTag.equals(sourceElement)) {
				if (i < (firstLevelTags.size() - 1)) {
					return firstLevelTags.get(i+1);					
				}
			}
			
		}
		return null;
	}	
	
	public Scraper() {
		super();
		this.useragent = FakedUseragents.getRandowmWebUseragent();
	}



	public Scraper(String useragent) {
		super();
		this.useragent = useragent;
	}



	public Element scrape(String url) throws IOException {
		Connection conn = Jsoup.connect(url);
		conn.userAgent(this.useragent);
		conn.timeout(timeout);
		//conn.method(Connection.Method.GET);
		Document doc;

		doc = conn.get();
		return doc.body();

//		if (this.connectionHeader != null) {
//			for(String key : this.connectionHeader.keySet()) {
//				_logger.info("Scraper header: " + key + "=" + this.connectionHeader.get(key));
//			}
//		}
//		String returnHtml = HttpConnection.get(url, this.connectionHeader);
//		Pattern p = Pattern.compile("<div class=\"content\">(.*)</div>", Pattern.DOTALL);
//		Matcher m = p.matcher(returnHtml);
//
//		if (m.find()) {
//			String returnStr = m.group(1).trim();
//		    _logger.info(returnStr);
//		    return returnStr;
//		} else {
//			   _logger.info("Nothing found");
//		}		
//		
	}
}
