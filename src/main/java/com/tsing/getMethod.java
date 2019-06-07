package com.tsing;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class getMethod {

    private CloseableHttpClient client = null;
    private CookieStore cookieStore = null;
    @Test
    public void getCookieTest() throws IOException {
        String testURL = "http://localhost:8890/getcookie";
        cookieStore = new BasicCookieStore();
        client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpGet get = new HttpGet(testURL);
        HttpResponse response = client.execute(get);
        String result = EntityUtils.toString(response.getEntity());
        for(Cookie cookie : cookieStore.getCookies()){
            System.out.println(cookie.getName()+"..."+cookie.getValue());
            Assert.assertEquals(cookie.getName(),"code");
            Assert.assertEquals(cookie.getValue(),"10001");
        }

    }
    @Test(dependsOnMethods = {"getCookieTest"})
    public void getWithCookie() throws IOException {
        System.out.println("===="+cookieStore.getCookies()+"=====");
        String testURL = "http://localhost:8890/get/with/cookie";
        HttpGet get = new HttpGet(testURL);
        HttpResponse response = client.execute(get);
        String result = EntityUtils.toString(response.getEntity());
        Assert.assertEquals(result,"收到cookie，访问成功！");
    }

    @Test
    public void getParamTest() throws Exception {
        client = HttpClients.createDefault();
//        第一种添加参数的方式
//        URI uri = new URIBuilder("http://localhost:8890/get/with/param")
//                .setParameter("startPage", "1")
//                .setParameter("endPage","10").build();

//        第二种天剑参数的方式
        URIBuilder uri = new URIBuilder("http://localhost:8890/get/with/param");
        List<NameValuePair> params = new LinkedList<>();
        BasicNameValuePair startPage = new BasicNameValuePair("startPage", "10");
        BasicNameValuePair endPage = new BasicNameValuePair("endPage", "20");
        params.add(startPage);
        params.add(endPage);
        uri.setParameters(params);
        HttpGet get = new HttpGet(uri.build());
        CloseableHttpResponse response = client.execute(get);
        String content = EntityUtils.toString(response.getEntity(),"UTF-8");
        JSONObject goods = new JSONObject(content);
        System.out.println(goods.get("蓝牙耳机"));
        Assert.assertEquals(goods.get("蓝牙耳机"),256.9);

    }

}
