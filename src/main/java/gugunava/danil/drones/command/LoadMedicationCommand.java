package gugunava.danil.drones.command;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Value
public class LoadMedicationCommand {

	@NotBlank(message = "Please specify medication name.")
	@Pattern(regexp = "[\\w-]*", message = "Invalid medication name.")
	String name;

	@NotNull(message = "Please specify medication weight.")
	@Positive(message = "Medication weight should be positive number.")
	Integer weight;

	@Pattern(regexp = "[A-Z0-9_]*", message = "Invalid medication code.")
	String code;
}
