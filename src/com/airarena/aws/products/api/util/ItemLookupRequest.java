package com.airarena.aws.products.api.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.airarena.product.resources.Image;
import com.airarena.product.resources.Price;
import com.airarena.products.model.Product;
import com.airarena.products.model.ProductAttributeMetaKey;
import com.airarena.products.model.ProductCondition;
import com.airarena.products.model.ProductPrice;
import com.airarena.scraper.AwsScraper;
import com.airarena.scraper.Scraper;

public class ItemLookupRequest extends BasicApiRequest {
	
	private String sourceObjectId;
	private int reviewPage = 1;
	private int tagPage = 1;
	private int tagsPerPage = 10;
	//private String tagSort = "Usages";
	private boolean includeReviewsSummary = true;
	private ItemLookupResponse ilr= null;


	
	public ItemLookupRequest(String sourceObjectId, int reviewPage, int tagPage) {
		super();
		this.sourceObjectId = sourceObjectId;
		this.reviewPage = reviewPage;
		this.tagPage = tagPage;
		
        params.put("Operation", BasicApiRequest.AWS_OPERATION_ITEMLOOKUP);
        params.put("ResponseGroup", BasicApiRequest.AWS_RESPONSE_GROUP_ITEMLOOKUP + "," + BasicApiRequest.AWS_RESPONSE_GROUP_OFFERSUMMARY);
        params.put("IncludeReviewsSummary", String.valueOf(this.includeReviewsSummary));
        params.put("ItemId", this.sourceObjectId);
        params.put("ReviewPage", "2"); //String.valueOf(this.reviewPage));
        params.put("ReviewSort", "SubmissionDate");

//        params.put("TagPage", String.valueOf(this.tagPage));
        
	}
	
	
	protected void buildPrimaryOrVariantImage(Document doc, String type) throws XPathExpressionException {
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile("//Item/DetailPageURL");

		//primary or variant
		expr = xpath.compile("//ImageSets/ImageSet[@Category='" + type + "']/ThumbnailImage");
		NodeList tempLinks1 = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
		if (tempLinks1 != null && tempLinks1.getLength() > 0) {
		
			Image image1 = buildImage(tempLinks1.item(0),Image.CATEGORY_THUMBNAIL);
			if (image1 != null)
				this.ilr.getImages().put(Image.CATEGORY_THUMBNAIL, image1);
		}
		
		
		expr = xpath.compile("//ImageSets/ImageSet[@Category='" + type + "']/TinyImage");
		NodeList tempLinks2 = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
		if (tempLinks2 != null && tempLinks2.getLength() > 0) {
			Image image2 = buildImage(tempLinks2.item(0),Image.CATEGORY_TINY);
			if (image2 != null)
				this.ilr.getImages().put(Image.CATEGORY_TINY, image2);
		}
		
		expr = xpath.compile("//ImageSets/ImageSet[@Category='" + type + "']/SmallImage");
		NodeList tempLinks3 = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
		if (tempLinks3 != null && tempLinks3.getLength() > 0) {
		
			Image image3 = buildImage(tempLinks3.item(0),Image.CATEGORY_SAMLL); 
			if (image3 != null)
			this.ilr.getImages().put(Image.CATEGORY_SAMLL, image3);
		}
		
		expr = xpath.compile("//ImageSets/ImageSet[@Category='" + type + "']/MediumImage");		
		NodeList tempLinks4 = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
		if (tempLinks4 != null && tempLinks4.getLength() > 0) {
		
			Image image4 = buildImage(tempLinks4.item(0),Image.CATEGORY_MEDIUM);
			if (image4 != null)
				this.ilr.getImages().put(Image.CATEGORY_MEDIUM, image4);
		}
		
		expr = xpath.compile("//ImageSets/ImageSet[@Category='" + type + "']/LargeImage");
		NodeList tempLinks5 = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
		if (tempLinks5 != null && tempLinks5.getLength() > 0) {		
			Image image5 = buildImage(tempLinks5.item(0),Image.CATEGORY_LARGE);
			if (image5 != null)
				this.ilr.getImages().put(Image.CATEGORY_LARGE, image5);
		}
		
		expr = xpath.compile("//ImageSets/ImageSet[@Category='" + type + "']/SwatchImage");
		NodeList tempLinks6 = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
		if (tempLinks6 != null && tempLinks6.getLength() > 0) {		
			Image image6 = buildImage(tempLinks6.item(0),Image.CATEGORY_SWATCH);
			if (image6 != null)
				this.ilr.getImages().put(Image.CATEGORY_SWATCH, image6);
		}		
		
	}
	protected Image buildImage(Node imageNode, String imageCategory) {
		if (imageNode == null)
		return null;
		NodeList subNodes = (NodeList)imageNode.getChildNodes();
		Image image = new Image();
		image.setImageCategory(imageCategory);
		for(int i = 0; i< subNodes.getLength();i++) {
			Node node = subNodes.item(i);
			if (node.getNodeName().equalsIgnoreCase("URL".toLowerCase())) {
				image.setUrl(node.getTextContent());
			} else if (node.getNodeName().equalsIgnoreCase("Height".toLowerCase())) {
				image.setHeight(Integer.valueOf(node.getTextContent()));
			} else if (node.getNodeName().equalsIgnoreCase("Width".toLowerCase())) {
				image.setWeight(Integer.valueOf(node.getTextContent()));
			}			
		}
		return image;
	}
	
