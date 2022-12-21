package com.wildtac.domain.user;


import com.wildtac.domain.BaseEntity;
import com.wildtac.domain.user.security.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

import static com.wildtac.domain.Status.ENABLED;

@Getter
@Setter
@Entity
@Table(name = "users")
@ToString
public class User extends BaseEntity implements UserDetails {
    private String email;
    private String phoneNumber;
    private String name;
    private String surname;

    @ToString.Exclude
    private String address;

    @ToString.Exclude
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Lob
    private String refreshToken;

    public User() {
        super();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getGrantedAuthority();
    }

    /**
     * The method uses for finding users
     * @return return phoneNumber if email is null, in other case it returns email
     */
    @Override
    public String getUsername() {
        if (email == null) {
            return phoneNumber;
        }
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
}
