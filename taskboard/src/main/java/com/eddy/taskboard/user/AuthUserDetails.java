package com.eddy.taskboard.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * bridges our domain User to Spring Security's UserDetails
 * for now, we don't expose roles/authorities; we'll add RBAC later
 */
public class AuthUserDetails implements UserDetails {

    private final UUID id;
    private final String email;
    private final String passwordHash;

    public AuthUserDetails(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.passwordHash = user.getPasswordHash();
    }

    // expose database id for controllers/services that need it later
    public UUID getuserId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // no roles yet; return empty. OWNER/ADMIN/MEMBER will be added later
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        // spring Security's "username" is our email
        return email;
    }

    // simple defaults — all accounts are enabled and non-expired for now
    @Override public boolean isAccountNonExpired() {return true;}
    @Override public boolean isAccountNonLocked() {return true;}
    @Override public boolean isCredentialsNonExpired() {return true;}
    @Override public boolean isEnabled() {return true;}
}
