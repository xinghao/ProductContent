package com.airarena.scraper;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.airarena.aws.products.api.util.BasicApiRespose;
import com.airarena.hibernate.util.InitDB;
import com.airarena.product.resources.Review;
import com.airarena.product.resources.Review.ReviewDetail;

public class AwsScraper extends Scraper {
	private static final Logger _logger = Logger.getLogger(AwsScraper.class);
	public AwsScraper() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AwsScraper(String useragent) {
		super(useragent);
		// TODO Auto-generated constructor stub
	}
	
	public static Review scrapeReview(String url) throws IOException {
		AwsScraper as = new AwsScraper();
		Element containerDiv = as.scrape(url);
		
		if (containerDiv == null) return null;
		
		// return null if no review summary in this page
		Element summaryContainerTag = containerDiv.select("table#productSummary").first();
		if (summaryContainerTag == null) {
			return null;
		}
		
		Review review = new Review();
		boolean hasReview = false;
		Element totalAmountTag = summaryContainerTag.select("div > div.tiny > b").first();
		if (totalAmountTag != null) {
			hasReview = true;
			review.setTotalReviewsAmount(Integer.valueOf(totalAmountTag.html().toLowerCase().replaceAll("reviews|,", "").replaceAll("review", "").trim()));
			//_logger.info(totalReviewAmount);
		}		
		
		Elements starAmountTags = summaryContainerTag.select("div > table tr");
		for(Element starAmountTag : starAmountTags) {
			Element whichStar = starAmountTag.child(0);
			if (whichStar.children().size() > 0) {
				hasReview = true;
				String starName = whichStar.child(0).html();
				if (starName.startsWith("5")) {						
					review.setFiveStarAmount(Integer.valueOf(starAmountTag.child(2).html().trim().replaceAll("\\(|\\)|&nbsp;|,", "")));
//					_logger.info(fiveStarAmount);
				} else if (starName.startsWith("4")) {						
					review.setFourStarAmount(Integer.valueOf(starAmountTag.child(2).html().trim().replaceAll("\\(|\\)|&nbsp;|,", "")));
					//_logger.info(fourStarAmount);
				} else if (starName.startsWith("3")) {						
					review.setThreeStarAmount(Integer.valueOf(starAmountTag.child(2).html().trim().replaceAll("\\(|\\)|&nbsp;|,", "")));
					//_logger.info(threeStarAmount);
				} else if (starName.startsWith("2")) {						
					review.setTwoStarAmount(Integer.valueOf(starAmountTag.child(2).html().trim().replaceAll("\\(|\\)|&nbsp;|,", "")));
					//_logger.info(twoStarAmount);
				} else if (starName.startsWith("1")) {						
					review.setOneStarAmount(Integer.valueOf(starAmountTag.child(2).html().trim().replaceAll("\\(|\\)|&nbsp;|,", "")));
					//_logger.info(oneStarAmount);
				}
			}
		}		
		
		Element averageAmountTag = summaryContainerTag.select("span.crAvgStars > span.asinReviewsSummary span span").first();
		if (averageAmountTag != null) {
			hasReview = true;
			review.setAverageRate(Float.valueOf(averageAmountTag.html().replaceAll("out of 5 stars", "").trim()));
			//_logger.info(avr);
		}		
		
		
		Elements reviewsTags = containerDiv.select("table#productReviews td > div");
		for(Element reviewTag : reviewsTags) {
			hasReview = true;
			ReviewDetail rd = review.initReviewDetail(); 
			if (reviewTag.ownText() != null && !reviewTag.ownText().trim().isEmpty()) {
				rd.setReviewHtml(reviewTag.ownText());			
			}
			Elements conts = reviewTag.select("div[style=margin-bottom:0.5em;]");
			if (conts.size() >= 3) {
				rd.setHelpful(conts.get(0).html()); //helpful
				
				Elements rateAndTitle = conts.get(1).children();
				if (rateAndTitle.size() >= 2) {
					Elements t = rateAndTitle.get(1).children();
					if (t.size() >= 2) {
						try {
							rd.setSubmitAt(BasicApiRespose.reviewDate(t.get(1).html().replaceAll(",", "")));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							_logger.error(e);
						}
						//_logger.info();
					} 
					if (t.size() >= 1) {
						rd.setTitle(t.get(0).html());
						//_logger.info(t.get(0).html());
					}
				} 
				if (rateAndTitle.size() >= 1) {
					Element rate = rateAndTitle.get(0).select("span > span > span").first();
					if (rate != null) {
						rd.setRate(Float.valueOf(rate.html().replaceAll("out of 5 stars", "").trim()));
						//_logger.info(rate.html().replaceAll("out of 5 stars", "").trim());
					}						
					
				}
				
				Elements nameAndAdress = conts.get(2).children();
				if (nameAndAdress.size() > 0) {
					Elements namAndAdressDivs = nameAndAdress.get(0).children();
					if (namAndAdressDivs.size() > 1) {
						Element namAndAdressDiv = namAndAdressDivs.get(1);
						
						String address = namAndAdressDiv.ownText().trim().replaceAll("\\(|\\)|-", "");
						if (address != null && !address.isEmpty()) {
							rd.setAddress(address);
						}
						//_logger.info(address);
						if (namAndAdressDiv.children().size() > 0) {
							Element nameTag = namAndAdressDiv.children().get(0);
							if (nameTag.children().size() > 0) {
								String name = nameTag.child(0).html();
								rd.setAuthor(name);
								//_logger.info(name);
							}
						}
					}
				}
				//_logger.info(review.child(0).html());
			}
			// get ride of all the rubbish from websites
			if (!rd.isNull()) review.getRds().add(rd);
		}		
		if (hasReview) {
			return review;
		}else {
			return null;
		}
		
	}
	
	public static HashMap<String, String> scrapeTechnicalDetails(String url) throws IOException {
		HashMap<String, String> retHash = new HashMap<String, String>();
		AwsScraper as = new AwsScraper();
		Element containerDiv = as.scrape(url);
		Element tdElement = null;
		Elements firstLevelTags = containerDiv.select("div.content > h3");
		for (Element fTag : firstLevelTags) {
		  if (fTag.html().equalsIgnoreCase("Technical Details".toLowerCase()))  {			  
			  //_logger.info(fTag.html());
			  tdElement = getNextElement(fTag, "div");
			  			  			  
		  }
		}
		if (tdElement != null) {
			Elements tds = tdElement.select("li");
			for (Element td : tds) {
//				System.out.print(td.child(0).text() + "=");
//				_logger.info(Scraper.replaceBeginning(td.ownText().trim(), ":"));
				retHash.put(td.child(0).text(), Scraper.replaceBeginning(td.ownText().trim(), ":"));
			}			
//			_logger.info(tdElement.html());
		}
		return retHash;
		
	}
	
	
}
