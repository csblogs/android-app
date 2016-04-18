package com.csblogs.csblogsandroid.api.payloads;

import uk.co.alexpringle.crate.HasId;

import java.util.Date;

public class BlogPost implements HasId
{
    private String id;
    private String title;
    private String image_uri;
    private String description;
    private Date date_published;
    private Date date_updated;
    private String link;
    private String author_id;

    public BlogPost()
    {

    }

    @Override
    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getImage_uri()
    {
        return image_uri;
    }

    public void setImage_uri(String image_uri)
    {
        this.image_uri = image_uri;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getDate_published()
    {
        return date_published;
    }

    public void setDate_published(Date date_published)
    {
        this.date_published = date_published;
    }

    public Date getDate_updated()
    {
        return date_updated;
    }

    public void setDate_updated(Date date_updated)
    {
        this.date_updated = date_updated;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }
}
