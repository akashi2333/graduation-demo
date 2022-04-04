package fymxy.ckmp_server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 
 * </p>
 *
 * @author fymxy
 * @since 2022-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description = "用户记录")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "用户id",required = true)
    private Integer id;

    @ApiModelProperty(value = "名字（唯一）",required = true)
    @TableField("name")
    private String username;

    @ApiModelProperty(value = "登录密码",required = true)
    private String password;

    @ApiModelProperty(value = "邮箱",required = true)
    private String eMail;

    private String role;

    @TableField(exist = false)
    private String uuid;

    @TableField(exist = false)
    private String verifyCode;


    // 1:启用 ， 0：禁用
    @TableField(exist = false)
    @Getter(AccessLevel.NONE)
    private Integer enabled = 1;

    // 1：锁住 ， 0：未锁
    @TableField(exist = false)
    @Getter(AccessLevel.NONE)
    private Integer locked = 0;


    @TableField(exist = false)
    @Getter(AccessLevel.NONE)
    private List<String> listAuthorities;

    @TableField(exist = false)
    private String token;

    @TableField(exist = false)
    private String e_code;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled == 1;
    }
}
