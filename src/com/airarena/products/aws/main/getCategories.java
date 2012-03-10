package com.airarena.products.aws.main;

public class getCategories {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Set the service:
		com.ECS.client.jax.AWSECommerceService service = new com.ECS.client.jax.AWSECommerceService();

		//Set the service port:
		com.ECS.client.jax.AWSECommerceServicePortType port = service.getAWSECommerceServicePort();

		//Get the operation object:
		com.ECS.client.jax.ItemSearchRequest itemRequest = new com.ECS.client.jax.ItemSearchRequest();

		//Fill in the request object:
		itemRequest.setSearchIndex("Books");
		itemRequest.setKeywords("dog");
//		itemRequest.setVersion("2011-08-01");
		com.ECS.client.jax.ItemSearch ItemElement= new com.ECS.client.jax.ItemSearch();
		ItemElement.setAWSAccessKeyId("AKIAJSR3VSJWDRDRRP6A");
		ItemElement.setAssociateTag("xingza-20");
		ItemElement.getRequest().add(itemRequest);

		//Call the Web service operation and store the response
		//in the response object:
		com.ECS.client.jax.ItemSearchResponse
		    response = port.itemSearch(ItemElement);
	}

}
