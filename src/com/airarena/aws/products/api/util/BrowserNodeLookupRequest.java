package com.airarena.aws.products.api.util;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BrowserNodeLookupRequest extends BasicApiRequest {
	
	protected String  browseNodeId = null;
	private static String ROOT_CATEGORY_NAME = "Categories";
	private BrowserNodelLookupResponse b = null;
	
	public BrowserNodeLookupRequest(String browseNodeId) {
		super();
		this.browseNodeId = browseNodeId;	
        params.put("Operation", BasicApiRequest.AWS_OPERATION_BROWSENODELOOKUP);
        params.put("BrowseNodeId", this.browseNodeId);  		
	}
	
	
	public String getBrowseNodeId() {
		return browseNodeId;
	}

	public void setBrowseNodeId(String browseNodeId) {
		this.browseNodeId = browseNodeId;
	}

	protected void buildResponse(Document doc) throws XPathExpressionException {
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile("//BrowseNodes/BrowseNode/Name");
		Node nameNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
		if (nameNode.getTextContent().equalsIgnoreCase(ROOT_CATEGORY_NAME)) {
			expr = xpath.compile("//Ancestors/BrowseNode/Name");
			nameNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
			this.b.setParentSourceObjectId(null);
			this.b.setName(nameNode.getTextContent());
		} else {
			this.b.setName(nameNode.getTextContent());
			expr = xpath.compile("//Ancestors/BrowseNode/BrowseNodeId");
			Node parentIdNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
			this.b.setParentSourceObjectId(parentIdNode.getTextContent());			
		}
		
		expr = xpath.compile("//BrowseNodes/BrowseNode/Children/BrowseNode/BrowseNodeId");
		NodeList childrenNodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
		for (int i=0;i<childrenNodes.getLength();i++) {
			Node childNode = childrenNodes.item(i);
			this.b.getChildrenSourceObjectId().add(childNode.getTextContent());
		}
		

	}
	
	@Override
	protected BasicApiRespose parseResponse(Document doc) throws AwsApiException {
		validApiResponse(doc);
		this.b = new BrowserNodelLookupResponse();
		this.b.setSourceObjectId(this.browseNodeId);



		try {
			buildResponse(doc);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			throw new AwsApiException("Xpath error: " + e.getMessage());
		}
		  
		try {
			Thread.sleep(500); //extra 500 ms here
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
//	    Node browserNodes = doc.getElementsByTagName("").item(0);

		// TODO Auto-generated method stub
		return this.b;
	}
	
	

}
