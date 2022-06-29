package com.camel.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CamelSpringBootTest
//@CamelAutoConfiguration
@UseAdviceWith
public class CamelProjectApplicationTests {

    @Autowired
    CamelContext context;

    @EndpointInject("mock:result")
    protected MockEndpoint mockEndpoint;

    @Autowired
    ProducerTemplate producerTemplate;
//    @Test
//    public void testSimpleTimer() throws Exception{
//        String a="Hello World";
//
//        mockEndpoint.expectedBodiesReceived(a);
//        mockEndpoint.expectedMinimumMessageCount(1);
//
//        AdviceWith.adviceWith(context,"simpleTimerid",routeBuilder->{
//           routeBuilder.weaveAddLast().to(mockEndpoint);
//        });
//
//        context.start();
//        mockEndpoint.assertIsSatisfied();
//    }

    @Test
    public void testLegacyFileMove1() throws Exception{
        //setup the mock
        String a="This is input file";
        mockEndpoint.expectedBodiesReceived(a);
        mockEndpoint.expectedMinimumMessageCount(1);

        //route defination
        AdviceWith.adviceWith(context,"Legacy",routeBuilder->{
           routeBuilder.weaveByToUri("file:*").replace().to(mockEndpoint);
      });


        //start context and validate mock
        context.start();
        mockEndpoint.assertIsSatisfied();
    }

    @Test
    public void testFileMove2() throws Exception{
        String a="Bitch";
        mockEndpoint.expectedBodiesReceived(a);
        mockEndpoint.expectedMinimumMessageCount(1);

        AdviceWith.adviceWith(context,"Legacy",routeBuilder->{
            routeBuilder.replaceFromWith("direct:mockStart");
           routeBuilder.weaveByToUri("file:*").replace().to(mockEndpoint);
        });


        context.start();
        producerTemplate.sendBody("direct:mockStart",a);
        mockEndpoint.assertIsSatisfied();
    }

//    @Test
//    public void testProcessor() throws Exception{
//        String a="OutboundNameAdress(name=ronaldo, address=7,UTD)";
//        mockEndpoint.expectedBodiesReceived(a);
//        mockEndpoint.expectedMinimumMessageCount(1);
//
//        AdviceWith.adviceWith(context,"Legacy",routeBuilder->{
//            routeBuilder.replaceFromWith("direct:mockStart");
//            routeBuilder.weaveByToUri("file:*").replace().to(mockEndpoint);
//        });
//
//
//        context.start();
//        producerTemplate.sendBody("direct:mockStart","name, club, shirt " &&
//                "ronaldo,7,UTD");
//        mockEndpoint.assertIsSatisfied();
//    }


}
