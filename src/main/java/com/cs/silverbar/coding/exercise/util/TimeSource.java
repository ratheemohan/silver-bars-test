package com.cs.silverbar.coding.exercise.util;

import java.time.Instant;

@FunctionalInterface
public interface TimeSource {
    Instant now();
}
