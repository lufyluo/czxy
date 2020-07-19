package com.czxy.manage.infrastructure.util.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.model.vo.wechat.TagInfo;
import com.czxy.manage.model.vo.wechat.UserTagInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class WechatUtil {
    @Value("${czxy.wechat.accessTokenUrl}")
    public String accessTokenUrl;
    @Value("${czxy.wechat.accessToken}")
    public String accessTokenApi;
    @Value("${czxy.wechat.createTag}")
    public String createTagApi;
    @Value("${czxy.wechat.addTag}")
    public String addTagApi;
    @Value("${czxy.wechat.clearTag}")
    public String clearTagApi;
    @Value("${czxy.wechat.msgTag}")
    public String msgTag;
    @Value("${czxy.wechat.quetionnaire}")
    public String quetionnaire;
    @Value("${czxy.wechat.sendByTag}")
    public String sendByTagApi;
    public String accessToken;
    @Autowired
    private RestTemplate restTemplate;


    public String getOpenId(String code) {
        try {
            String url = String.format(accessTokenUrl, code);
            log.info("url: " + url);
//            ResponseEntity<WechatAccessTokenResponse> forEntity = restTemplate.getForEntity(url, WechatAccessTokenResponse.class);
            ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
            log.info("微信返回值：" + forEntity.getBody());
            Map<String, String> map = JSON.parseObject(forEntity.getBody(), Map.class);
            if (map.get("errcode") == null && map.get("openid") != null) {
                return map.get("openid").toString();
            }
            log.info(map.get("errcode").toString());
            return null;
        } catch (Exception ex) {
            log.error("获取openid发生错误：" + ex);
            return null;
        }

    }

    /**
     * {
     * "filter":{
     * "is_to_all":false,
     * "tag_id":2
     * },
     * "text":{
     * "content":"CONTENT"
     * },
     * "msgtype":"text"
     * }
     */
    public Boolean send(int tag, String msg) {

        JSONObject postData = new JSONObject();
        Map<String, Object> filter = new HashMap<>();
        filter.put("is_to_all", false);
        filter.put("tag_id", tag);

        Map<String, Object> text = new HashMap<>();
        text.put("content", msg);
        postData.put("filter", filter);
        postData.put("text", text);
        postData.put("msgtype", "text");

        TagInfo body = restTemplate.postForEntity(sendByTagApi + accessToken, postData, TagInfo.class).getBody();
        if (body.getErrcode() == 40014 || body.getErrcode() == 42001 || body.getErrcode() == 40001) {
            getAccessToken();
            return send(tag, msg);
        }
        if (body.getErrcode() != null && body.getErrcode() > 0) {
            return false;
        }
        return true;
    }


    /**
     * {   "tag" : {     "name" : "广东"//标签名   } }
     */
    public Integer createTag(String tag) {
        createTagApi += accessToken;
        JSONObject postData = new JSONObject();
        TagInfo tagInfo = new TagInfo();
        tagInfo.setName(tag);
        postData.put("tag", tagInfo);
        tagInfo = restTemplate.postForEntity(createTagApi, postData, TagInfo.class).getBody();
        if (tagInfo.getErrcode() == 40014 || tagInfo.getErrcode() == 42001) {
            getAccessToken();
            return createTag(tag);
        }
        return tagInfo.getId();
    }

    public Boolean addTag(List<String> ids, int tagId) {
        int pageIndex = 0;
        int pageSize = 50;
        int toIndex = pageIndex * pageSize + pageSize;
        int max = Math.min(toIndex, ids.size());
        while (max <= ids.size()) {
            JSONObject postData = new JSONObject();
            List<String> currentIds = ids.subList(pageIndex, max);
            postData.put("openid_list", currentIds);
            postData.put("tagid", tagId);
            TagInfo body = restTemplate.postForEntity(addTagApi + accessToken, postData, TagInfo.class).getBody();
            if (body.getErrcode() == 40014 || body.getErrcode() == 42001 || body.getErrcode() == 40001) {
                getAccessToken();
                addTag(currentIds, tagId);
            }
            if (body.getErrcode() != null && body.getErrcode() > 0) {
                return false;
            }
            if (max == ids.size()) {
                break;
            }
            toIndex += pageSize;
            max = toIndex > ids.size() ? ids.size() : toIndex;
        }
        return true;
    }

    public Boolean clearTag(List<String> ids, int tagId) {
        int pageIndex = 0;
        int pageSize = 50;
        int toIndex = pageIndex * pageSize + pageSize;
        int max = Math.max(toIndex, ids.size());
        while (max < ids.size()) {
            JSONObject postData = new JSONObject();
            List<String> currentIds = ids.subList(pageIndex, max);
            postData.put("openid_list", currentIds);
            postData.put("tagid", tagId);
            TagInfo body = restTemplate.postForEntity(clearTagApi + accessToken, postData, TagInfo.class).getBody();
            if (body.getErrcode() == 40014 || body.getErrcode() == 42001 || body.getErrcode() == 40001) {
                getAccessToken();
                clearTag(currentIds, tagId);
            }
            if (body.getErrcode() != null && body.getErrcode() > 0) {
                return false;
            }

        }
        return true;
    }

    private String getAccessToken() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(accessTokenApi, String.class);
        Map<String, String> map = JSON.parseObject(forEntity.getBody(), Map.class);
        if (map.get("errcode") == null && map.get("access_token") != null) {
            this.accessToken = map.get("access_token");
            return map.get("access_token");
        }
        throw new ManageException(ResponseStatus.ACCESSTOKENFAIL);
    }
}
