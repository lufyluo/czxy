package com.czxy.manage.infrastructure.util.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.model.vo.wechat.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
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
    @Value("${czxy.wechat.findFans}")
    public String findFansApi;
    @Value("${czxy.wechat.clearTag}")
    public String clearTagApi;
    @Value("${czxy.wechat.msgTag}")
    public String msgTag;
    @Value("${czxy.wechat.quetionnaire}")
    public String quetionnaire;
    @Value("${czxy.wechat.sendByTag}")
    public String sendByTagApi;
    @Value("${czxy.wechat.preview}")
    public String preview;
    @Value("${czxy.wechat.uploadnews}")
    public String uploadnews;
    public String accessToken = "42_VX4kDTj-rC5t595HQLaym1xtnrAyv9fGhmnVPCcFTbUtos8hfitIE_KGsVguewDxHfJL_dJok0nSRkALfNPBu95VTmsIMd3jGsfUNiwBFmZKu7imsu6NJ7aCTZ5zRyaqCaCFlAfV1N1iiqPrLKZjAAABAP";
    @Autowired
    private RestTemplate restTemplate;


    public String getOpenId(String code) {
        try {
            String url = String.format(accessTokenUrl, code);
            log.info("url: " + url);
            ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
            log.info("微信返回值：" + forEntity.getBody());
            Map<String, String> map = JSON.parseObject(forEntity.getBody(), Map.class);
            if (map.get("errcode") == null && map.get("openid") != null) {
                return map.get("openid");
            }
            log.info(map.get("errcode"));
            return null;
        } catch (Exception ex) {
            log.error("获取openid发生错误：" + ex);
            return null;
        }

    }

    public Boolean preview(String touser, String msg) {
        return preview(touser, msg, true);
    }

    public Boolean preview(String touser, String msg, boolean retry) {
//        WechatArticleResponse wechatArticleResponse = uploadNews(msg, true);
//        if (wechatArticleResponse == null) {
//            return false;
//        }
        JSONObject postData = new JSONObject();
        Map<String, Object> media = new HashMap<>();
        media.put("media_id", "-F0u8JShzk1lMknWUlyDmS5tjrywScvxj0u3KdaT6l3-pchHgLgyOmHDUWg3ml8Z");
        postData.put("touser", touser);
        postData.put("mpnews", media);
        postData.put("msgtype", "mpnews");
        //图文消息被判定为转载时，是否继续群发。 1为继续群发（转载），0为停止群发。 该参数默认为0。
        postData.put("send_ignore_reprint", 0);

        WechatResponse body = restTemplate.postForEntity(preview + accessToken, postData, TagInfo.class).getBody();
        if (body.getErrcode() == 40014 || body.getErrcode() == 42001 || body.getErrcode() == 40001) {
            if (!retry) {
                return false;
            }
            getAccessToken();
            return preview(touser, msg, false);
        }
        if (body.getErrcode() != null && body.getErrcode() > 0) {
            return false;
        }
        return true;
    }

    private WechatArticleResponse uploadNews(String msg, boolean retry) {
        Map arcticles = arcticleMap(msg);
        WechatArticleResponse body = restTemplate.postForEntity(uploadnews + accessToken, arcticles, WechatArticleResponse.class).getBody();
        if (body.getErrcode() == 40014 || body.getErrcode() == 42001 || body.getErrcode() == 40001) {
            if (!retry) {
                return null;
            }
            getAccessToken();
            return uploadNews(msg, false);
        }
        if (body.getErrcode() != null && body.getErrcode() > 0) {
            return null;
        }
        return body;
    }

    private Map<String, List<WechatArticle>> arcticleMap(String msg) {
        Map<String, List<WechatArticle>> map = new HashMap();
        map.put("articles", arcticles(msg));
        return map;
    }

    private List<WechatArticle> arcticles(String msg) {
        WechatArticle article = new WechatArticle();
        article.setTitle("问卷调查");
        article.setContent(msg);
        article.setDigest("<a href='www.sina.com'>内容</a>");
        article.setContent_source_url("www.baidu.com");
        return Arrays.asList(article);
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
        return send(tag, msg, true);
    }

    public Boolean send(int tag, String msg, boolean retry) {

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
            if (!retry) {
                return false;
            }
            getAccessToken();
            return send(tag, msg, false);
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
        if (StringUtils.isEmpty(accessToken)) {
            getAccessToken();
        }
        String url = createTagApi + accessToken;
        JSONObject postData = new JSONObject();
        TagInfo tagInfo = new TagInfo();
        tagInfo.setName(tag);
        postData.put("tag", tagInfo);
        tagInfo = restTemplate.postForEntity(url, postData, TagInfo.class).getBody();
        if (tagInfo.getErrcode() == 40014 || tagInfo.getErrcode() == 42001 || tagInfo.getErrcode() == 40001) {
            getAccessToken();
            return createTag(tag);
        }
        return tagInfo.getId();
    }

    public Boolean hasFans(Integer tagId) {
        return hasFans(tagId, true);
    }

    public Boolean hasFans(Integer tagId, Boolean retry) {
        JSONObject postData = new JSONObject();
        postData.put("tagid", tagId);
        WechatTagResponse body = restTemplate.postForEntity(findFansApi + accessToken, postData, WechatTagResponse.class).getBody();
        if (body.getCount() != null && body.getCount() == 0) {
            return false;
        }
        if (body.getErrcode() != null && (body.getErrcode() == 40014 || body.getErrcode() == 42001 || body.getErrcode() == 40001)) {
            if (!retry) {
                return true;
            }
            getAccessToken();
            hasFans(tagId, false);
        }
        if (body.getErrcode() != null && body.getErrcode() > 0) {
            return false;
        }
        if (body.getCount() == 0) {
            return false;
        }
        return true;
    }

    public Boolean addTag(List<String> openIds, int tagId) {
        return addTag(openIds, tagId, true);
    }

    public Boolean addTag(List<String> openIds, int tagId, boolean retry) {
        int pageIndex = 0;
        int pageSize = 50;
        int toIndex = pageIndex * pageSize + pageSize;
        int max = Math.min(toIndex, openIds.size());
        while (max <= openIds.size()) {
            JSONObject postData = new JSONObject();
            List<String> currentIds = openIds.subList(pageIndex * pageSize, max);
            postData.put("openid_list", currentIds);
            postData.put("tagid", tagId);
            TagInfo body = restTemplate.postForEntity(addTagApi + accessToken, postData, TagInfo.class).getBody();
            if (body.getErrcode() == 40014 || body.getErrcode() == 42001 || body.getErrcode() == 40001) {
                if (!retry) {
                    return false;
                }
                getAccessToken();
                addTag(currentIds, tagId, false);
            }
            if (body.getErrcode() != null && body.getErrcode() > 0) {
                return false;
            }
            if (max == openIds.size()) {
                break;
            }
            toIndex += pageSize;
            pageIndex++;
            max = toIndex > openIds.size() ? openIds.size() : toIndex;
        }
        return true;
    }

    public Boolean clearTag(List<String> openIds, int tagId) {
        return clearTag(openIds, tagId, true);
    }

    public Boolean clearTag(List<String> openIds, int tagId, boolean retry) {
        int pageIndex = 0;
        int pageSize = 50;
        int toIndex = pageIndex * pageSize + pageSize;
        int max = Math.min(toIndex, openIds.size());
        while (max <= openIds.size()) {
            JSONObject postData = new JSONObject();
            List<String> currentIds = openIds.subList(pageIndex, max);
            postData.put("openid_list", currentIds);
            postData.put("tagid", tagId);
            TagInfo body = restTemplate.postForEntity(clearTagApi + accessToken, postData, TagInfo.class).getBody();
            if (body.getErrcode() == 40014 || body.getErrcode() == 42001 || body.getErrcode() == 40001) {
                if (!retry) {
                    return false;
                }
                getAccessToken();
                return clearTag(currentIds, tagId, false);
            }
            if (body.getErrcode() != null && body.getErrcode() > 0) {
                return false;
            }
            if (max == openIds.size()) {
                break;
            }
            toIndex += pageSize;
            max = toIndex > openIds.size() ? openIds.size() : toIndex;
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

    public Boolean sendAll(Integer tagId, String msg, boolean retry) {
        JSONObject postData = new JSONObject();
        Map<String, Object> filter = new HashMap<>();
        filter.put("is_to_all", false);
        filter.put("tag_id", tagId);

        Map<String, Object> text = new HashMap<>();
        text.put("content", msg);
        postData.put("filter", filter);
        postData.put("text", text);
        postData.put("msgtype", "text");

        TagInfo body = restTemplate.postForEntity(sendByTagApi + accessToken, postData, TagInfo.class).getBody();
        if (body.getErrcode() == 40014 || body.getErrcode() == 42001 || body.getErrcode() == 40001) {
            if (!retry) {
                return false;
            }
            getAccessToken();
            return send(tagId, msg, false);
        }
        if (body.getErrcode() != null && body.getErrcode() > 0) {
            return false;
        }
        return true;
    }
}
