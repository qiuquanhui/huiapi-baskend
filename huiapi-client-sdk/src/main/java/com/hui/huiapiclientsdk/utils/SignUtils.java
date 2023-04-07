package com.hui.huiapiclientsdk.utils;/**
 * 作者:灰爪哇
 * 时间:2023-03-23
 */

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 *
 *
 * @author: shuaihui
 **/
public class SignUtils {

    //签名算法
    public static String getSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);

        String content = null;
        if (body !=null){
           content = body +  '.' + secretKey;
        }else {
            return md5.digestHex(secretKey);
        }

        return md5.digestHex(content);

    }
}
