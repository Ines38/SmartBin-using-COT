package tn.supcom.cot.models;

import tn.supcom.cot.enumer.UserRoles;

import java.util.Set;

public class RoleDTO {
    private Set<UserRoles> roles;

    public Set<UserRoles> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoles> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roles=" + roles +
                '}';
    }
}
