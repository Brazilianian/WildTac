package com.wildtac.domain.user;


import com.wildtac.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String email;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String password;

    public User() {
        super();
    }

    public User(String email, String name, String surname, String address, String phoneNumber, String password) {
        super();

        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
