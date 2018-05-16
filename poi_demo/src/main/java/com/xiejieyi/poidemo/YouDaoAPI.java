package com.xiejieyi.poidemo;

/**
 * 类描述：
 *
 * @author
 */

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;


public class YouDaoAPI
{
    // public static void main(String[] args) throws Exception
    public static Map translate(String query)
    {
        String appKey = "7a90639969136c02";
        // String query = "Thanks";
        String salt = String.valueOf(System.currentTimeMillis());
        String from = "EN";
        String to = "zh-CHS";
        String sign = md5(appKey + query + salt + "9vhBmQOY4wKYSreOb9gCbYUVX5KMv0Zs");
        Map params = new HashMap();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("sign", sign);
        params.put("salt", salt);
        params.put("appKey", appKey);
        // params.put("ext",false);
        // params.put("voice",false);

        Map result = requestForHttp("http://openapi.youdao.com/api", params);
    //    单词 音标 词性 释义 例句
        Map newResult =  new HashMap<String,Object>();
        newResult.put("word",result.get("query"));
        Map basicResult = (Map)result.get("basic");
        String phonetic= "us:["+basicResult.get("us-phonetic")+"]" + " uk:["+basicResult.get("uk-phonetic")+"]";
        newResult.put("phonetic",phonetic);
        newResult.put("explains",result.get("explains"));
        return newResult;
        // .get("us-phonetic")
        // newResult.put("")
    }

    public static Map<String, Object> requestForHttp(String url, Map requestParams)
    {
        String result;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        /**HttpPost*/
        HttpPost httpPost = new HttpPost(url);
        // System.out.println(new JSONObject(requestParams).toString());
        List params = new ArrayList();
        Iterator<Entry> it = requestParams.entrySet().iterator();
        while(it.hasNext())
        {
            Entry en = it.next();
            String key = (String) en.getKey();
            String value = (String) en.getValue();
            if (value != null)
            {
                params.add(new BasicNameValuePair(key, value));
            }
        }
        CloseableHttpResponse httpResponse = null;
        Map<String, Object> map = new HashMap<>();
        try
        {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            /**HttpResponse*/
            httpResponse = httpClient.execute(httpPost);


            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, "utf-8");
            EntityUtils.consume(httpEntity);

            Gson gson = new Gson();

            map = gson.fromJson(result, map.getClass());
            System.out.println(map);

        }
        catch(Exception e){
            e.printStackTrace();
        }finally
        {
            try
            {
                if (httpResponse != null)
                {
                    httpResponse.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 生成32位MD5摘要
     *
     * @param string
     * @return
     */
    public static String md5(String string)
    {
        if (string == null)
        {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};

        try
        {
            byte[] btInput = string.getBytes("utf-8");
            /** 获得MD5摘要算法的 MessageDigest 对象 */
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            /** 使用指定的字节更新摘要 */
            mdInst.update(btInput);
            /** 获得密文 */
            byte[] md = mdInst.digest();
            /** 把密文转换成十六进制的字符串形式 */
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md)
            {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            return null;
        }
    }

    /**
     * 根据api地址和参数生成请求URL
     *
     * @param url
     * @param params
     * @return
     */
    public static String getUrlWithQueryString(String url, Map<String,String> params)
    {
        if (params == null)
        {
            return url;
        }

        StringBuilder builder = new StringBuilder(url);
        if (url.contains("?"))
        {
            builder.append("&");
        } else
        {
            builder.append("?");
        }

        int i = 0;
        for (String key : params.keySet())
        {
            String value = params.get(key);
            if (value == null)
            { // 过滤空的key
                continue;
            }

            if (i != 0)
            {
                builder.append('&');
            }

            builder.append(key);
            builder.append('=');
            builder.append(encode(value));

            i++;
        }

        return builder.toString();
    }

    /**
     * 进行URL编码
     *
     * @param input
     * @return
     */
    public static String encode(String input)
    {
        if (input == null)
        {
            return "";
        }

        try
        {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return input;
    }
}
