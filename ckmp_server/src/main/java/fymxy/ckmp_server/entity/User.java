package fymxy.ckmp_server.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "用户id",required = true)
    private Integer id;

    @ApiModelProperty(value = "名字（唯一）",required = true)
    private String name;

    @ApiModelProperty(value = "登录密码",required = true)
    private String password;

    @ApiModelProperty(value = "邮箱",required = true)
    private String eMail;


}
