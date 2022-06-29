package com.camel.camel.components;

import com.camel.camel.beans.InboundRestProcessorBean;
import com.camel.camel.beans.NameAddress;
import com.camel.camel.processor.InboundMessageProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import java.net.ConnectException;

@Component
public class NewRestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        Predicate isNumber = header("number").isEqualTo("7");

        onException(JMSException.class, ConnectException.class).routeId("ExceptionId")
                .handled(true).log(LoggingLevel.INFO,"JMS Exception handled-> Gracefully");

        restConfiguration().component("jetty").host("0.0.0.0").port("8080")
                .bindingMode(RestBindingMode.json).enableCORS(true);

        rest("masterclass")
                .produces("application/json")
                .post("nameAddress").type(NameAddress.class)
                .route().routeId("NewRestRouteID")
                .log(LoggingLevel.INFO,"${body}")

//                .process(new InboundMessageProcessor())
//                .log(LoggingLevel.INFO,"Transformed Body: ${body} ")
//                .convertBodyTo(String.class)
//                .to("file:src/data/output?fileName=output.csv&fileExist=append&appendChars=\\n");
                //.multicast() (for simultaneous message)
//                   .to("jpa:"+NameAddress.class.getName())
//                   .to("activemq:queue:nameaddressqueue?exchangePattern=InOnly");
//route process to different location wiretap
 //               .bean(new InboundRestProcessorBean(),"ValidateAnother")
                .bean(new InboundRestProcessorBean())
                //Setup Rule
                //If number=7 -> send to MQ else send to both
                .choice()
                .when(isNumber)
                     .to("direct:toActiveMQ")
                .otherwise()
                     .to("direct:toActiveMQ")
                     .to("direct:toDB")
                .end()
//                .log(LoggingLevel.INFO,">>>> Sending to DB EP")
//                .to("direct:toDB")
//                .log(LoggingLevel.INFO,">>>> Sending to AMQ EP")
//                .to("direct:toActiveMQ")
                .setHeader(Exchange.HTTP_RESPONSE_CODE,constant(200))
                .transform().simple("Message Processed and Result generated with body : ${body}")
                .endRest();

        from("direct:toDB").routeId("toDBId")
                .log(LoggingLevel.INFO,">>>> In DB EP")
                .to("jpa:"+NameAddress.class.getName());

        from("direct:toActiveMQ").routeId("toActiveMQId")
                .log(LoggingLevel.INFO,">>>> In AMQ EP")
                .to("activemq:queue:nameaddressqueue?exchangePattern=InOnly");
    }
}