	protected void buildPriceList(Node priceListNode) throws AwsApiException {
		if (priceListNode == null) return;

		NodeList subNodes = (NodeList)priceListNode.getChildNodes();
		

		Set<String> conditionKeys = ProductCondition.loadKeys().keySet();
		
		for(int i = 0; i< subNodes.getLength();i++) {
			Node node = subNodes.item(i);
			String nodeName = node.getNodeName();

			for(String ck : conditionKeys ) {
				if (nodeName.toLowerCase().contains(ck) && nodeName.toLowerCase().endsWith("price")) {
					Price pp = new Price();
					pp.setCategoryName(ck);
					
					NodeList singPriceSubNodes = (NodeList)node.getChildNodes();
					for(int j = 0; j< singPriceSubNodes.getLength();j++) {
						Node node1 = singPriceSubNodes.item(j);
						
						if (node1.getTextContent().isEmpty()) continue;

						if (node1.getNodeName().equalsIgnoreCase("Amount")) {
							pp.setAmount(Long.valueOf(node1.getTextContent()));
						} else if (node1.getNodeName().equalsIgnoreCase("CurrencyCode")) {
							pp.setCurrencyCode(node1.getTextContent());
						} else if (node1.getNodeName().equalsIgnoreCase("FormattedPrice")) {
							pp.setFormatedPrice(node1.getTextContent());
						} else {
							throw new AwsApiException("unknow price node in response xml");
						}
					}
					
					this.ilr.getPriceList().put(ck, pp);
				} 											
			}
		}
		
		// check stock level
		for(int i = 0; i< subNodes.getLength();i++) {
			Node node = subNodes.item(i);
			String nodeName = node.getNodeName();
			for(String ck : conditionKeys ) {
				if (nodeName.toLowerCase().contains(ck) && nodeName.toLowerCase().startsWith("total")) {
					if (node.getTextContent().isEmpty() || !this.ilr.getPriceList().containsKey(ck)) continue;
					this.ilr.getPriceList().get(ck).setAvailable(Integer.valueOf(node.getTextContent()));					
				}
			}
		}
	}
	
	protected void buildItemAttributes(Node itemAtrributeNode) {
		NodeList subNodes = (NodeList)itemAtrributeNode.getChildNodes();
		for(int i = 0; i< subNodes.getLength();i++) {
			Node node = subNodes.item(i);
			ProductAttributeMetaKey pa = ProductAttributeMetaKey.getProductAttributeByName(node.getNodeName());
			if (pa != null) {
				String value;
				if (node.getNodeName().endsWith("imensions")) {
					value = buildDimensionsString(node);
				} else if (node.getNodeName().equalsIgnoreCase("languages")) {
					value = node.getFirstChild().getFirstChild().getTextContent();
				} else if (node.getNodeName().equalsIgnoreCase("listprice")) {
					value = node.getLastChild().getTextContent();
				} else {
					value = node.getTextContent();
				}
				
				
				if (ilr.getItemAttributes().containsKey(Long.valueOf(pa.getId()))) {
					this.ilr.getItemAttributes().put(Long.valueOf(pa.getId()), (String)ilr.getItemAttributes().get(Long.valueOf(pa.getId())) + BasicApiRequest.AWS_VALUE_SEPARATER + value);
				} else {
					this.ilr.getItemAttributes().put(Long.valueOf(pa.getId()), value);
				}
			}
		}
	}
	
	
	protected String buildDimensionsString(Node dimensionsNode) {
		NodeList subNodes = (NodeList)dimensionsNode.getChildNodes();
        StringBuilder as = new StringBuilder();
        
		for(int i = 0; i< subNodes.getLength();i++) {
			Node node = subNodes.item(i);
			if (as.toString().equals("")) {
				as.append(node.getNodeName() + ":" + node.getTextContent());
			} else {
				as.append(", " + node.getNodeName() + ":" + node.getTextContent());
			}
			
		}
		return as.toString();		
	}

	
	protected void buildTechnicalDetails(String url) throws IOException {
		this.ilr.setTechnicalDetailList(AwsScraper.scrapeTechnicalDetails(url));
	}
	
	protected void buildReview(String url)  throws IOException {
		this.ilr.setReview(AwsScraper.scrapeReview(url));
	}
	
