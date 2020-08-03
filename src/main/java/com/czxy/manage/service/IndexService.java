package com.czxy.manage.service;

import com.czxy.manage.dao.ClassMapper;
import com.czxy.manage.dao.IndexMapper;
import com.czxy.manage.dao.StudentMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.model.vo.IndexInfo;
import com.czxy.manage.model.vo.NumbersInfo;
import com.czxy.manage.model.vo.index.RankInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IndexService {
    @Resource
    private IndexMapper indexMapper;
    @Autowired
    private MessageService messageService;
    @Resource
    private ClassMapper classMapper;
    @Resource
    private StudentMapper studentMapper;

    public IndexInfo get(Integer userId) {
        List<String> url = indexMapper.getUrl();
        IndexInfo indexInfo = new IndexInfo();
        indexInfo.setUrl(url);
        indexInfo.setMessage(messageService.getNews(userId));
        return indexInfo;
    }

    public NumbersInfo getNumbers() {
        NumbersInfo numbersInfo = new NumbersInfo();
        numbersInfo.setStudentNumbers(indexMapper.getStudentNumbers());
        numbersInfo.setTeacherNumbers(indexMapper.getTeacherNumbers());
        numbersInfo.setSiteNumbers(indexMapper.getSiteNumbers());
        numbersInfo.setSubjectNumbers(indexMapper.getSubjectNumbers());
        return numbersInfo;
    }

    public List<RankInfo> getTeacherRank() {
        return indexMapper.getTeacherCount();
    }

    public List<RankInfo> getSiteRank() {
        return indexMapper.getSiteCount();
    }

    public List<RankInfo> getClassTrend(Date beginTime, Date endTime) {
        return getTrendData(beginTime, endTime, classMapper.queryByTime(beginTime, endTime));
    }

    public List<RankInfo> getStudentTrend(Date beginTime, Date endTime) {
        return getTrendData(beginTime, endTime, studentMapper.queryByTime(beginTime, endTime));
    }

    private List<RankInfo> getTrendData(Date beginTime, Date endTime, List<RankInfo> rankInfos2) {
        if (beginTime != null && endTime != null && endTime.getTime() < beginTime.getTime()) {
            throw new ManageException(ResponseStatus.ARGUMENTNOTVALID, "结束时间");
        }
        List<RankInfo> rankInfos = rankInfos2;
        return filledDate(beginTime, endTime, rankInfos);
    }

    public List<RankInfo> filledDate(Date beginTime, Date endTime, List<RankInfo> data) {
        List<String> strings = dateFill(beginTime, endTime);
        if (data == null || data.size() == 0) {
            return strings.stream().map(n -> {
                RankInfo rankInfo = new RankInfo();
                rankInfo.setName(n);
                return rankInfo;
            }).collect(Collectors.toList());
        }
        return strings.stream().map(n -> {
            RankInfo rankInfo = new RankInfo();
            rankInfo.setName(n);
            Optional<RankInfo> first = data.stream().filter(d -> ObjectUtils.nullSafeEquals(n, d.getName())).findFirst();
            if (first.isPresent()) {
                rankInfo.setValue(first.get().getValue());
            }
            return rankInfo;
        }).collect(Collectors.toList());
    }

    public List<String> dateFill(Date beginTime, Date endTime) {
        if (endTime == null) {
            endTime = new Date();
        }
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        List<String> dates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        do {
            dates.add(sdf.format(begin.getTime()));
            begin.add(Calendar.MONTH, 1);
        }
        while (begin.getTime().before(endTime));
        String endTimeStr = sdf.format(endTime);
        if (!dates.contains(endTimeStr)) {
            dates.add(endTimeStr);
        }
        return dates;
    }
}
