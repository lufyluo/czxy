package com.czxy.manage.service;

import com.czxy.manage.dao.IndexMapper;
import com.czxy.manage.model.vo.IndexInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndexService {
    @Resource
    private IndexMapper indexMapper;

    public IndexInfo get(List<Integer> ids) {
        List<String> url = indexMapper.getUrl(ids);
        String s = indexMapper.queryNewMessage();
        IndexInfo indexInfo = new IndexInfo();
        indexInfo.setUrl(url);
        indexInfo.setMessage(s);
        return indexInfo;
    }
}
