package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.Project;
import fymxy.ckmp_server.entity.User;
import fymxy.ckmp_server.entity.UserProject;
import fymxy.ckmp_server.service.ProjectService;
import fymxy.ckmp_server.service.UserProjectService;
import fymxy.ckmp_server.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fymxy
 * @since 2022-03-26
 */
@RestController
@RequestMapping("/user-project")
@Api(tags = "project和用户对应关系的增删查")
public class UserProjectController {
    @Autowired
    UserProjectService userProjectService;
    @Autowired
    UserService userService;


    @ApiOperation(value = "插入project和用户对应关系")
    @ApiResponses({
            @ApiResponse(code = 200,message = "加入成功")
    })
    @PostMapping("/add")
    private Respone add(@RequestBody UserProject userProject){
        userProjectService.save(userProject);
        return new Respone(200,"加入成功",null);
    }

    @ApiOperation(value = "删除project和用户对应关系（即退群）")
    @ApiResponses({
            @ApiResponse(code = 200,message = "删除成功")
    })
    @DeleteMapping("/delete")
    private Respone delete(@RequestBody UserProject userProject){
        userProjectService.remove(new QueryWrapper<UserProject>().eq("id",userProject.getUid()).eq("UserProject",userProject.getPid()));
        return new Respone(200,"删除成功",null);
    }

    @ApiOperation(value = "获得群成员")
    @ApiOperationSupport(ignoreParameters = {"userProject.uid"})
    @ApiResponses({
            @ApiResponse(code = 200,message = "查找成功 返回群成员")
    })
    @GetMapping("getMembers")
    public Respone getMembers(@RequestBody UserProject userProject){
        //群成员
        List<User> members = new ArrayList<>();
        for (UserProject up : userProjectService.list(new QueryWrapper<UserProject>().eq("pid", userProject.getPid()))) {
            members.add(userService.getById(up.getUid()));
        }
        return new Respone(200,"查找成功",members);
    }




}
