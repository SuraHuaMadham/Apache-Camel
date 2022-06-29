package com.camel.camel.components;

import com.camel.camel.beans.NameAddress;
import com.camel.camel.processor.InboundMessageProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LegacyFileRoute extends RouteBuilder {

    Logger logger= LoggerFactory.getLogger(getClass());
    BeanIODataFormat inboundDataFormat=new BeanIODataFormat
            ("inboundMessageBeanIOMapping.xml","inputMessageStream");

    @Override
    public void configure() throws Exception {
        from("file:src/data/input?fileName=input.csv").routeId("Legacy")
                .split(body().tokenize("\n",1,true))
                .unmarshal(inboundDataFormat)
                .process(new InboundMessageProcessor())
                .log(LoggingLevel.INFO,"Transformed Body: ${body} ")
                .convertBodyTo(String.class)
        .to("file:src/data/output?fileName=output.csv&fileExist=append&appendChars=\\n")
                .end();
    }

    // Internal Processor
//    .process(exchange->{
//        NameAddress filedata=exchange.getIn().getBody(NameAddress.class);
//        logger.info("This is filedata "+filedata.toString());
//        //exchange.getIn().setBody(filedata.toString());
//    })
}
