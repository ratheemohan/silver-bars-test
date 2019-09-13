package com.cs.silverbar.coding.exercise.util;

import java.time.Instant;

/**
 * Simple service to provide current time.
 */
@FunctionalInterface
public interface TimeSource {
    /**
     * @return current instant time in UTC.
     */
    Instant now();
}
