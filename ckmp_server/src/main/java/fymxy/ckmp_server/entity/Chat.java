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
 * @since 2022-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description = "聊天记录")
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发送者",required = true)
    private Integer sid;

    @ApiModelProperty(value = "接收者",required = true)
    private Integer rid;

    @ApiModelProperty(value = "消息内容",required = true)
    private String message;

    @ApiModelProperty(value = "时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date sendDate;

    // TODO: 2022/4/6 现在可能有三种消息，项目，个人，团队
    @ApiModelProperty(value = "是否为群消息 0为个人此时rid为用户id，1为群发，此时rid为群id",required = true)
    private Boolean sendAll;


}
