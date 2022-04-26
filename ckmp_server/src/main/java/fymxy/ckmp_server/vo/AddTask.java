package fymxy.ckmp_server.vo;

import fymxy.ckmp_server.entity.ProjectTask;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tangWu
 */
@Data
@AllArgsConstructor
public class AddTask{
    private ProjectTask projectTask;
    private int[] members;
}