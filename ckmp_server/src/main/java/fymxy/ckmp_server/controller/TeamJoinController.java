package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.TeamJoin;
import fymxy.ckmp_server.service.TeamJoinService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fymxy
 * @since 2022-04-06
 */
@RestController
@RequestMapping("/team-join")
public class TeamJoinController {

    @Autowired
    TeamJoinService teamJoinService;

    @ApiOperation(value = "申请加  团队  操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "申请成功")
    })
    @PostMapping("/add")
    private Respone add(@RequestBody TeamJoin teamJoin){
        teamJoinService.save(teamJoin);
        return new Respone(200,"申请成功",null);
    }

    @ApiOperation(value = "删除对应申请加 团队 操作的记录（即处理了此请求）")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @DeleteMapping("/delete")
    private Respone delete(@RequestBody TeamJoin teamJoin){
        teamJoinService.remove(new QueryWrapper<TeamJoin>().eq("uid",teamJoin.getUid()).eq("tid",teamJoin.getTid()));
        return new Respone(200,"处理成功",null);
    }

    @ApiOperation(value = "根据tid查询想进  团队  人员id，返回int的list")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"teamJoin.uid"})
    @GetMapping("/getByTeamId")
    private Respone getByTeamId(@RequestBody TeamJoin teamJoin){
        ArrayList<Integer> res = new ArrayList<>();
        for (TeamJoin exit : teamJoinService.list(new QueryWrapper<TeamJoin>()
                .eq("tid", teamJoin.getTid()))) {
            res.add(exit.getUid());
        }
        return new Respone(200,"处理成功",res);
    }

    @ApiOperation(value = "根据uid查询想进的 团队 ，返回int的list")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"teamJoin.tid"})
    @GetMapping("/getByUserId")
    private Respone getByUserId(@RequestBody TeamJoin teamJoin){
        ArrayList<Integer> res = new ArrayList<>();
        for (TeamJoin exit : teamJoinService.list(new QueryWrapper<TeamJoin>()
                .eq("uid", teamJoin.getUid()))) {
            res.add(exit.getUid());
        }
        return new Respone(200,"处理成功",res);
    }
}
