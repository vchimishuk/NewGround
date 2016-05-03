package com.github.vchimishuk.newground.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.inject.Inject;

import com.github.vchimishuk.newground.model.Range;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class StorageService {
    private final ParserService parserService;

    private final File file;

    private final ReadWriteLock lock;

    @Inject
    public StorageService(ParserService parserService, File file) {
        this.parserService = parserService;
        this.file = file;
        this.lock = new ReentrantReadWriteLock();
    }

    public Optional<BigDecimal> get(int index) {
        Lock l = this.lock.readLock();
        l.lock();

        try {
            try (InputStream in = new FileInputStream(file)) {
                return parserService.parse(IOUtils.toString(in), index);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } finally {
            l.unlock();
        }
    }

    public void put(int index, BigDecimal n) {
        Lock l = this.lock.writeLock();
        l.lock();

        try {
            String contents;

            try (InputStream in = new FileInputStream(file)) {
                contents = IOUtils.toString(in);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Optional<Range> rangeO = parserService.find(contents, index);

            try(OutputStream out = new FileOutputStream(file)) {
                if (rangeO.isPresent()) {
                    // Update existing number.
                    Range range = rangeO.get();

                    IOUtils.write(contents.substring(0, range.getStart()), out);
                    IOUtils.write(n.toString(), out);
                    IOUtils.write(contents.substring(range.getEnd()), out);
                } else {
                    // Add new one to the end.
                    int size = parserService.size(contents);
                    int commas = size > 0 ? index - size + 1 : index - size;

                    IOUtils.write(contents, out);
                    IOUtils.write(StringUtils.repeat(',', commas), out);
                    IOUtils.write(n.toString(), out);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } finally {
            l.unlock();
        }
    }
}
