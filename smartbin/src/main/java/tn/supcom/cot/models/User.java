package tn.supcom.cot.models;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;
import tn.supcom.cot.FieldPropertyVisibilityStrategy;
import tn.supcom.cot.enumer.UserRoles;

import javax.json.bind.annotation.JsonbVisibility;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@JsonbVisibility(FieldPropertyVisibilityStrategy.class)
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;

    @Column
    private String phone;
    @Column
    private UserRoles role;

    public User() {
    }

    public User(Long userId, String username,String email,String password ,String phone ,UserRoles role) {
        this.Id=userId;
        this.username=username;
        this.role=role;
        this.email=email;
        this.password=password;
        this.phone=phone;
    }


    public String getName() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(password);
    }

    @Override
    public String toString() {
        return username + " ["
                + ", email=" + email
                + ", téléphone=" + phone
                + ", role=" + role
                + "]";
    }
}
