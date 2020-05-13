package com.czxy.manage.service;

import com.czxy.manage.dao.CustomerMapper;
import com.czxy.manage.dao.OrgMapper;
import com.czxy.manage.dao.StudentMapper;
import com.czxy.manage.dao.UserMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.CustomerEntity;
import com.czxy.manage.model.vo.CustomerPageParam;
import com.czxy.manage.model.vo.student.CustomerInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerService {
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private OrgMapper orgMapper;
    @Autowired
    private OrgService orgService;

    public PageInfo<CustomerInfo> page(CustomerPageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<CustomerEntity> customerEntityList = customerMapper.query(pageParam.getParam(),pageParam.getStar());
        PageInfo pageInfo = page.toPageInfo();
        pageInfo.setList(PojoMapper.INSTANCE.toCustomerInfos(customerEntityList));
        return pageInfo;
    }

    public Boolean update(CustomerInfo customerInfo) {
        userMapper.updateByCustomer(customerInfo);
        orgService.insertIfAbsentOrg(customerInfo.getOrgName(),customerInfo.getOrgId());
        orgMapper.updateStar(customerInfo);
        return true;
    }
}
