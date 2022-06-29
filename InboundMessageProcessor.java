package com.camel.camel.processor;

import com.camel.camel.beans.NameAddress;
import com.camel.camel.beans.OutboundNameAdress;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InboundMessageProcessor implements Processor {

    Logger logger= LoggerFactory.getLogger(getClass());
    @Override
    public void process(Exchange exchange) throws Exception {

        NameAddress nameAddress=exchange.getIn().getBody(NameAddress.class);
        exchange.getIn().setBody(new OutboundNameAdress(nameAddress.getName(),
                returnOutboundAddress(nameAddress)));
        exchange.getIn().setHeader("consumeID",nameAddress.getId());

    }
    private String returnOutboundAddress(NameAddress nameAddress){
        StringBuilder concat=new StringBuilder(200);
        concat.append(nameAddress.getNumber() + ",");
        concat.append(nameAddress.getClub());

        return concat.toString();
    }
}
