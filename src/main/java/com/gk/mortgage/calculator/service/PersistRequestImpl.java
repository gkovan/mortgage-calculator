package com.gk.mortgage.calculator.service;

import org.springframework.stereotype.Service;

import com.gk.mortgage.calculator.domain.MortgageCalculatorDataToPersist;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Service
public class PersistRequestImpl implements PersistRequest {

    private final DynamoDbClient ddb = DynamoDbClient.builder()
            .region(Region.US_EAST_1)
            .build();
    private final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(ddb)
            .build();

    private final String flightsTableName = "mortgage-calculator-dev";

    public String saveToDB(MortgageCalculatorDataToPersist requestDataToPersist) {
        DynamoDbTable<MortgageCalculatorDataToPersist> mappedTable = enhancedClient
                .table(flightsTableName, TableSchema.fromBean(MortgageCalculatorDataToPersist.class));

        mappedTable.putItem(requestDataToPersist);

        return requestDataToPersist.getTimestamp();
    }
}
