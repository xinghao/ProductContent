package com.airarena.aws.products.api.util;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class ItemSearchRequest extends BasicApiRequest {
		
	
	private String browseNode = null;
	private Long minimumPrice = null;
	private String searchIndex = null;
	private String sort = BasicApiRequest.AWS_SORT_US_ELECTRONIC_ASC;
	private int page = 1;

	
	private ItemSearchResponse tsr = null;

	public ItemSearchRequest(String browseNode, String searchIndex, int page, String sort, Long minimumPrice) {
		super();
		this.browseNode = browseNode;
		this.searchIndex = searchIndex;
		this.page = page;
		if (sort != null) this.sort = sort;
		if (minimumPrice != null) this.minimumPrice = minimumPrice;
        params.put("Operation", BasicApiRequest.AWS_OPERATION_ITEMSEARCH);
        params.put("ResponseGroup", BasicApiRequest.AWS_RESPONSE_GROUP_ITEMIDS + "," + BasicApiRequest.AWS_RESPONSE_GROUP_OFFERSUMMARY);
        params.put("BrowseNode", this.browseNode);
        params.put("ItemPage", String.valueOf(this.page));
        params.put("SearchIndex", this.searchIndex);
        params.put("Sort", this.sort);
        if(minimumPrice != null && minimumPrice > 0) {
        	params.put("MinimumPrice", String.valueOf(this.minimumPrice));
        }        
	}

	
	@Override
	protected BasicApiRespose parseResponse(Document doc)
			throws AwsApiException {
		
		validApiResponse(doc);
		this.tsr = new ItemSearchResponse();
		
		try {
			buildResponse(doc);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			throw new AwsApiException("Xpath error: " + e.getMessage());
		}
		return this.tsr;
	}


	protected void buildResponse(Document doc) throws XPathExpressionException {
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile("//Items/TotalResults");
		Node itemsCountNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
		this.tsr.setTotalItems(Integer.valueOf(itemsCountNode.getTextContent()));
		expr = xpath.compile("//Items/TotalPages");
		Node totalPageNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
		this.tsr.setTotalPage(Integer.valueOf(totalPageNode.getTextContent()));
		this.tsr.setCurrentPage(this.page);
				
		expr = xpath.compile("//Items/Item/ASIN");
		NodeList itemsNodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
		for (int i=0;i<itemsNodes.getLength();i++) {
			Node itemNode = itemsNodes.item(i);
			this.tsr.getItemsIdList().add(itemNode.getTextContent());
		}
		
		expr = xpath.compile("//Items/Item/OfferSummary/LowestNewPrice/Amount");
		NodeList pricesNodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
		this.tsr.setPageLowerNewPrice(Long.valueOf(pricesNodes.item(pricesNodes.getLength() - 1).getTextContent()));
		
	}

}
