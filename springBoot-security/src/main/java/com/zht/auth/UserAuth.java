package com.zht.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 *
 * </p>
 *
 * @author ZHT
 * @since 2020/6/14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth implements UserDetails, Serializable {


    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户主键
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户身份: 0管理员; 1普通会员
     */
    private Integer auth;

    /**
     * 菜单权限
     */
    //private List<Menu> menus;

    /**
     * 区划id
     */
    private Integer regId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginName;
    }

    /**
     * 帐户是否未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 帐户是否未被冻结
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 帐户密码是否未过期，一般有的密码要求性高的系统会使用到，比如每隔一段时间就要求用户重置密码
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
