package fymxy.ckmp_server.entity;

import java.io.Serializable;

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
 * @since 2022-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description = "退团队申请")
public class TeamExit implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "团队id",required = true)
    private Integer tid;

    @ApiModelProperty(value = "用户id",required = true)
    private Integer uid;


}
