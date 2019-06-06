package com.tsing;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class postMethod {

    CookieStore cookieStore = null;
    CloseableHttpClient client = null;
    @Test
    public void postWithParam() throws IOException {
        String testURL = "http://localhost:8890/post/login";
        cookieStore = new BasicCookieStore();
        client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpPost post = new HttpPost(testURL);
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        param.add(new BasicNameValuePair("userName","kevin"));
        param.add(new BasicNameValuePair("password", "123456"));
        post.setEntity(new UrlEncodedFormEntity(param, Consts.UTF_8));

        CloseableHttpResponse response = client.execute(post);
        String result = EntityUtils.toString(response.getEntity());
        System.out.println(result);
        JSONObject jsonObject = new JSONObject(result);
        String user = jsonObject.get("user").toString();
        String id = new JSONObject(jsonObject.get("user").toString()).get("id").toString();
        System.out.println(id);
        Assert.assertEquals(id,"116894");
    }

}
