package com.airarena.aws.products.api.util;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public abstract class BasicApiRequest {
	
	public static final String AWS_OPERATION_BROWSENODELOOKUP = "BrowseNodeLookup";	
	public static final String AWS_OPERATION_ITEMSEARCH = "ItemSearch";
	public static final String AWS_OPERATION_ITEMLOOKUP = "ItemLookup";
	
	public static final String AWS_RESPONSE_GROUP_LARGE = "Large";
	public static final String AWS_RESPONSE_GROUP_SMALL = "Small";
	public static final String AWS_RESPONSE_GROUP_ITEMIDS = "ItemIds";
//	public static final String AWS_RESPONSE_GROUP_TAGS = "Tags,TagsSummary";
	public static final String AWS_RESPONSE_GROUP_EDITORIALREVIEW = "EditorialReview";
	public static final String AWS_RESPONSE_GROUP_IMAGES = "Images";
	public static final String AWS_RESPONSE_GROUP_ITEMATTRIBUTES = "ItemAttributes";
	public static final String AWS_RESPONSE_GROUP_REVIEWS = "Reviews";
	public static final String AWS_RESPONSE_GROUP_SALESRANK = "SalesRank";
	public static final String AWS_CONTENT_SPECIFICATION = "Technical Details";
	public static final String AWS_CONTENT_DESCRIPTION_SOURCE = "Product Description";
	
	public static final String AWS_VALUE_SEPARATER = "|||||";
	
	public static final String AWS_RESPONSE_GROUP_ITEMLOOKUP = AWS_RESPONSE_GROUP_EDITORIALREVIEW +
	"," + AWS_RESPONSE_GROUP_IMAGES + 
	"," + AWS_RESPONSE_GROUP_ITEMATTRIBUTES + 
	"," + AWS_RESPONSE_GROUP_REVIEWS +
//	"," + AWS_RESPONSE_GROUP_TAGS + 
	"," + AWS_RESPONSE_GROUP_SALESRANK;
 	
	public static String AWS_SEARCHINDEX_ELECTRONICS = "Electronics";
	
	protected Map<String, String> params = new HashMap<String, String>();
	protected String requestUrl;
	
	public BasicApiRespose call() {
		
    	/*
         * Set up the signed requests helper 
         */
        SignedRequestsHelper helper;
        ApiConfiguration apiConf = ApiConfiguration.getInstance();
        try {
            helper = SignedRequestsHelper.getInstance(apiConf.getAwsEndPoint(), apiConf.getAwsAccessKeyId(), apiConf.getAwsSecretKey());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        String requestUrl = null;




        
        params.put("Service", "AWSECommerceService");
        params.put("Version", apiConf.getAwsApiVersion());
        //params.put("Operation", this.operation);
        params.put("AssociateTag", apiConf.getAwsAssociateTag());
        //params.put("ResponseGroup", this.responseGroup);
        
        
        requestUrl = helper.sign(params);
        System.out.println("Signed Request is \"" + requestUrl + "\"");
        this.requestUrl = requestUrl;
        
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestUrl);
            return parseResponse(doc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        

        
	}

	
	
	protected boolean validApiResponse(Document doc) throws AwsApiException {
		try {
		    Node requestProcessingTimeNode = doc.getElementsByTagName("RequestProcessingTime").item(0);
		    System.out.println("Result Time = " + requestProcessingTimeNode.getTextContent());	
		    Node validNode = doc.getElementsByTagName("IsValid").item(0);
		    System.out.println("isValid? = " + validNode.getTextContent());
		    if (validNode.getTextContent().equalsIgnoreCase("False")) {
		    	throw new AwsApiException("Response valid failed! valid equals false");
		    }        	
        
		} catch(Exception e) {
			throw new AwsApiException("Response valid failed! + " + e.getMessage());
		}
		return true;
	}
	
	
	protected abstract BasicApiRespose parseResponse(Document doc) throws AwsApiException;

}
