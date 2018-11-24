package com.aws.java.dynamodb;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.regions.*;;

public class SavePersonHandler implements RequestHandler<PersonRequest, PersonResponse>{
	
	 private DynamoDB dynamoDb;
	    private String DYNAMODB_TABLE_NAME = "Person";
	    private Regions REGION = Regions.US_EAST_1;
	   

	    public PersonResponse handleRequest(
	    	      PersonRequest personRequest, Context context) {
	    		  System.out.println("gfhgfhgfhgfh");  	
	    	  
	    	        this.initDynamoDbClient();
	    	 
	    	      persistData(personRequest);   
	    	 
	    	        PersonResponse personResponse = new PersonResponse();
	    	        personResponse.setMessage("Saved Successfully!!!");
	    	        return personResponse;
	    	    }
	    	 
	    	    private PutItemOutcome persistData(PersonRequest personRequest) 
	    	      throws ConditionalCheckFailedException {
	    	        return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME)
	    	          .putItem(
	    	            new PutItemSpec().withItem(new Item()
	    	              .withString("firstName", personRequest.getFirstName())
	    	              .withString("lastName", personRequest.getLastName())));
	    	    }
	    	 
	    	    private void initDynamoDbClient() {
	    	        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
	    	        client.setRegion(Region.getRegion(REGION));
	    	        this.dynamoDb = new DynamoDB(client);
	    	    }


}
