package com.czxy.manage.service;

import com.czxy.manage.dao.IndexMapper;
import com.czxy.manage.model.vo.IndexInfo;
import com.czxy.manage.model.vo.NumbersInfo;
import com.czxy.manage.model.vo.index.RankInfo;
import com.czxy.manage.model.vo.index.TrendInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndexService {
    @Resource
    private IndexMapper indexMapper;
    @Autowired
    private MessageService messageService;

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
        return null;
    }

    public List<RankInfo> getSiteRank() {
        return null;
    }

    public List<TrendInfo> getClassTrend(Long beginTime, Long endTime) {
        return null;
    }

    public List<TrendInfo> getStudentTrend(Long beginTime, Long endTime) {
        return null;
    }
}
