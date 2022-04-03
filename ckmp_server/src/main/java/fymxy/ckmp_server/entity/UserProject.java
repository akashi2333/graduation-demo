package fymxy.ckmp_server.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description = "project和用户对应关系")
public class UserProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "成员id",required = true)
    private Integer uid;

    @ApiModelProperty(value = "群id",required = true)
    private Integer pid;


}
