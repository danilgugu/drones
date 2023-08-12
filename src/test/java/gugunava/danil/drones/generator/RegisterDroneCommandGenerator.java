package gugunava.danil.drones.generator;

import gugunava.danil.drones.command.RegisterDroneCommand;

public final class RegisterDroneCommandGenerator {

    public static RegisterDroneCommand valid() {
        return new RegisterDroneCommand("1000", "Heavyweight", 450);
    }

    public static RegisterDroneCommand invalid() {
        return new RegisterDroneCommand("1".repeat(101), "Heavyweight", 501);
    }
}
