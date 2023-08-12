package gugunava.danil.drones.generator;

import gugunava.danil.drones.command.LoadMedicationCommand;

public final class LoadMedicationCommandGenerator {

    public static LoadMedicationCommand valid() {
        return new LoadMedicationCommand("medication_1", 30, "HC_500");
    }

    public static LoadMedicationCommand overweight() {
        return new LoadMedicationCommand("medication_1", 3000, "HC_500");
    }

    public static LoadMedicationCommand invalid() {
        return new LoadMedicationCommand("invalid medication name", null, "invalid medication code");
    }
}
