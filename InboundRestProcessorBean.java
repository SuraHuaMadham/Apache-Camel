package com.camel.camel.beans;

import org.apache.camel.Exchange;

public class InboundRestProcessorBean {
    public void validate(Exchange exchange){
        NameAddress nameAddress=exchange.getIn().getBody(NameAddress.class);
        exchange.getIn().setHeader("Number", nameAddress.getNumber());
    }
}
