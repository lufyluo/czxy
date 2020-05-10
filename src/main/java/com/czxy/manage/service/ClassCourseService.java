package com.czxy.manage.service;

import com.czxy.manage.dao.ArrangeMapper;
import com.czxy.manage.dao.CourseArrangeMapper;
import com.czxy.manage.dao.SubjectMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.ArrangeEntity;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.ClassArrangeWithTimeEntity;
import com.czxy.manage.model.entity.CourseArrangeEntity;
import com.czxy.manage.model.entity.CourseDetailEntity;
import com.czxy.manage.model.vo.arrange.ArrangeInfo;
import com.czxy.manage.model.vo.classes.ClassArrangeInfo;
import com.czxy.manage.model.vo.classes.CourseArrangeAddInfo;
import com.czxy.manage.model.vo.classes.CourseArrangeUpdateInfo;
import com.czxy.manage.model.vo.classes.SubjectDetailInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ClassCourseService {

    @Resource
    ArrangeMapper arrangeMapper;
    @Resource
    private SubjectMapper subjectMapper;
    @Resource
    private CourseArrangeMapper courseArrangeMapper;

    public ClassArrangeInfo get(Integer classId) {
        ClassArrangeWithTimeEntity classArrangeWithTimeEntity = arrangeMapper.get(classId);
        if (classArrangeWithTimeEntity == null) {
            throw new ManageException(ResponseStatus.DATANOTEXIST);
        }
        ClassArrangeInfo classArrangeInfo = PojoMapper.INSTANCE.toClassArrangeInfo(classArrangeWithTimeEntity);
        List<CourseDetailEntity> courseEntities = subjectMapper.queryByArrangeId(classArrangeWithTimeEntity.getId());
        List<SubjectDetailInfo> courseInfos = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Long dayMiliSeconds = 86400000L;
        calendar.setTime(classArrangeWithTimeEntity.getBeginTime());
        Long beginTime = calendar.getTimeInMillis();
        Calendar temp = Calendar.getInstance();

        courseEntities.forEach(n -> {
            SubjectDetailInfo subjectDetailInfo =  PojoMapper.INSTANCE.toCourseInfo(n);
            Long current = beginTime+ + dayMiliSeconds * n.getOffset();
            temp.setTimeInMillis(current);

            calendar.setTimeInMillis(n.getBeginTime());
            temp.add(Calendar.HOUR_OF_DAY,calendar.getTime().getHours());
            temp.add(Calendar.MINUTE,calendar.getTime().getMinutes());

            subjectDetailInfo.setBeginTime(temp.getTime());
            Long endTemp = n.getEndTime()-n.getBeginTime() + temp.getTimeInMillis();
            temp.setTimeInMillis(endTemp);
            subjectDetailInfo.setEndTime(temp.getTime());
            courseInfos.add(subjectDetailInfo);
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

    public PageInfo<ArrangeInfo> page(PageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<ArrangeEntity> arrangeEntities = arrangeMapper.page(pageParam.getParam());
        PageInfo<ArrangeInfo> result = page.toPageInfo();
        result.setList(PojoMapper.INSTANCE.toArrangeInfos(arrangeEntities));
        return result;
    }

    public Boolean delete(List<Integer> arrangeIds) {
        arrangeMapper.delete(arrangeIds);
        return true;
    }

    public Boolean update(CourseArrangeUpdateInfo courseArrangeUpdateInfo) {
        return null;
    }
}
