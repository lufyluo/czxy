package com.czxy.manage.service;

import com.czxy.manage.dao.CompositionMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.CompositionEntity;
import com.czxy.manage.model.entity.OrgEntity;
import com.czxy.manage.model.vo.CompositionInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CompositionService {
    @Resource
    private CompositionMapper compositionMapper;
    public PageInfo<CompositionInfo> page(PageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(),pageParam.getPageSize());
        List<CompositionEntity> list =  compositionMapper.queryAll(pageParam.getParam());
        PageInfo<CompositionInfo> result = page.toPageInfo();
        result.setList(PojoMapper.INSTANCE.toCompositionInfos(list));
        return result;
    }

    public Integer insertIfAbsent(Integer compositionId,String composition){
        if (compositionId == null && !StringUtils.isEmpty(composition)) {
            CompositionEntity compositionEntity = new CompositionEntity();
            compositionEntity.setName(composition);
            compositionMapper.insertEntity(compositionEntity);
            return compositionEntity.getId();
        }
        return compositionId;
    }
}
