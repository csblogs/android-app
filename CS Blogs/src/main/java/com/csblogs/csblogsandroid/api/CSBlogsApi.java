package com.csblogs.csblogsandroid.api;

import com.csblogs.csblogsandroid.api.payloads.BlogsResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface CSBlogsApi
{
    @GET("/blogs")
    void getBlogs(@Query("page") int page,@Query("limit") int limit, Callback<BlogsResponse> blogsResponseCallback);
}
