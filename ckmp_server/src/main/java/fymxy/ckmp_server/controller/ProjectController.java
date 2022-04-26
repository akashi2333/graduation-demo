package fymxy.ckmp_server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import fymxy.ckmp_server.common.Respone;
import fymxy.ckmp_server.entity.Project;
import fymxy.ckmp_server.entity.Team;
import fymxy.ckmp_server.entity.UserProject;
import fymxy.ckmp_server.service.ProjectService;
import fymxy.ckmp_server.service.UserProjectService;
import fymxy.ckmp_server.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
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
@RequestMapping("/project")
@Api(tags = "project 的增改查")
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    UserProjectService userProjectService;
    @Autowired
    UserService userService;

    @ApiOperation(value = "新建project操作")
    @ApiOperationSupport(ignoreParameters = {"project.pid","project.timestamp"})
    @ApiResponses({
            @ApiResponse(code = 200,message = "创建成功")
    })
    @PostMapping("/add")
    private Respone add(@RequestBody MultipartFile file, Project project){

        setProjectImg(file,project);

        project.setTimestamp(new Date());
        projectService.save(project);
        //插入到群-成员关系表
        userProjectService.save(new UserProject(project.getUid(),project.getPid()));
        return new Respone(200,"创建成功",project);
    }

    @ApiOperation(value = "修改project操作,前端需做核实拥有者身份")
    @ApiResponses({
            @ApiResponse(code = 200,message = "创建成功")
    })
    @ApiOperationSupport(ignoreParameters = {"uid","timestamp"})
    @PutMapping("/update")
    private Respone update(@RequestBody MultipartFile file, Project project){
        Project oldProject = projectService.getOne(new QueryWrapper<Project>()
                .eq("pid", project.getPid()));

        project.setTimestamp(oldProject.getTimestamp());
        project.setUid(oldProject.getUid());
        if (file!=null){
            setProjectImg(file,project);
        }
        else {
            project.setImg(oldProject.getImg());
        }
        UpdateWrapper<Project> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("pid",project.getPid());
        if (projectService.update(project,updateWrapper)){
            return new Respone(200,"修改成功",null);
        }else {
            return new Respone(400,"修改失败",null);
        }
    }

    @ApiOperation(value = "根据pid查询project操作")
    @ApiResponses({
            @ApiResponse(code = 200,message = "创建成功")
    })
    @ApiOperationSupport(ignoreParameters = {"name","uid","state","timestamp","img"})
    @GetMapping("/getSingleProjectDetail")
    private Respone getSingleProjectDetail(Project project){
        Object[] res = new Object[2];
        Project project1 = projectService.getById(project.getPid());
        res[0] = project1;
        res[1] = userService.getById(project1.getUid());

        return new Respone(200,"查询成功",res);
    }

    private void setProjectImg(MultipartFile file, Project project){
        InputStream ins;
        try {
            ins = file.getInputStream();
            byte[] buffer=new byte[1024];
            int len=0;
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            while((len=ins.read(buffer))!=-1){
                bos.write(buffer,0,len);
            }
            bos.flush();
            project.setImg( bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
