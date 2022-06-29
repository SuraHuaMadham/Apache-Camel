package com.camel.camel.components;

import com.camel.camel.beans.NameAddress;
import com.camel.camel.processor.InboundMessageProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class BacthJPAProcessingRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:readDB?period=10000").routeId("readDBId")
                .to("jpa:"+ NameAddress.class.getName()+"?namedQuery=fetchAllRows")
                .split(body())
                .process(new InboundMessageProcessor())
                .log(LoggingLevel.INFO,"Transformed Body: ${body} ")
                .convertBodyTo(String.class)
                .to("file:src/data/output?fileName=output.csv&fileExist=append&appendChars=\\n")
                .toD("jpa:"+NameAddress.class.getName()+"?nativeQuery=DELETE FROM NAME_ADDRESS WHERE " +
                        "id=${header.consumeID}&useExecuteUpdate=true")
                .end();
    }
}
