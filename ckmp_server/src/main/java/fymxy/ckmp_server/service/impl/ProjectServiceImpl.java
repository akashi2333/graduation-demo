package fymxy.ckmp_server.service.impl;

import fymxy.ckmp_server.entity.Project;
import fymxy.ckmp_server.mapper.ProjectMapper;
import fymxy.ckmp_server.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fymxy
 * @since 2022-03-26
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

}
