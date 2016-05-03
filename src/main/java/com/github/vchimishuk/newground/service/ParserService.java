package com.github.vchimishuk.newground.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.github.vchimishuk.newground.model.Range;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ParserService {
    public Optional<BigDecimal> parse(String contents, int index) {
        return find(contents, index)
                .map(r -> contents.substring(r.getStart(), r.getEnd()))
                .filter(StringUtils::isNotEmpty)
                .map(BigDecimal::new);
    }

    public Optional<Range> find(String contents, int index) {
        for (int i = 0, n = 0; i < contents.length(); i++) {
            if (n == index) {
                int end;

                if (contents.charAt(i) == ',') {
                    end = i;
                } else {
                    end = contents.indexOf(',', i + 1);
                    if (end == -1) {
                        end = contents.length();
                    }
                }

                return Optional.of(new Range(i, end));
            }
            if (contents.charAt(i) == ',') {
                n++;
            }
        }

        return Optional.empty();
    }

    public int size(String contents) {
        int n = 0;
        int len = contents.length();

        for (int i = 0; i < len; i++) {
            if (contents.charAt(i) == ',') {
                n++;
            }
        }
        // And the last one.
        if (len > 0) {
            n++;
        }

        return n;
    }
}
