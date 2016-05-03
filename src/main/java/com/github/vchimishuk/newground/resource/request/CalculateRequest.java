package com.github.vchimishuk.newground.resource.request;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.core.style.ToStringCreator;

@XmlRootElement(name = "request")
public class CalculateRequest {
    @XmlElement
    private int source;

    @XmlElement
    private int destination;

    @XmlElement
    private BigDecimal number;

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }

    public BigDecimal getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("source", source)
                .append("destination", destination)
                .append("number", number)
                .toString();
    }
}
