package com.czxy.manage.service;

import com.czxy.manage.dao.*;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.CustomerEntity;
import com.czxy.manage.model.vo.customer.CustomerPageParam;
import com.czxy.manage.model.vo.customer.CustomerCreateInfo;
import com.czxy.manage.model.vo.customer.CustomerInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerService {
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private OrgMapper orgMapper;
    @Autowired
    private OrgService orgService;

    public PageInfo<CustomerInfo> page(CustomerPageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<CustomerEntity> customerEntityList = customerMapper.query(pageParam.getParam(), pageParam.getStar());
        PageInfo pageInfo = page.toPageInfo();
        pageInfo.setList(PojoMapper.INSTANCE.toCustomerInfos(customerEntityList));
        return pageInfo;
    }

    @Transactional
    public Boolean update(CustomerInfo customerInfo) {
        customerMapper.update(customerInfo);
        orgMapper.updateStarAndName(customerInfo);
        return true;
    }

    @Transactional
    public Boolean add(CustomerCreateInfo customerInfo) {
        Integer orgId = orgService.insertIfAbsentOrg(customerInfo.getOrgName(), customerInfo.getOrgId());
        customerInfo.setOrgId(orgId);
        CustomerEntity customerEntity = PojoMapper.INSTANCE.toCustomerEntity(customerInfo);
        customerMapper.insert(customerEntity);
        orgMapper.updateStar(customerEntity.getOrgId(),customerEntity.getOrgStar());
        return true;
    }

    public Boolean delete(List<Integer> ids) {
        customerMapper.delete(ids);
        return true;
    }
}
