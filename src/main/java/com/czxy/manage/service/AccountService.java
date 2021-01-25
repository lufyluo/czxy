package com.czxy.manage.service;

import com.czxy.manage.dao.AccountMapper;
import com.czxy.manage.dao.TokenMapper;
import com.czxy.manage.dao.UserAccountMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.infrastructure.util.wechat.WechatUtil;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.AccountEntity;
import com.czxy.manage.model.vo.user.AccountInfo;
import com.czxy.manage.model.vo.user.ChangePwdInfo;
import com.czxy.manage.model.vo.user.UserAccountInfo;
import com.czxy.manage.model.vo.user.UserInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-16 下午9:32
 */
@Service
@Slf4j
public class AccountService {
    @Resource
    private AccountMapper accountMapper;
    @Resource
    private UserAccountMapper userAccountMapper;
    @Resource
    private TokenMapper tokenMapper;
    @Value("${token.expire}")
    public Long expire;
    @Autowired
    public UserService userService;
    @Autowired
    private WechatUtil wechatUtil;

    public String login(AccountInfo accountInfo, String timestamp) {
        String pwd = decodePassword(accountInfo.getPassword(), timestamp);
        AccountEntity accountEntity = accountMapper.query(accountInfo.getAccount(), pwd);
        if (accountEntity != null && accountEntity.getUserId() > 0) {
            String token = UUID.randomUUID().toString();
            tokenMapper.delete(accountEntity.getAccount());
            tokenMapper.insert(accountEntity.getUserId(), accountInfo.getAccount(), token, expire);
            return token;
        }
        throw new ManageException(ResponseStatus.LOGINERROR);
    }

    public String decodePassword(String enodePassword, String timestamp) {
        String base64Decode = new String(Base64.getDecoder().decode(enodePassword));
        StringTokenizer stringTokenizer = new StringTokenizer(base64Decode, ";");
        String result = stringTokenizer.nextToken();
        return result;
//        if (stringTokenizer.nextToken().equals(timestamp)) {  暂时不验证时间戳
//            return result;
//        }else {
//            throw new UserCenterException(ResponseStatus.FAILURE,"账号密码不合法");
//        }
    }

    public Boolean changePassword(ChangePwdInfo changePwdInfo, String token) {
        String pwd = decodePassword(changePwdInfo.getOriginPassword(), null);
        String newPwd = decodePassword(changePwdInfo.getNewPassword(), null);

        AccountEntity accountEntity = userAccountMapper.query(pwd, token);

        if (accountEntity == null) {
            throw new ManageException(ResponseStatus.ORIGINPWDERROR);
        }
        Integer result = accountMapper.updatePwd(accountEntity.getId(), newPwd);
        if (result == null || result < 1) {
            log.error("更新密码发生错误，用户id{}，旧密码{}，新密码{}", accountEntity.getId(), pwd, newPwd);
            throw new ManageException(ResponseStatus.FAILURE);
        }
        tokenMapper.delete(accountEntity.getAccount());
        return true;
    }

    public Boolean delete(Integer accountId) {
        return accountMapper.delete(accountId) == 1;
    }

    public PageInfo<UserAccountInfo> page(PageParam<String> pageParam) {
        Page pageUtil = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<UserAccountInfo> result = userAccountMapper.queryAll(pageParam.getParam());
        fillGender(result);
        PageInfo<UserAccountInfo> userAccountPageInfo = pageUtil.toPageInfo();
        userAccountPageInfo.setList(PojoMapper.INSTANCE.toUserAccountInfos(result));
        return userAccountPageInfo;
    }

    private void fillGender(List<UserAccountInfo> userAccountInfos) {
        if (userAccountInfos != null) {
            userAccountInfos.forEach(n -> {
                if(n.getGender()==null){
                    n.setGender(2);
                }
                switch (n.getGender()) {
                    case 0:
                        n.setGenderDesc("男");
                        break;
                    case 1:
                        n.setGenderDesc("女");
                        break;
                    default:
                        n.setGenderDesc("未知");
                        break;
                }
            });
        }
    }

    public String wechatLogin(String code) {
        if(StringUtils.isEmpty(code)){
            throw new ManageException(ResponseStatus.ARGUMENTNOTVALID);
        }
        String openId = wechatUtil.getOpenId(code);
        if(StringUtils.isEmpty(openId)){
            throw new ManageException(ResponseStatus.FAILURE,"获取认证失败！");
        }
        UserInfo userInfo = userService.queryByWechatId(openId);
        if(userInfo == null){
            throw new ManageException(ResponseStatus.FAILURE,openId);
        }
        String token = UUID.randomUUID().toString();
        tokenMapper.delete(userInfo.getPhone());
        tokenMapper.insert(userInfo.getId(), userInfo.getPhone(), token, expire);
        return token;
    }
}
