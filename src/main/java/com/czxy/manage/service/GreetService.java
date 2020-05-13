package com.czxy.manage.service;

import com.czxy.manage.dao.GreetMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.GreetEntity;
import com.czxy.manage.model.vo.GreetInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GreetService {
    @Resource
    private GreetMapper greetMapper;

    public PageInfo<GreetInfo> page(PageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<GreetEntity> greetEntityList = greetMapper.page(pageParam.getParam());
        PageInfo pageInfo = page.toPageInfo();
        pageInfo.setList(PojoMapper.INSTANCE.toGreetInfos(greetEntityList));
        return pageInfo;
    }

    public Boolean add(GreetInfo greetInfo) {
        greetMapper.add(greetInfo);
        return true;
    }

    public Boolean update(GreetInfo greetInfo) {
        greetMapper.update(greetInfo);
        return true;
    }

    public Boolean delete(List<Integer> ids) {
        greetMapper.delete(ids);
        return true;
    }
}