	protected void buildResponse(Document doc) throws XPathExpressionException, AwsApiException {
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile("//Item/DetailPageURL");
		Node rawHtmlNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
//		this.ilr.setTotalItems(Integer.valueOf(itemsCountNode.getTextContent()));
//		expr = xpath.compile("//Items/TotalPages");
//		Node totalPageNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
//		this.ilr.setTotalPage(Integer.valueOf(totalPageNode.getTextContent()));
//		this.ilr.setCurrentPage(this.page);
//		
//		expr = xpath.compile("//Items/Item/ASIN");
//		NodeList itemsNodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
//		for (int i=0;i<itemsNodes.getLength();i++) {
//			Node itemNode = itemsNodes.item(i);
//			this.ilr.getItemsIdList().add(itemNode.getTextContent());
//		}		
		this.ilr.setSourceObjectId(this.sourceObjectId);
		
		this.ilr.setRawXmlContentUrl(this.requestUrl);
		this.ilr.setRawHtmlContentUrl(rawHtmlNode.getTextContent());
		
		expr = xpath.compile("//Item/ItemLinks/ItemLink/Description");
		NodeList itemLinks = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
		for (int i=0;i<itemLinks.getLength();i++) {
			Node childNode = itemLinks.item(i);
			if (childNode.getTextContent().equalsIgnoreCase(BasicApiRequest.AWS_CONTENT_SPECIFICATION.toLowerCase())) {
				this.ilr.setSpecificationUrl(childNode.getNextSibling().getTextContent());				
			} else if (childNode.getTextContent().equalsIgnoreCase(BasicApiRequest.AWS_CONTENT_ALL_REVIEW.toLowerCase())) {
				this.ilr.setReviewUrl(childNode.getNextSibling().getTextContent());
			}
		}		

		expr = xpath.compile("//Item/SalesRank");
		Node salesRankNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
		if (salesRankNode != null) {
			this.ilr.setSalesRank(Long.valueOf(salesRankNode.getTextContent()));
		}
		
		expr = xpath.compile("//Item/OfferSummary");
		Node priceListNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
		buildPriceList(priceListNode);
		
		
		try {
			buildTechnicalDetails(this.ilr.getSpecificationUrl());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new AwsApiException("Aws technical detail scraping error: " + e);
		}
		
		
		try {
			buildReview(this.ilr.getReviewUrl());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new AwsApiException("Aws review scraping error: " + e);
		}
		//_logger.info(HttpConnection.get(this.ilr.getSpecificationUrl(), null));
		
//		Scraper s = new Scraper();
//		s.scrape(this.ilr.getSpecificationUrl(), "<dic class=\"content\">(.*)</div>");

		
		/* Using all review page instead of iframe page.
		expr = xpath.compile("//Item/CustomerReviews/IFrameURL");
		Node reviewUrlNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
		this.ilr.setReviewUrl(reviewUrlNode.getTextContent());
		*/
		// price
//		expr = xpath.compile("//Item/OfferSummary/LowestNewPrice/Amount");
//		Node priceNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
//		this.ilr.setPriceAmount(Long.valueOf(priceNode.getTextContent()));
//		
//		expr = xpath.compile("//Item/OfferSummary/LowestNewPrice/CurrencyCode");
//		Node priceCodeNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
//		this.ilr.setCurrencyCode(priceCodeNode.getTextContent());
		
//		this.ilr.setPriceAmount(-1L);
//		this.ilr.setCurrencyCode("Not Used");
		
		expr = xpath.compile("//EditorialReview/Source");
		Node descriptionSourceNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
		if (descriptionSourceNode!= null && descriptionSourceNode.getTextContent().equalsIgnoreCase(BasicApiRequest.AWS_CONTENT_DESCRIPTION_SOURCE.toLowerCase())) {
			expr = xpath.compile("//EditorialReview/Content");
			Node descriptionNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
			this.ilr.setDescirption(descriptionNode.getTextContent());
		}
		
		//BasicApiRequest
		this.buildPrimaryOrVariantImage(doc, BasicApiRequest.AWS_PRODUCT_IMAGE_PRIMARY);
		if (this.ilr.getImages() == null || this.ilr.getImages().size() == 0 ) {
			this.buildPrimaryOrVariantImage(doc, BasicApiRequest.AWS_PRODUCT_IMAGE_VARIANT);
		}
		
		expr = xpath.compile("//ItemAttributes");
		Node df = (Node)expr.evaluate(doc, XPathConstants.NODE);
		buildItemAttributes(df);

		
		
//		
		
//		
//		for (int i=0;i<imageNodes.getLength();i++) {
//			Node childNode = imageNodes.item(i);
//			
//		}			
		
	    
		
		
		
		
		

		
	}
	
	@Override
	protected BasicApiRespose parseResponse(Document doc)
			throws AwsApiException {
		validApiResponse(doc);
		this.ilr = new ItemLookupResponse();
		
		try {
			buildResponse(doc);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			throw new AwsApiException("Xpath error: " + e.getMessage());
		}
		return this.ilr;	}

}
