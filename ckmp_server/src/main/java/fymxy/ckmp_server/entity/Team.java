package fymxy.ckmp_server.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author fymxy
 * @since 2022-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description = "团队表")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "团队标识",required = true)
    @TableId(type = IdType.AUTO)
    private Integer tid;

    @ApiModelProperty(value = "用户id",required = true)
    private Integer uid;

    @ApiModelProperty(value = "此用户是否为创建者",required = true)
    private Integer isowner;

    @ApiModelProperty(value = "团队名字",required = true)
    private String name;

    @ApiModelProperty(value = "团队描述",required = true)
    private String brief;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "时间")
    private Date timestamp;

}
