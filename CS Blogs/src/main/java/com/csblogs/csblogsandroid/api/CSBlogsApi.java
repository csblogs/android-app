package com.csblogs.csblogsandroid.api;

import com.csblogs.csblogsandroid.api.payloads.BlogPost;
import com.csblogs.csblogsandroid.api.payloads.Blogger;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.List;

public interface CSBlogsApi
{
    String API_ENDPOINT_BASE = "https://newapi.csblogs.com/";
    String API_VERSION_STRING ="v2.0";
    String API_ENDPOINT_URL = API_ENDPOINT_BASE + API_VERSION_STRING;

    String API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    @GET("/posts")
    void getBlogs(@Query("page") int page,@Query("page_size") int pageSize, Callback<List<BlogPost>> blogsResponseCallback);

    @GET("/users")
    void getBloggers(Callback<List<Blogger>> bloggersResponseCallback);
}
