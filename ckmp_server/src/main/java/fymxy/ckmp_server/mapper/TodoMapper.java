package fymxy.ckmp_server.mapper;

import fymxy.ckmp_server.entity.Todo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fymxy
 * @since 2022-03-26
 */
@Mapper
public interface TodoMapper extends BaseMapper<Todo> {

}
