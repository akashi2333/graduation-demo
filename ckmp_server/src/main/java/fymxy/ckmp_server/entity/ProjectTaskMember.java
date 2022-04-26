package fymxy.ckmp_server.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
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
 * @since 2022-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "任务与成员对应表")
public class ProjectTaskMember implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableField("tid")
    private Integer taskId;


    private Integer uid;


}
