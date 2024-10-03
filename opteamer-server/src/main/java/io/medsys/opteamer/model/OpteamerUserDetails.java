package io.medsys.opteamer.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OpteamerUserDetails implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    @Autowired
    public OpteamerUserDetails(Long id, String password, String email, String username,
                               Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.username = username;
        this.authorities = authorities;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
    }

    public static OpteamerUserDetails build(User user) {
        List<GrantedAuthority> authorities = getAuthorities(user);
        return new OpteamerUserDetails(
                user.getId(), user.getPassword(),
                user.getEmail(), user.getUsername(), authorities);
    }

    public static List<GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .flatMap(role -> role.getPrivileges().stream().map(
                        privilege -> new SimpleGrantedAuthority(String.valueOf(privilege.getName()))))
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return  true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OpteamerUserDetails user = (OpteamerUserDetails) o;
        return Objects.equals(id, user.id);
    }
}
