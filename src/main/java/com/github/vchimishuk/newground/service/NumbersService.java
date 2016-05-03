package com.github.vchimishuk.newground.service;

import java.math.BigDecimal;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

@Service
public class NumbersService {
    private final StorageService sourceStorage;

    private final StorageService calculationsStorage;

    @Inject
    public NumbersService(@Named("sourceStorage") StorageService sourceStorage,
            @Named("calculationsStorage") StorageService calculationsStorage)
    {
        this.sourceStorage = sourceStorage;
        this.calculationsStorage = calculationsStorage;
    }

    public Optional<Boolean> calculate(int srcIndex, int descIndex, BigDecimal num) {
        return sourceStorage.get(srcIndex).map(n -> {
            boolean cond;

            if (n.add(n).compareTo(BigDecimal.TEN) < 0) {
                n = n.add(num).add(BigDecimal.TEN);
                cond = true;
            } else {
                n = n.add(num);
                cond = false;
            }
            calculationsStorage.put(descIndex, n);

            return cond;
        });
    }

    public Optional<BigDecimal> get(int index) {
        return calculationsStorage.get(index)
                .map(n -> n.compareTo(BigDecimal.TEN) > 0 ? n.subtract(BigDecimal.TEN) : n);
    }
}
