package com.csblogs.csblogsandroid.api.payloads;

import uk.co.alexpringle.crate.HasId;

import java.util.Date;

public class BlogPost implements HasId
{
    private String _id;
    private String userProvider;
    private String userId;
    private String title;
    private String imageUrl;
    private String summary;
    private Date pubDate;
    private Date updateDate;
    private String link;
    private Author author;

    public BlogPost()
    {

    }

    public String get_id()
    {
        return _id;
    }

    public void set_id(String _id)
    {
        this._id = _id;
    }

    public String getUserProvider()
    {
        return userProvider;
    }

    public void setUserProvider(String userProvider)
    {
        this.userProvider = userProvider;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public Date getPubDate()
    {
        return pubDate;
    }

    public void setPubDate(Date pubDate)
    {
        this.pubDate = pubDate;
    }

    public Date getUpdateDate()
    {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public Author getAuthor()
    {
        return author;
    }

    public void setAuthor(Author author)
    {
        this.author = author;
    }

    @Override
    public String getId()
    {
        return get_id();
    }
}
