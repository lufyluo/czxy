package com.czxy.manage.service;

import com.czxy.manage.dao.ClassFeeMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.ClassFeeEntity;
import com.czxy.manage.model.vo.classes.ClassFeeDetailInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassFeeService {
    @Resource
    private ClassFeeMapper classFeeMapper;
    public List<ClassFeeDetailInfo> get(Integer classId) {
        List<ClassFeeEntity>classFeeEntities =  classFeeMapper.query(classId);
        if(classFeeEntities == null){
            throw new ManageException(ResponseStatus.DATANOTEXIST);
        }
        List<ClassFeeDetailInfo> result = PojoMapper.INSTANCE.toClassFeeDetailInfos(classFeeEntities);
        return result;
    }
}
