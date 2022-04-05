package fymxy.ckmp_server.entity;

import java.io.Serializable;
import java.util.Date;

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
@ApiModel(description = "团队资源内容")
public class TeamResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "团队id",required = true)
    private Integer tid;

    @ApiModelProperty(value = "上传者id",required = true)
    private Integer uid;

    @ApiModelProperty(value = "资源名",required = true)
    private String resourceName;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "时间")
    private Date timestamp;


}
