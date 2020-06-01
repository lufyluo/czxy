package com.czxy.manage.service;

import com.czxy.manage.dao.IndexMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndexService {
    @Resource
    private IndexMapper indexMapper;

    public List<String> get(List<Integer> ids) {
        List<String> url = indexMapper.getUrl(ids);
        return url;
    }
}
