package com.airarena.aws.products.api.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.airarena.products.conf.ConfigElementTags;

public class ApiConfiguration {
	
	///private static ArrayList<ApiConfiguration> _instances = null;
	private static int listIndex = 0;
	private String awsAccessKeyId;
	private String awsSecretKey;
	private String awsApiVersion;
	private String awsEndPoint;
	private String awsAssociateTag;
	private static Queue<ApiConfiguration> _instances;
    
	private ApiConfiguration(String awsAccessKeyId, String awsSecretKey, String awsAssociateTag) {
//		this.awsAccessKeyId = (String) System.getProperty(ConfigElementTags.AWS_ACCESS_KEY_ID);
//		this.awsSecretKey = (String) System.getProperty(ConfigElementTags.AWS_SECRET_KEY);
		this.awsApiVersion = (String) System.getProperty(ConfigElementTags.AWS_API_VERSION);
		this.awsEndPoint = (String) System.getProperty(ConfigElementTags.AWS_ENDPOINT);
//		this.awsAssociateTag = (String) System.getProperty(ConfigElementTags.AWS_ASSOCIATETAG);
		this.awsAccessKeyId = awsAccessKeyId;
		this.awsSecretKey = awsSecretKey;
		this.awsAssociateTag = awsAssociateTag;
		//ApiConfiguration._instances.add(this);
	}
	
	public static ApiConfiguration getInstance() {
		if(ApiConfiguration._instances == null) {
//			listIndex = -1;
			ApiConfiguration._instances = new LinkedList<ApiConfiguration>();
//			ApiConfiguration._instances = new ArrayList<ApiConfiguration>();
			ApiConfiguration._instances.add(new ApiConfiguration("AKIAJSR3VSJWDRDRRP6A", "lUW7dz1hKLhMBa4ZKF64UKTLC59pkRaxyqzgw8zP", "xingza-20"));
			ApiConfiguration._instances.add(new ApiConfiguration("AKIAJ3P3UODLSW5VP3XQ", "xWZ72mryi+UXTbk9ptj6T7zN956OZI5mqSeIjFzm", "crazytvs-20")); //jameswu36@y7mail.com
			ApiConfiguration._instances.add(new ApiConfiguration("AKIAIVEX4SSUHD7BH36A", "e4sxxdPb65OnU+5meXczCTKxzweyuXO4hEEUciXP", "textbooksfore-20"));//jamesgu@y7mail.com
			ApiConfiguration._instances.add(new ApiConfiguration("AKIAIXM32RH6J5YV3R7A", "PbH7s47eEXwqtVw4pRd2B/auUZE7ep2NJO8/yx7G", "valiantglass-20"));//zhangmichael@y7mail.com
			ApiConfiguration._instances.add(new ApiConfiguration("AKIAIPMUKGFB2EMBZO6Q", "tgZvoLuGSA4MDzvpH0E1WggpVdXXEWbmi8hLZNb/", "secondhand05-20")); // michael8001_2@hotmail.com
			ApiConfiguration._instances.add(new ApiConfiguration("AKIAJYXDY6HKIKFW3YIQ", "OOy764ENsxQAfuHhuqWIx17PWK/R7SzbX1wn3hpY", "ledtvsydney-20"));//james_8001_1@hotmail.com
			ApiConfiguration._instances.add(new ApiConfiguration("AKIAJRF7NSA6VDFTCFAQ", "BxCuMPyZkINq2i79IyTC1R9Iqw3IhUbwIKYOn1Di", "retravision-20")); //xin_wu@y7mail.com
			ApiConfiguration._instances.add(new ApiConfiguration("AKIAIPBY2MU24S7LMZIQ", "lngFP0HOQR2qutKXlZs8Ck45A23nlYR/4vtc9Jtb", "gumtree0c1-20"));//feizhang@y7mail.com
			ApiConfiguration._instances.add(new ApiConfiguration("AKIAJBV2JLBAYFXZ4DPA", "XuuHd7j/EOWO4db9B0O/sDIVBDXSrR9/mznSB6PV", "huthwaite0d-20"));//xu.david@y7mail.com
			ApiConfiguration._instances.add(new ApiConfiguration("AKIAIYYYH3UV2ZNCJTUQ", "kwpY5dAHFZjBPzA20TljBEI/1BYG7JRQbqOntKff", "tvmarket-20"));//dwainesu@y7mail.com
	      }
		
		//Random randomGenerator = new Random();
		//return ApiConfiguration._instances.get(randomGenerator.nextInt(ApiConfiguration._instances.size()));
//		listIndex++;
//		 ArrayList<ApiConfiguration> aa = ApiConfiguration._instances ;
//		if (listIndex == 10) listIndex = 0;
//		System.out.println(listIndex);
//		ApiConfiguration __instance = ApiConfiguration._instances.get(listIndex);
//		return ApiConfiguration._instances.get(listIndex);
		ApiConfiguration __instance = ApiConfiguration._instances.poll();
		ApiConfiguration._instances.add(__instance);
		return __instance;
		

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
