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
import java.util.Objects;
import java.util.Set;

import static com.wildtac.domain.Status.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    private String email;
    private String phoneNumber;
    private String name;
    private String surname;
    private String address;
    private String password;

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

    /**
     *
     * @return email of user
     */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return getStatus().equals(ENABLED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return getStatus().equals(ENABLED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return getStatus().equals(ENABLED);
    }

    @Override
    public boolean isEnabled() {
        return getStatus().equals(ENABLED);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                "email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, phoneNumber);
    }
}
