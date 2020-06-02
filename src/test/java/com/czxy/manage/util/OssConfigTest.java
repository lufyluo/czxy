package com.czxy.manage.util;

import com.czxy.manage.infrastructure.util.Base64Util;
import org.junit.jupiter.api.Test;
import sun.misc.BASE64Encoder;

public class OssConfigTest {
    @Test
    public void md5Test(){
        String md = "prod-czxy";
        String result = Base64Util.encodeData(md);
        result = Base64Util.encodeData(result);
        System.out.println(result);
        System.out.println(Base64Util.decodeData(Base64Util.decodeData(result)));
    }
}
