package com.example.janackoverflow.global.security.auth;

import com.example.janackoverflow.user.entity.Users;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
@Getter
@Setter
public class NowUserDetails implements UserDetails{

    private Users user;

    public NowUserDetails(Users user){
        this.user = user;
    }

    public Users getUser() {
        return user;
    }

    public long getId(){
        return user.getId();
    }

    @Override
    public String getPassword() { //인증 정보에 내장된 패스워드
        return user.getPassword();
    }
    @Override
    public String getUsername() { //인증 정보에 내장된 아이디
        return user.getEmail();
    }
    @Override
    public boolean isAccountNonExpired() { //계정 만료 여부
        return true;
    }
    @Override
    public boolean isAccountNonLocked() { //계정이 잠겨있는지 여부
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() { //자격 증명 만료 여부
        return true;
    }
    @Override
    public boolean isEnabled() { //계정 활성화 여부
        return true;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        this.getRoleList().forEach(role -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
            authorities.add(authority);
        });
        return authorities;
    }

    //권한 식별을 위한 리스트 생성
    public List<String> getRoleList(){
        if(!user.getRole().isEmpty()){
            return Arrays.asList(user.getRole().split(","));
        }
        return new ArrayList<>();
    }
}
