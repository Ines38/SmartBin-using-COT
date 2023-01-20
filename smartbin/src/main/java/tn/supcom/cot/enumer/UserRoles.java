package tn.supcom.cot.enumer;

import java.util.function.Supplier;

public enum UserRoles implements Supplier<String> {
    Client,
    Administrateur;
    @Override
    public String get() {
        return this.name();
    }
}
