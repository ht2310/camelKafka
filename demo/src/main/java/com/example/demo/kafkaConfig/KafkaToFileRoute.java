package com.example.demo.kafkaConfig;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;


@Component
public class KafkaToFileRoute extends RouteBuilder {

    @Value("${aggregator.groupSize}")
    private int groupSize;

    @Override
    public void configure() throws Exception {
        from("kafka:my-messages-topic?brokers=localhost:9092&groupId=my-consumer-group-test&autoOffsetReset=earliest")
                .routeId("KafkaToFileRoute")
                .log("Raw Kafka Message: ${body}")
                .filter().simple("${body} regex '\\{.*:.*\\}'")
                .unmarshal().json(JsonLibrary.Jackson)
                .log("After Unmarshal: ${body}")
                .process(exchange -> {
                    Map<String, Object> body = exchange.getIn().getBody(Map.class);
                    if (body != null) {
                        body.put("processedAt", LocalDateTime.now().toString());
                        if (body.containsKey("text")) {
                            body.put("text", ((String) body.get("text")).toUpperCase());
                        }
                        exchange.getIn().setBody(body);
                    }
                })
                .log("After Processing: ${body}")
                .aggregate(simple("${body[id]}"), new MessageAggregationStrategy())
                .completionSize(simple("{{aggregator.groupSize}}"))
                .completionTimeout(30000)
                .marshal().json(JsonLibrary.Jackson)
                .log("Aggregated and Marshalled Message: ${body}")
                .to("file:D:\\POC\\demo\\output?fileName=grouped-${header.CamelAggregatedCorrelationKey}.json")
                .end();
    }
}