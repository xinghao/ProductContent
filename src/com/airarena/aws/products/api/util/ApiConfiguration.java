package com.airarena.aws.products.api.util;

import java.util.ArrayList;
import java.util.Random;

import com.airarena.products.conf.ConfigElementTags;

public class ApiConfiguration {
	
	private static ArrayList<ApiConfiguration> _instances = null;
	private static int listIndex = 0;
	private String awsAccessKeyId;
	private String awsSecretKey;
	private String awsApiVersion;
	private String awsEndPoint;
	private String awsAssociateTag;
    
	private ApiConfiguration(String awsAccessKeyId, String awsSecretKey, String awsAssociateTag) {
//		this.awsAccessKeyId = (String) System.getProperty(ConfigElementTags.AWS_ACCESS_KEY_ID);
//		this.awsSecretKey = (String) System.getProperty(ConfigElementTags.AWS_SECRET_KEY);
		this.awsApiVersion = (String) System.getProperty(ConfigElementTags.AWS_API_VERSION);
		this.awsEndPoint = (String) System.getProperty(ConfigElementTags.AWS_ENDPOINT);
//		this.awsAssociateTag = (String) System.getProperty(ConfigElementTags.AWS_ASSOCIATETAG);
		this.awsAccessKeyId = awsAccessKeyId;
		this.awsSecretKey = awsSecretKey;
		this.awsAssociateTag = awsAssociateTag;
		ApiConfiguration._instances.add(this);
	}
	
	public static ApiConfiguration getInstance() {
		if(ApiConfiguration._instances == null) {
			listIndex = 0;
			ApiConfiguration._instances = new ArrayList<ApiConfiguration>();
			ApiConfiguration._instances.add(new ApiConfiguration("AKIAJSR3VSJWDRDRRP6A", "lUW7dz1hKLhMBa4ZKF64UKTLC59pkRaxyqzgw8zP", "xingza-20"));
			ApiConfiguration._instances.add(new ApiConfiguration("AKIAJ3P3UODLSW5VP3XQ", "xWZ72mryi+UXTbk9ptj6T7zN956OZI5mqSeIjFzm", "crazytvs-20"));
			ApiConfiguration._instances.add(new ApiConfiguration("AKIAIVEX4SSUHD7BH36A", "e4sxxdPb65OnU+5meXczCTKxzweyuXO4hEEUciXP", "textbooksfore-20"));
	      }
		
		//Random randomGenerator = new Random();
		//return ApiConfiguration._instances.get(randomGenerator.nextInt(ApiConfiguration._instances.size()));
		listIndex++;
		if (listIndex == 3) listIndex = 0;
		return ApiConfiguration._instances.get(listIndex);
		

	}
	
	public String getAwsAccessKeyId() {
		return awsAccessKeyId;
	}

	public String getAwsSecretKey() {
		return awsSecretKey;
	}

	public String getAwsApiVersion() {
		return awsApiVersion;
	}

	public String getAwsEndPoint() {
		return awsEndPoint;
	}

	public String getAwsAssociateTag() {
		return awsAssociateTag;
	}	

}
