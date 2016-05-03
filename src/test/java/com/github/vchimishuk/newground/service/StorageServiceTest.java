package com.github.vchimishuk.newground.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class StorageServiceTest {
    @Test
    public void testPutGet() throws IOException {
        ParserService parser = new ParserService();
        File file = File.createTempFile("newground-test-", ".csv");
        file.deleteOnExit();
        StorageService storage = new StorageService(parser, file);

        storage.put(10, new BigDecimal(1));
        Assert.assertEquals(Optional.of(new BigDecimal(1)),
                storage.get(10));
        storage.put(5, new BigDecimal(2));
        Assert.assertEquals(Optional.of(new BigDecimal(2)),
                storage.get(5));
        storage.put(0, new BigDecimal(3));
        Assert.assertEquals(Optional.of(new BigDecimal(3)),
                storage.get(0));
        storage.put(4, new BigDecimal(4));
        Assert.assertEquals(Optional.of(new BigDecimal(4)),
                storage.get(4));

        Assert.assertEquals(Optional.empty(), storage.get(1));
        Assert.assertEquals(Optional.empty(), storage.get(2));
        Assert.assertEquals(Optional.empty(), storage.get(3));
        Assert.assertEquals(Optional.empty(), storage.get(6));
        Assert.assertEquals(Optional.empty(), storage.get(11));

        String expected = "3,,,,4,2,,,,,1";
        String actual = IOUtils.toString(new FileInputStream(file));

        Assert.assertEquals(expected, actual);
    }
}
