package life.majiang.community.provider;

import java.io.IOException;

import com.alibaba.fastjson.JSON;

import org.springframework.stereotype.Component;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType jsonMediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(jsonMediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            System.out.println(str);
            return str.split("&")[0].split("=")[1];
            //return str;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("https://api.github.com/user?access_token=" + accessToken).build();

        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
           // System.out.println(str);
            GithubUser githubUser = JSON.parseObject(str, GithubUser.class);
            System.out.println(githubUser.getName());
            return githubUser;
        } catch (IOException e) {

        }

        return null;
    }

}