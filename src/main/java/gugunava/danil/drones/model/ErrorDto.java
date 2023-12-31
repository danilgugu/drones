package gugunava.danil.drones.model;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ErrorDto {

    private final String message;

    private final Instant timestamp;

    public ErrorDto(String message) {
        this.message = message;
        this.timestamp = Instant.now();
    }
}
