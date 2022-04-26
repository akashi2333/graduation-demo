package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.TeamExit;
import fymxy.ckmp_server.entity.TeamJoin;
import fymxy.ckmp_server.entity.User;
import fymxy.ckmp_server.service.TeamJoinService;
import fymxy.ckmp_server.service.UserService;
import io.swagger.annotations.Api;
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
@Api(tags = "team申请 进进进 的增删查")
public class TeamJoinController {

    @Autowired
    TeamJoinService teamJoinService;
    @Autowired
    UserService userService;

    @ApiOperation(value = "申请加  团队  操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "申请成功")
    })
    @PostMapping("/add")
    private Respone add(@RequestBody TeamJoin teamJoin){
        if (teamJoinService.list(new QueryWrapper<TeamJoin>()
                .eq("uid",teamJoin.getUid())
                .eq("tid",teamJoin.getTid()))!=null){
            return new Respone(200,"已申请",null);
        }
        teamJoinService.save(teamJoin);
        return new Respone(200,"申请成功",null);
    }

    @ApiOperation(value = "删除对应申请加 团队 操作的记录（即处理了此请求）")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @DeleteMapping("/delete")
    private Respone delete( TeamJoin teamJoin){
        teamJoinService.remove(new QueryWrapper<TeamJoin>().eq("uid",teamJoin.getUid()).eq("tid",teamJoin.getTid()));
        return new Respone(200,"处理成功",null);
    }

    @ApiOperation(value = "根据tid查询想进  团队  人员id，返回user的list")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"uid"})
    @GetMapping("/getByTeamId")
    private Respone getByTeamId(TeamJoin teamJoin){
        ArrayList<User> res = new ArrayList<>();
        for (TeamJoin exit : teamJoinService.list(new QueryWrapper<TeamJoin>()
                .eq("tid", teamJoin.getTid()))) {
            res.add(userService.getById(exit.getUid()));
        }
        return new Respone(200,"处理成功",res);
    }

    @ApiOperation(value = "根据uid查询想进的 团队 ，返回int的list")
    @ApiResponses({
            @ApiResponse(code = 200,message = "处理成功")
    })
    @ApiOperationSupport(ignoreParameters = {"tid"})
    @GetMapping("/getByUserId")
    private Respone getByUserId( TeamJoin teamJoin){
        ArrayList<Integer> res = new ArrayList<>();
        for (TeamJoin exit : teamJoinService.list(new QueryWrapper<TeamJoin>()
                .eq("uid", teamJoin.getUid()))) {
            res.add(exit.getTid());
        }
        return new Respone(200,"处理成功",res);
    }
}
