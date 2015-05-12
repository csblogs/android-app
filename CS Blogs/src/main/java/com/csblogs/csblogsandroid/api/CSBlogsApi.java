package com.csblogs.csblogsandroid.api;

import com.csblogs.csblogsandroid.api.payloads.BlogsResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface CSBlogsApi
{
    String API_ENDPOINT_BASE = "http://api.csblogs.com/";
    String API_VERSION_STRING ="v0.1";
    String API_ENDPOINT_URL = API_ENDPOINT_BASE + API_VERSION_STRING;

    String API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    @GET("/blogs")
    void getBlogs(@Query("page") int page,@Query("limit") int limit, Callback<BlogsResponse> blogsResponseCallback);
}
