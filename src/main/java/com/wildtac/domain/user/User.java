package com.wildtac.domain.user;


import com.wildtac.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    private String email;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String password;

    private boolean isEnabled;

    @ElementCollection
    private Set<Role> roles = new HashSet<>();

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
