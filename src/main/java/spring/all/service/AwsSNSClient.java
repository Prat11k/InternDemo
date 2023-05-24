package spring.all.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;


@Service
public class AwsSNSClient {

	Logger logger = LoggerFactory.getLogger(AwsSNSClient.class);
	public static final String accessKey = "AKIA2W4GCXAW4VJC67FN";
	public static final String secretKey = "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";
	public static final String arn = "arn:aws:sns:ap-south-1:736331216941:iscolto";
	Regions region = Regions.AP_SOUTH_1;
	String applicationId = "";
	 int allowedAttempts = 3;
     int codeLength = 6;
     int validityPeriod = 15;
     String brandName = "iscolto";
     String language = "en-US";
     public static final String destinationNumber = "+917709402088";
	
	public PublishResult sendSingleSMS(String message, String phoneNumber) {
	//AmazonSNS snsClient = AmazonSNSClient.builder().withRegion(Regions.AP_SOUTH_1).build();
	AmazonSNS snsClient = AmazonSNSClientBuilder.standard().withRegion(region)
			.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey))).build();
	 logger.info(String.format("sending message to : %s",phoneNumber));
		Map<String, MessageAttributeValue> smsAttribute = new HashMap<>();
//	smsAttribute.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue().withStringValue("iscolto").withDataType("String"));
	smsAttribute.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));
//    smsAttribute.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue().withStringValue("0.05").withDataType("Number"));
	PublishResult result = snsClient.publish(new PublishRequest()
									.withMessage(message)
									.withPhoneNumber(phoneNumber)
									.withMessageAttributes(smsAttribute));
	return result;
	}
	
}
