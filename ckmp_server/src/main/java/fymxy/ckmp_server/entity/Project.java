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
@ApiModel(description = "项目记录")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "项目标识",required = true)
    private Integer pid;

    @ApiModelProperty(value = "创建者id",required = true)
    private Integer uid;

    @ApiModelProperty(value = "项目名字",required = true)
    private String name;

    @ApiModelProperty(value = "项目状态",required = true)
    private String state;


}
