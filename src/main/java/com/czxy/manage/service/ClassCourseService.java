package com.czxy.manage.service;

import com.czxy.manage.dao.ArrangeMapper;
import com.czxy.manage.dao.CourseArrangeMapper;
import com.czxy.manage.dao.CourseMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.ArrangeEntity;
import com.czxy.manage.model.entity.ClassArrangeWithTimeEntity;
import com.czxy.manage.model.entity.CourseArrangeEntity;
import com.czxy.manage.model.entity.CourseDetailEntity;
import com.czxy.manage.model.vo.classes.ClassArrangeInfo;
import com.czxy.manage.model.vo.classes.CourseArrangeAddInfo;
import com.czxy.manage.model.vo.classes.SubjectDetailInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

@Service
public class ClassCourseService {

    @Resource
    ArrangeMapper arrangeMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private CourseArrangeMapper courseArrangeMapper;

    public ClassArrangeInfo get(Integer classId) {
        ClassArrangeWithTimeEntity classArrangeWithTimeEntity = arrangeMapper.get(classId);
        if (classArrangeWithTimeEntity == null) {
            throw new ManageException(ResponseStatus.DATANOTEXIST);
        }
        ClassArrangeInfo classArrangeInfo = PojoMapper.INSTANCE.toClassArrangeInfo(classArrangeWithTimeEntity);
        List<CourseDetailEntity> courseEntities = courseMapper.get(classArrangeWithTimeEntity.getId());
        List<SubjectDetailInfo> courseInfos = PojoMapper.INSTANCE.toCourseInfos(courseEntities);
        Calendar calendar = Calendar.getInstance();
        Long dayMiliSeconds = 86400000L;
        calendar.setTime(classArrangeWithTimeEntity.getBeginTime());
        Long beginTime = calendar.getTimeInMillis();
        Calendar temp = Calendar.getInstance();

        courseInfos.forEach(n -> {
            Long current = beginTime + dayMiliSeconds * n.getOffset();
            temp.setTimeInMillis(current);
            temp.add(Calendar.HOUR_OF_DAY, n.getBeginTime().getHours());
            n.setBeginTime(temp.getTime());
            temp.add(Calendar.HOUR_OF_DAY, n.getEndTime().getHours());
            n.setEndTime(temp.getTime());
        });
        classArrangeInfo.setCourseInfos(courseInfos);
        return classArrangeInfo;
    }

    @Transactional
    public Boolean add(CourseArrangeAddInfo classCourseInfo) {
        ArrangeEntity arrangeEntity = PojoMapper.INSTANCE.toArrangeEntity(classCourseInfo);
        arrangeMapper.insert(arrangeEntity);
        if(classCourseInfo.getCourseInfos() ==null ||classCourseInfo.getCourseInfos().size() ==0){
            return true;
        }
        List<CourseArrangeEntity> courseArrangeEntities =
                PojoMapper.INSTANCE.toCourseArrangeEntities(classCourseInfo.getCourseInfos());
        courseArrangeEntities.forEach(n->n.setArrangeId(arrangeEntity.getId()));
        courseArrangeMapper.batchInsert(courseArrangeEntities);
        return true;
    }
}
