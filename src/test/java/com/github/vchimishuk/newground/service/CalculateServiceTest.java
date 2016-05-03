package com.github.vchimishuk.newground.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

public class CalculateServiceTest {
    @Test
    public void testCalculateGet() throws IOException {
        File sourceFile = File.createTempFile("newground-test-", ".csv");
        sourceFile.deleteOnExit();
        File calculationsFile = File.createTempFile("newground-test-", ".csv");
        calculationsFile.deleteOnExit();

        ParserService parser = new ParserService();
        StorageService sourceStorage = new StorageService(parser, sourceFile);
        StorageService calculationsStorage = new StorageService(parser, calculationsFile);
        NumbersService numbers = new NumbersService(sourceStorage, calculationsStorage);

        // Create source file.
        IntStream.range(0, 10).forEach(i -> sourceStorage.put(i, new BigDecimal(i + 10)));

        Assert.assertEquals(Optional.empty(), numbers.get(1));
        Assert.assertEquals(Optional.of(false), numbers.calculate(1, 1, BigDecimal.TEN));
        Assert.assertEquals(Optional.of(new BigDecimal(11)), numbers.get(1));

        Assert.assertEquals(Optional.empty(), numbers.get(3));
        Assert.assertEquals(Optional.of(false), numbers.calculate(3, 3, BigDecimal.TEN));
        Assert.assertEquals(Optional.of(new BigDecimal(13)), numbers.get(3));
    }
}
