package com.github.vchimishuk.newground.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.github.vchimishuk.newground.model.Range;

import org.junit.Assert;
import org.junit.Test;

public class ParserServiceTest {
    @Test
    public void testSize() {
        ParserService parser = new ParserService();

        Assert.assertEquals(0, parser.size(""));
        Assert.assertEquals(1, parser.size("1.0"));
        Assert.assertEquals(5, parser.size("1,2,3,4,5"));
        Assert.assertEquals(5, parser.size("1,,,,5"));
        Assert.assertEquals(7, parser.size(",1,,,,5,"));
    }

    @Test
    public void testFind() {
        ParserService parser = new ParserService();

        Assert.assertEquals(Optional.empty(),
                parser.find("", 0));
        Assert.assertEquals(Optional.of(new Range(0, 1)),
                parser.find("1", 0));
        Assert.assertEquals(Optional.of(new Range(4, 7)),
                parser.find("123,456,789", 1));
        Assert.assertEquals(Optional.of(new Range(8, 11)),
                parser.find("123,456,789", 2));
        Assert.assertEquals(Optional.empty(),
                parser.find("123,456,789", 3));
    }

    @Test
    public void testParse() {
        ParserService parser = new ParserService();

        Assert.assertEquals(Optional.empty(),
                parser.parse("", 0));
        Assert.assertEquals(Optional.of(new BigDecimal(1)),
                parser.parse("1", 0));
        Assert.assertEquals(Optional.of(new BigDecimal("4.56")),
                parser.parse("123,4.56,789", 1));
        Assert.assertEquals(Optional.of(new BigDecimal("78.9")),
                parser.parse("123,456,78.9", 2));
        Assert.assertEquals(Optional.empty(),
                parser.parse("123,456,789", 3));
    }
}
