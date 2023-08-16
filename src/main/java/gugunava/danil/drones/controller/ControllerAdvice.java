package gugunava.danil.drones.controller;

import gugunava.danil.drones.exception.*;
import gugunava.danil.drones.model.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorDto handle(MethodArgumentNotValidException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorDto("Invalid request: " + e.getMessage());
    }

    @ExceptionHandler(DroneNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorDto handle(DroneNotFoundException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(DroneAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorDto handle(DroneAlreadyExistsException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(DroneBatteryIsLowException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorDto handle(DroneBatteryIsLowException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(WrongDroneStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorDto handle(WrongDroneStateException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(DroneReachedWeightLimitException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorDto handle(DroneReachedWeightLimitException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(CompressImageException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorDto handle(CompressImageException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(DecompressImageException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorDto handle(DecompressImageException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(ReadImageFileException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorDto handle(ReadImageFileException e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorDto(e.getMessage());
    }
}
