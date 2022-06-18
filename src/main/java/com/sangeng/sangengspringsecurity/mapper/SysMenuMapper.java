package com.sangeng.sangengspringsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.sangengspringsecurity.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {

    /**
     * 根据userid查询权限
     * @param userId
     * @return
     */
    List<String>getPermsByUserId(Long userId);
}
