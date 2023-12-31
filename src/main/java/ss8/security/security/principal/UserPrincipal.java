package ss8.security.security.principal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ss8.security.model.entity.Users;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPrincipal implements UserDetails {
    private Long id;
    private String name;
    private String username;
    private String password;
    private boolean status;
    // user principal không lưu trữ dưới dạng role mà lưu dưới dạng GrantedAuthority
    // private List<Role> roles;
    // bảng Role cột role phải đặt tên là ROLE_name để nhận dạng các quyền khi đọc
    // và tự động xóa tiền tố ROLE_
    private Collection<? extends GrantedAuthority> authorities;
    // chuyển đổi user sang principal
    public static  UserPrincipal build(Users user){
        List<GrantedAuthority> authorityList = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
        return UserPrincipal.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .password(user.getPassword())
                .status(user.isStatus())
                .authorities(authorityList)
                .build();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
}
