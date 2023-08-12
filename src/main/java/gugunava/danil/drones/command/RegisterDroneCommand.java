package gugunava.danil.drones.command;

import lombok.Value;

import javax.validation.constraints.*;

@Value
public class RegisterDroneCommand {

    @NotBlank(message = "Please specify drone serial number.")
    @Size(max = 100, message = "Serial number is too long.")
    String serialNumber;

    String model;

    @NotNull(message = "Please specify drone weight limit.")
    @Max(value = 500, message = "Weight limit cannot be more than 500 gr")
    @Positive(message = "Weight limit should be positive number.")
    Integer weightLimit;
}
