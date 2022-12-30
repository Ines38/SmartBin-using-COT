package tn.smartbin.cot.models;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import tn.smartbin.cot.enumer.UserRoles;

@Entity
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

    public User(){
    }
    public User(Long userId, String username,String email,String password ,String phone ,UserRoles role) {
        this.Id=userId;
        this.username=username;
        this.role=role;
        this.email=email;
        this.password=password;
        this.phone=phone;
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
