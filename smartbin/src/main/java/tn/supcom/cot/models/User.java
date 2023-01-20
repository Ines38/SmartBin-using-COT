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
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private String forename;
    @Column
    private String surname;
    @Column
    private String phone;
    @Column
    private Set<UserRoles> role;

    public User() {
    }

    public User(Long userId, String username,String email,String password,String forename,String surname ,String phone ,Set<UserRoles> role) {
        this.Id=userId;
        this.username=username;
        this.forename=forename;
        this.surname=surname;
        this.role=role;
        this.email=email;
        this.password=password;
        this.phone=phone;
    }

    public void add(Set<UserRoles> roles) {
        if (this.role == null) {
            this.role = new HashSet<>();
        }
        this.role.addAll(roles);
    }

    public void remove(Set<UserRoles> roles) {
        if (this.role == null) {
            this.role = new HashSet<>();
        }
        this.role.removeAll(roles);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTelephone(String telephone) {
        this.phone = telephone;
    }

    public void setRoles(Set<UserRoles> roles) {
        this.role = roles;
    }
    public String getName() {
        return username;
    }

    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
    public String getTelephone() {
        return phone;
    }
    public String getPassword() {
        return password;
    }

    public Set<UserRoles> getRoles() {
        return role;
    }

    public void updatePassword(String password, Pbkdf2PasswordHash passwordHash) {
        this.password = passwordHash.generate(password.toCharArray());
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

    public static UserBuilder builder() {
        return new UserBuilder();
    }
    public static class UserBuilder {
        private String username;

        private String email;


        private String password;
        private String telephone;
        private Set<UserRoles> role;



        public UserBuilder withUserName(String username) {
            this.username = username;
            return this;
        }


        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withTelephone(String telephone) {
            this.telephone= telephone;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withRoles(Set<UserRoles> roles) {
            this.role = roles;
            return this;
        }


        public User build() {
            User user = new User();
            user.role = role;
            user.username = username;
            user.email = email;
            user.phone = telephone;
            return user;
        }
    }
}
