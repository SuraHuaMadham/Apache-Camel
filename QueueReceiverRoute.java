package com.camel.camel.components;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class QueueReceiverRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("activemq:queue:nameaddressqueue").routeId("queuereciverID")
                .log(LoggingLevel.INFO,">>>>>> Message Received from Queue: ${body}");
    }
}
