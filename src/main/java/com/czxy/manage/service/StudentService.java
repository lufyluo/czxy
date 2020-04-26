package com.czxy.manage.service;

import com.czxy.manage.model.vo.classes.ClassOrgInfo;
import com.czxy.manage.model.vo.student.StudentPageParam;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    public PageInfo<ClassOrgInfo> page(StudentPageParam<String> pageParam) {
        return null;
    }
}
