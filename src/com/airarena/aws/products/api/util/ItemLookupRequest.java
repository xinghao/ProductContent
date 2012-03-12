package com.airarena.aws.products.api.util;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.airarena.product.resources.Image;
import com.airarena.products.model.ProductAttributeMetaKey;

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
        params.put("ResponseGroup", BasicApiRequest.AWS_RESPONSE_GROUP_ITEMLOOKUP);
        params.put("IncludeReviewsSummary", String.valueOf(this.includeReviewsSummary));
        params.put("ItemId", this.sourceObjectId);
        params.put("ReviewPage", String.valueOf(this.reviewPage));
//        params.put("TagPage", String.valueOf(this.tagPage));
        
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

	protected void buildResponse(Document doc) throws XPathExpressionException {
		
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

		expr = xpath.compile("//Item/SalesRank");
		Node salesRankNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
		if (salesRankNode != null) {
			this.ilr.setSalesRank(Long.valueOf(salesRankNode.getTextContent()));
		}
		
		for (int i=0;i<itemLinks.getLength();i++) {
			Node childNode = itemLinks.item(i);
			if (childNode.getTextContent().equalsIgnoreCase(BasicApiRequest.AWS_CONTENT_SPECIFICATION.toLowerCase())) {
				this.ilr.setSpecificationUrl(childNode.getNextSibling().getTextContent());
				break;
			}									
		}		
		
		expr = xpath.compile("//Item/CustomerReviews/IFrameURL");
		Node reviewUrlNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
		this.ilr.setReviewUrl(reviewUrlNode.getTextContent());
		
		
		
		expr = xpath.compile("//EditorialReview/Source");
		Node descriptionSourceNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
		if (descriptionSourceNode.getTextContent().equalsIgnoreCase(BasicApiRequest.AWS_CONTENT_DESCRIPTION_SOURCE.toLowerCase())) {
			expr = xpath.compile("//EditorialReview/Content");
			Node descriptionNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
			this.ilr.setDescirption(descriptionNode.getTextContent());
		}
		
		expr = xpath.compile("//ImageSets/ImageSet[@Category='primary']/ThumbnailImage");
		Image image1 = buildImage((Node)expr.evaluate(doc, XPathConstants.NODE),Image.CATEGORY_THUMBNAIL);
		if (image1 != null)
			this.ilr.getImages().put(Image.CATEGORY_THUMBNAIL, image1);
		
		expr = xpath.compile("//ImageSets/ImageSet[@Category='primary']/TinyImage");
		Image image2 = buildImage((Node)expr.evaluate(doc, XPathConstants.NODE),Image.CATEGORY_TINY);
		if (image2 != null)
			this.ilr.getImages().put(Image.CATEGORY_TINY, image2);

		expr = xpath.compile("//ImageSets/ImageSet[@Category='primary']/SmallImage");
		Image image3 = buildImage((Node)expr.evaluate(doc, XPathConstants.NODE),Image.CATEGORY_SAMLL); 
		if (image3 != null)
		this.ilr.getImages().put(Image.CATEGORY_SAMLL, image3);

		expr = xpath.compile("//ImageSets/ImageSet[@Category='primary']/MediumImage");
		Image image4 = buildImage((Node)expr.evaluate(doc, XPathConstants.NODE),Image.CATEGORY_MEDIUM);
		if (image4 != null)
			this.ilr.getImages().put(Image.CATEGORY_MEDIUM, image4);

		expr = xpath.compile("//ImageSets/ImageSet[@Category='primary']/LargeImage");
		Image image5 = buildImage((Node)expr.evaluate(doc, XPathConstants.NODE),Image.CATEGORY_LARGE);
		if (image5 != null)
			this.ilr.getImages().put(Image.CATEGORY_LARGE, image5);
		
		expr = xpath.compile("//ItemAttributes");
		buildItemAttributes((Node)expr.evaluate(doc, XPathConstants.NODE));

		
		
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
