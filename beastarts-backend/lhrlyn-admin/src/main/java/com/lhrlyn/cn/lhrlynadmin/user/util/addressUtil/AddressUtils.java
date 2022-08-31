package com.lhrlyn.cn.lhrlynadmin.user.util.addressUtil;
import com.alibaba.fastjson.JSONObject;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lihaoran
 * @date 2022/8/30 17:33
 */

/**
 *  根据IP地址获取详细的地域信息
 *  @project:personGocheck
 *  @class:AddressUtils.java
 *  @author:heguanhua E-mail:37809893@qq.com
 *  @date：Nov 14, 2012 6:38:25 PM
 */
public class AddressUtils {

    /**
     * description 地址库查询
     *
     * @param ip 地址
     * @return java.lang.String
     * @version 1.0
     */
    public static String getRealAddress(String ip) {
        String result = "";
        try {
            // 加载地址库
            ClassPathResource resource = new ClassPathResource("GeoLite2-City.mmdb");
            DatabaseReader reader = new DatabaseReader.Builder(resource.getInputStream()).build();
            InetAddress inetAddress = InetAddress.getByName(ip);
            CityResponse response = reader.city(inetAddress);
            // 获取所在国家
            String country = response.getCountry().getNames().get("ja");
            // 库内获取不到的IP，访问ali的地域查询
            if (response.getSubdivisions().size() > 0) {
                // 获取所在省份
                String province = response.getSubdivisions().get(0).getNames().get("zh-CN");
                // 获取所在城市
                String city = response.getCity().getNames().get("zh-CN");
                city = city == null ? "" : city;
                result = country + province + city;
            } else {
                result = getAlibaba(ip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * description ali地域查询
     *
     * @param ip ip地址
     * @return java.lang.String
     * @version 1.0
     */
    public static String getAlibaba(String ip) {
        Map map = new HashMap();
        map.put("ip", ip);
        map.put("accessKey", "alibaba-inc");
        String result = post("http://ip.taobao.com/outGetIpInfo", map);
        Map valueMap = JSONObject.parseObject(result, Map.class);

        // 请求成功，解析响应数据
        if ("query success".equals(valueMap.get("msg"))) {
            Map<String, String> dataMap = (Map<String, String>) valueMap.get("data");
            String country = dataMap.get("country");
            String region = dataMap.get("region");
            String city = dataMap.get("city");
            return country + region + city;
        }
        return "";
    }



    public static String post(String url, Map<String, String> mapParameter) {
        System.out.println("开始请求: url = {}, mapParameter = {}, charset = {}"+ url+ mapParameter);
        // 创建httpClient的默认实例
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建POST请求
            HttpPost httpPost = new HttpPost(url);
            // 设置参数
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            // 迭代参数
            if (mapParameter != null && mapParameter.size() > 0) {
                mapParameter.forEach((k, v) -> nameValuePairList.add(new BasicNameValuePair(k, v)));
            }
            // 编码
            if (nameValuePairList.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairList);
                httpPost.setEntity(entity);
            }
            // 执行post请求
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                if (response != null) {
                    System.out.println("请求成功, 响应状态: {}"+ response.getStatusLine().getStatusCode());
                    HttpEntity httpEntity = response.getEntity();
                    // 如果返回的内容不为空
                    if (httpEntity != null) {
                        return EntityUtils.toString(httpEntity);
                    }
                }
            } catch (Exception e) {
                System.out.println("请求异常: e = {}"+e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("请求异常: e = {}"+ e);
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
////        System.out.printf("", getRealAddress("123.179.160.133"));
//        System.out.println(getAlibaba("127.0.0.1"));
//    }

}