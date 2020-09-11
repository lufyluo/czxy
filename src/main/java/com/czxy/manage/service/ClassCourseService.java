package com.czxy.manage.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelCollectionParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.util.PoiPublicUtil;
import com.czxy.manage.dao.ArrangeMapper;
import com.czxy.manage.dao.ClassMasterMapper;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClassCourseService {

    @Resource
    ArrangeMapper arrangeMapper;
    @Resource
    private SubjectMapper subjectMapper;
    @Resource
    private CourseArrangeMapper courseArrangeMapper;
    @Resource
    ClassMasterMapper classMasterMapper;
    @Resource
    HttpServletResponse httpServletResponse;

    public ClassArrangeInfo get(Integer classId) {
        ClassArrangeWithTimeEntity classArrangeWithTimeEntity = arrangeMapper.get(classId);
        if (classArrangeWithTimeEntity == null) {
            throw new ManageException(ResponseStatus.DATANOTEXIST);
        }

        ClassArrangeInfo classArrangeInfo = PojoMapper.INSTANCE.toClassArrangeInfo(classArrangeWithTimeEntity);
        buildState(classArrangeInfo);
        List<CourseDetailEntity> courseEntities = subjectMapper.queryByArrangeId(classArrangeWithTimeEntity.getId());
        List<CourseSubjectDetailInfo> courseInfos = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(classArrangeWithTimeEntity.getBeginTime());
        courseEntities.forEach(n -> {
            CourseSubjectDetailInfo subjectDetailInfo = PojoMapper.INSTANCE.toCourseInfo(n);
            if (subjectDetailInfo.getCategory() == 1) {
                subjectDetailInfo.setId(n.getSiteId());
                subjectDetailInfo.setName(n.getSiteName());
            }
            calendar.setTimeInMillis(n.getBeginTime());
            subjectDetailInfo.setBeginTime(calendar.getTime());
            calendar.setTimeInMillis(n.getEndTime());
            subjectDetailInfo.setEndTime(calendar.getTime());
            courseInfos.add(subjectDetailInfo);
        });
        classArrangeInfo.setCourseInfos(courseInfos);
        return classArrangeInfo;
    }

    private void buildState(ClassArrangeInfo classArrangeInfo) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        classArrangeInfo.setState(0);
        classArrangeInfo.setStateDesc("未使用");
        if (calendar.after(classArrangeInfo.getBeginTime())
                && calendar.before(classArrangeInfo.getEndTime())) {
            classArrangeInfo.setState(1);
            classArrangeInfo.setStateDesc("使用中");
        }
    }


    public ClassArrangeInfo getById(Integer id) {
        ClassArrangeWithTimeEntity classArrangeWithTimeEntity = arrangeMapper.getById(id);
        if (classArrangeWithTimeEntity == null) {
            throw new ManageException(ResponseStatus.DATANOTEXIST);
        }

        ClassArrangeInfo classArrangeInfo = PojoMapper.INSTANCE.toClassArrangeInfo(classArrangeWithTimeEntity);
        buildState(classArrangeInfo);
        List<CourseDetailEntity> courseEntities = subjectMapper.queryByArrangeId(classArrangeWithTimeEntity.getId());
        List<CourseSubjectDetailInfo> courseInfos = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        setDurantion(classArrangeInfo, courseEntities);
        courseEntities.forEach(n -> {
            CourseSubjectDetailInfo subjectDetailInfo = PojoMapper.INSTANCE.toCourseInfo(n);
            if (subjectDetailInfo.getCategory() == 1) {
                subjectDetailInfo.setId(n.getSiteId());
                subjectDetailInfo.setName(n.getSiteName());
            }
            calendar.setTimeInMillis(n.getBeginTime());
            subjectDetailInfo.setBeginTime(calendar.getTime());
            calendar.setTimeInMillis(n.getEndTime());
            subjectDetailInfo.setEndTime(calendar.getTime());
            courseInfos.add(subjectDetailInfo);
        });
        classArrangeInfo.setCourseInfos(courseInfos);
        return classArrangeInfo;
    }

    private void setDurantion(ClassArrangeInfo classArrangeInfo, List<CourseDetailEntity> courseEntities) {
        if (courseEntities != null && courseEntities.size() > 0) {
            Calendar calendar = Calendar.getInstance();
            Long begin = courseEntities.stream().map(n -> n.getBeginTime()).min(Long::compareTo).get();
            calendar.setTimeInMillis(begin);
            classArrangeInfo.setBeginTime(calendar.getTime());
            Long end = courseEntities.stream().map(n -> n.getEndTime()).max(Long::compareTo).get();
            calendar.setTimeInMillis(end);
            classArrangeInfo.setEndTime(calendar.getTime());
        }
    }

    @Transactional
    public Integer add(CourseArrangeAddInfo classCourseInfo) {
        ArrangeEntity arrangeEntity = PojoMapper.INSTANCE.toArrangeEntity(classCourseInfo);
        arrangeMapper.insert(arrangeEntity);
        if (classCourseInfo.getCourseInfos() == null || classCourseInfo.getCourseInfos().size() == 0) {
            return arrangeEntity.getId();
        }
        List<CourseArrangeEntity> courseArrangeEntities =
                PojoMapper.INSTANCE.toCourseArrangeEntities(classCourseInfo.getCourseInfos());
        courseArrangeEntities.forEach(n -> n.setArrangeId(arrangeEntity.getId()));
        courseArrangeMapper.batchInsert(courseArrangeEntities);
        return arrangeEntity.getId();
    }

    public PageInfo<ArrangeInfo> page(PageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<ArrangeEntity> arrangeEntities = arrangeMapper.page(pageParam.getParam());
        PageInfo<ArrangeInfo> result = page.toPageInfo();
        List<ArrangeInfo> arrangeInfos = PojoMapper.INSTANCE.toArrangeInfos(arrangeEntities);
        Calendar calendar = Calendar.getInstance();
        arrangeInfos.forEach(n -> {
            n.setState(0);
            n.setStateDesc("未使用");
            if (n.getBeginTime() != null && n.getEndTime() != null) {
                calendar.setTime(new Date());
                if (calendar.after(n.getBeginTime())
                        && calendar.before(n.getEndTime())) {
                    n.setState(1);
                    n.setStateDesc("使用中");
                }
            }
        });
        result.setList(arrangeInfos);
        return result;
    }

    public Boolean delete(List<Integer> arrangeIds) {
        arrangeMapper.delete(arrangeIds);
        return true;
    }

    @Transactional
    public Boolean update(CourseArrangeUpdateInfo courseArrangeUpdateInfo) {
        ArrangeEntity arrangeEntity = PojoMapper.INSTANCE.updateInfoToArrangeEntity(courseArrangeUpdateInfo);
        arrangeMapper.update(arrangeEntity);

        if (courseArrangeUpdateInfo.getCourseInfos() == null
                || courseArrangeUpdateInfo.getCourseInfos().size() == 0) {
            return true;
        }
        courseArrangeMapper.deleteByArrangeId(arrangeEntity.getId());
        List<CourseArrangeEntity> courseArrangeEntities =
                PojoMapper.INSTANCE.toCourseArrangeEntities(courseArrangeUpdateInfo.getCourseInfos());
        courseArrangeEntities.forEach(n -> n.setArrangeId(arrangeEntity.getId()));
        courseArrangeMapper.batchInsert(courseArrangeEntities);

        return true;
    }

    public ClassArrangeTableInfo table(Integer classId) {
        ClassArrangeInfo classArrangeInfo = get(classId);
        ClassArrangeTableInfo classArrangeTableInfo = new ClassArrangeTableInfo();
        if (classArrangeInfo.getBeginTime() != null) {
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

    public ClassArrangeTableInfo tableById(Integer id) {
        ClassArrangeInfo classArrangeInfo = getById(id);
        ClassArrangeTableInfo classArrangeTableInfo = new ClassArrangeTableInfo();
        if (classArrangeInfo.getBeginTime() != null) {
            classArrangeTableInfo.setHead(getHead(classArrangeInfo.getBeginTime(), classArrangeInfo.getEndTime()));
        }
        if (classArrangeInfo.getCourseInfos() != null
                && classArrangeInfo.getCourseInfos().size() > 0) {
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
        head.add("班次日期");
        while (beginCalendar.getTimeInMillis() < end.getTime()
                && beginCalendar.getTime().getDate() != end.getDate()) {
            head.add(dateFormat.format(beginCalendar.getTime()));
            beginCalendar.add(Calendar.DATE, 1);
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
        subjectDetailDomainInfos.sort(Comparator.comparing(SubjectDetailDomainInfo::getTime));
        Map<String, List<SubjectDetailDomainInfo>> maps
                = subjectDetailDomainInfos.stream().collect(Collectors.groupingBy(n -> n.getTime(), LinkedHashMap::new, Collectors.toList()));

        List<String[]> body = new ArrayList<>();
        for (Map.Entry<String, List<SubjectDetailDomainInfo>> entry : maps.entrySet()) {
            String[] arr = new String[size + 1];
            setValue(begin, arr, entry.getValue());
            arr[0] = entry.getKey();
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
            if (currentDayNum - dayNum >= 0 && currentDayNum - dayNum < arr.length) {
                String content = "课题: " + n.getName();
                if (n.getCategory() == 0) {
                    if (!StringUtils.isEmpty(n.getTeacherName())) {
                        content = content + "  授课老师: " + n.getTeacherName();
                    }
                    if (!StringUtils.isEmpty(n.getDescription())) {
                        content = content + "  描述:" + n.getDescription();
                    }
                } else if (n.getCategory() == 1) {
                    if (StringUtils.isEmpty(n.getAddress())) {
                        content = content + "  点位: --";
                    } else {
                        content = content + "  点位: " + n.getAddress();
                    }
                }
                if (arr.length > dayNum - currentDayNum + 1) {
                    int arrIndex = currentDayNum - dayNum + 1;
                    arr[arrIndex] = content;
                }
            }

        });
    }

    public ClassArrangeInfo getByUserId(Integer userId) {
        Integer classId = courseArrangeMapper.queryRecentClassIdByUserId(userId);
        if (classId == null) {
            classId = classMasterMapper.queryClass(userId);
        }
        if (classId == null) {
            return null;
        }
        return get(classId);
    }

    public void exportFile(Integer id) throws Exception {
        ClassArrangeTableInfo tableInfo = tableById(id);
        int size = tableInfo.getBody().size();
        int length = tableInfo.getHead().size();
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        sheet.setDefaultColumnWidth(50);
        for (int rowNum = 0; rowNum < size + 1; rowNum++) {
            Row row = sheet.createRow(rowNum);
            row.setHeight((short) 500);
            for (int i = 0; i < length; i++) {
                Cell cell = getCell(row, i, workbook);
                if (rowNum == 0) {
                    cell.setCellValue(tableInfo.getHead().get(i));
                } else {
                    String[] strings = tableInfo.getBody().get(rowNum - 1);
                    List<String> asList = Arrays.asList(strings);
                    String s = asList.get(i);
                    if (s != null) {
                        cell.setCellValue(s);
                    } else {
                        cell.setCellValue("");
                    }
                }
            }
        }
        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("班级课表.xls", "UTF-8"));
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }

    private Cell getCell(Row row, Integer index, Workbook workbook) {
        Cell cell = row.createCell(index);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cell.setCellStyle(cellStyle);
        return cell;
    }
}
