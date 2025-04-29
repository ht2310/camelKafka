package com.example.demo.kafkaConfig;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import java.util.ArrayList;
import java.util.List;

public class MessageAggregationStrategy implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            List<Object> list = new ArrayList<>();
            list.add(newExchange.getIn().getBody());
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            List<Object> list = oldExchange.getIn().getBody(List.class);
            list.add(newExchange.getIn().getBody());
            return oldExchange;
        }
    }
}
