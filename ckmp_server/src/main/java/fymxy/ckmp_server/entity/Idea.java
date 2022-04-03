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
 * @since 2022-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description = "想法记录")
public class Idea implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "拥有者id",required = true)
    private Integer id;

    @ApiModelProperty(value = "idea内容",required = true)
    private String idea;


}
