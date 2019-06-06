package com.tsing;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

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


}
