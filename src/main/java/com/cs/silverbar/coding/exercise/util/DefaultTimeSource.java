package com.cs.silverbar.coding.exercise.util;

import java.time.Instant;

public class DefaultTimeSource implements TimeSource {
    @Override
    public Instant now() {
        return Instant.now();
    }
}
