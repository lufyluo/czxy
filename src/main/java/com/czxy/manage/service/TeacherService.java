package com.czxy.manage.service;

import com.czxy.manage.dao.TeacherMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.StudentDetailEntity;
import com.czxy.manage.model.entity.TeacherDetailEntity;
import com.czxy.manage.model.vo.student.StudentDetailInfo;
import com.czxy.manage.model.vo.teacher.TeacherDetailInfo;
import com.czxy.manage.model.vo.teacher.TeacherPageParam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherService {
    @Resource
    private TeacherMapper teacherMapper;

    public PageInfo<TeacherDetailInfo> page(TeacherPageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<TeacherDetailEntity> teacherDetailEntities = teacherMapper.query(pageParam);
        PageInfo<TeacherDetailInfo> result = page.toPageInfo();
        result.setList(PojoMapper.INSTANCE.toTeacherDetailInfos(teacherDetailEntities));
        return result;
    }
}
