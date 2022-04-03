package fymxy.ckmp_server.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
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
@ApiModel(description = "退群申请")
public class ProjectExit implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "群id",required = true)
    private Integer pid;

    @ApiModelProperty(value = "用户id",required = true)
    private Integer uid;


}
