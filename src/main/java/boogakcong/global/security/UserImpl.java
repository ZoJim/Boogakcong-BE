package boogakcong.global.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserImpl implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities; // 권한 목록
    private String profile; // 사용자 프로필 정보 (추가 필드)

    public UserImpl(String username, String password, List<GrantedAuthority> authorities, String profile) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.profile = profile;
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getProfile() {
        return profile;
    }
}
