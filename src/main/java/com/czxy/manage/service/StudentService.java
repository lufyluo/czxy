package com.czxy.manage.service;

import com.czxy.manage.dao.StudentMapper;
import com.czxy.manage.model.entity.StudentDetailEntity;
import com.czxy.manage.model.vo.student.StudentDetailInfo;
import com.czxy.manage.model.vo.student.StudentPageParam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentService {
    @Resource
    private StudentMapper studentMapper;

    public PageInfo<StudentDetailInfo> page(StudentPageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<StudentDetailEntity> studentDetailEntities =  studentMapper.query(pageParam);
        return null;
    }
}
