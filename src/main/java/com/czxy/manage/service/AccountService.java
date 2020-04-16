package com.czxy.manage.service;

import com.czxy.manage.dao.AccountMapper;
import com.czxy.manage.dao.TokenMapper;
import com.czxy.manage.dao.UserAccountMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.model.entity.AccountEntity;
import com.czxy.manage.model.entity.TokenEntity;
import com.czxy.manage.model.entity.UserEntity;
import com.czxy.manage.model.vo.user.AccountInfo;
import com.czxy.manage.model.vo.user.ChangePwdInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Base64;
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

    public String login(AccountInfo accountInfo, String timestamp) {
        String pwd = decodePassword(accountInfo.getPassword(), timestamp);
        AccountEntity accountEntity = accountMapper.query(accountInfo.getAccount(), pwd);
        if (accountEntity != null && accountEntity.getUserId() > 0) {
            String token = UUID.randomUUID().toString();
            tokenMapper.delete(accountEntity.getAccount());
            tokenMapper.insert(accountInfo.getAccount(), token, expire);
            return token;
        }
        throw new ManageException(ResponseStatus.LOGINERROR);
    }

    private String decodePassword(String enodePassword, String timestamp) {
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

        AccountEntity accountEntity = userAccountMapper.query(token, pwd);

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
        return accountMapper.delete(accountId)==1;
    }
}
