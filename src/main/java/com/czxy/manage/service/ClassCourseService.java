package com.czxy.manage.service;

import com.czxy.manage.dao.ArrangeMapper;
import com.czxy.manage.dao.CourseMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.ClassArrangeWithTimeEntity;
import com.czxy.manage.model.entity.CourseDetailEntity;
import com.czxy.manage.model.vo.classes.ClassArrangeInfo;
import com.czxy.manage.model.vo.classes.CourseArrangeAddInfo;
import com.czxy.manage.model.vo.classes.SubjectDetailInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

@Service
public class ClassCourseService {

    @Resource
    ArrangeMapper arrangeMapper;
    @Resource
    private CourseMapper courseMapper;

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

    public Boolean add(CourseArrangeAddInfo classCourseInfo) {
        return null;
    }
}
