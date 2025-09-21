package me.baero.core.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;

public record CloudEvent<T>(
        @JsonProperty("specversion")
        String specVersion,
        String id,
        String type,
        String source,
        long time,
        T data
) {
    @Builder
    public CloudEvent(String type, String source, long time, T data) {
        this("1.0", UUID.randomUUID().toString(), type, source, time, data);
    }
}
