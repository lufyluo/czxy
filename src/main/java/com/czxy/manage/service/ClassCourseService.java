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
import com.czxy.manage.model.vo.classes.*;
import com.czxy.manage.model.vo.subject.SubjectDetailDomainInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
        List<CourseSubjectDetailInfo> courseInfos = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Long dayMiliSeconds = 86400000L;
        calendar.setTime(classArrangeWithTimeEntity.getBeginTime());
        Long beginTime = calendar.getTimeInMillis();
        Calendar temp = Calendar.getInstance();

        courseEntities.forEach(n -> {
            CourseSubjectDetailInfo subjectDetailInfo = PojoMapper.INSTANCE.toCourseInfo(n);
            Long current = beginTime + +dayMiliSeconds * n.getOffset();
            temp.setTimeInMillis(current);

            calendar.setTimeInMillis(n.getBeginTime());
            temp.add(Calendar.HOUR_OF_DAY, calendar.getTime().getHours());
            temp.add(Calendar.MINUTE, calendar.getTime().getMinutes());

            subjectDetailInfo.setBeginTime(temp.getTime());
            Long endTemp = n.getEndTime() - n.getBeginTime() + temp.getTimeInMillis();
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
        if (classCourseInfo.getCourseInfos() == null || classCourseInfo.getCourseInfos().size() == 0) {
            return true;
        }
        List<CourseArrangeEntity> courseArrangeEntities =
                PojoMapper.INSTANCE.toCourseArrangeEntities(classCourseInfo.getCourseInfos());
        courseArrangeEntities.forEach(n -> n.setArrangeId(arrangeEntity.getId()));
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

    public ClassArrangeTableInfo table(Integer classId) {
        ClassArrangeInfo classArrangeInfo = get(classId);
        ClassArrangeTableInfo classArrangeTableInfo = new ClassArrangeTableInfo();
        if(classArrangeInfo.getBeginTime()!=null){
            classArrangeTableInfo.setHead(getHead(classArrangeInfo.getBeginTime(), classArrangeInfo.getEndTime()));
        }
        if (classArrangeInfo.getCourseInfos() == null
                || classArrangeInfo.getCourseInfos().size() == 0) {
            classArrangeTableInfo.setBody(
                    getBody(classArrangeInfo.getCourseInfos(),
                            classArrangeTableInfo.getHead().size(),
                            classArrangeInfo.getBeginTime()));
        }

        return classArrangeTableInfo;
    }

    private List<String> getHead(Date begin, Date end) {
        List<String> head = new ArrayList<>();
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(begin);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        while (beginCalendar.before(end)) {
            head.add(dateFormat.format(beginCalendar.getTime()));
            beginCalendar.add(1, Calendar.DAY_OF_MONTH);
        }
        head.add(dateFormat.format(endCalendar.getTime()));
        head.stream().distinct();
        return head;
    }

    private List<String[]> getBody(List<CourseSubjectDetailInfo> courseInfos, int size, Date begin) {
        List<SubjectDetailDomainInfo> subjectDetailDomainInfos =
                PojoMapper.INSTANCE.toSubjectDetailDomainInfos(courseInfos);
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
        subjectDetailDomainInfos
                .forEach(n -> n.setTime(
                        hourFormat.format(n.getBeginTime()) + "-" + hourFormat.format(n.getEndTime()))
                );
        Map<String, List<SubjectDetailDomainInfo>> maps
                = subjectDetailDomainInfos.stream().collect(Collectors.groupingBy(n -> n.getTime()));

        List<String[]> body = new ArrayList<>();
        for (Map.Entry<String, List<SubjectDetailDomainInfo>> entry : maps.entrySet()) {
            String[] arr = new String[size];
            arr[0] = entry.getKey();
            setValue(begin, arr, entry.getValue());
            body.add(arr);
        }
        return body;
    }

    private void setValue(Date begin, String[] arr, List<SubjectDetailDomainInfo> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        Calendar beginCalendar = Calendar.getInstance();
        Calendar currentTime = Calendar.getInstance();
        beginCalendar.setTime(begin);
        int dayNum = beginCalendar.get(Calendar.DAY_OF_YEAR);
        //算法有跨年问题
        list.forEach(n -> {
            currentTime.setTime(n.getBeginTime());
            int currentDayNum = currentTime.get(Calendar.DAY_OF_YEAR);
            if (currentDayNum - dayNum > 0 && currentDayNum - dayNum < arr.length) {
                String content = "课题: " + n.getName();
                if (n.getCategory() == 0) {
                    content = content + "\r\n授课老师: " + n.getTeacherName();
                } else if (n.getCategory() == 1) {
                    content = content + "\r\n点位: " + n.getTeacherName();
                }
                arr[dayNum - currentDayNum] = content;
            }

        });
    }
}
