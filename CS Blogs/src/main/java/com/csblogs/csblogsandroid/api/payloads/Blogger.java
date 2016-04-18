package com.csblogs.csblogsandroid.api.payloads;

import uk.co.alexpringle.crate.HasId;

public class Blogger implements HasId
{
    private String id;
    private String profile_picture_uri;
    private String authentication_id;
    private String authentication_provider;
    private String first_name;
    private String last_name;
    private String blog_feed_uri;
    private String blog_uri;
    private String website_uri;

    public Blogger()
    {

    }

    @Override
    public String getId()
    {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfile_picture_uri()
    {
        return profile_picture_uri;
    }

    public void setProfile_picture_uri(String profile_picture_uri)
    {
        this.profile_picture_uri = profile_picture_uri;
    }

    public String getAuthentication_id()
    {
        return authentication_id;
    }

    public void setAuthentication_id(String authentication_id)
    {
        this.authentication_id = authentication_id;
    }

    public String getAuthentication_provider()
    {
        return authentication_provider;
    }

    public void setAuthentication_provider(String authentication_provider)
    {
        this.authentication_provider = authentication_provider;
    }

    public String getFirst_name()
    {
        return first_name;
    }

    public void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }

    public String getLast_name()
    {
        return last_name;
    }

    public void setLast_name(String last_name)
    {
        this.last_name = last_name;
    }

    public String getBlog_feed_uri()
    {
        return blog_feed_uri;
    }

    public void setBlog_feed_uri(String blog_feed_uri)
    {
        this.blog_feed_uri = blog_feed_uri;
    }

    public String getBlog_uri()
    {
        return blog_uri;
    }

    public void setBlog_uri(String blog_uri)
    {
        this.blog_uri = blog_uri;
    }

    public String getWebsite_uri()
    {
        return website_uri;
    }

    public void setWebsite_uri(String website_uri)
    {
        this.website_uri = website_uri;
    }

}
