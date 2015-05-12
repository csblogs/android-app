package com.csblogs.csblogsandroid.api.payloads;

import java.util.List;

public class BlogsResponse
{
    private List<BlogPost> blogs;
    private int pageNumber;
    private boolean hasLess;
    private boolean hasMore;

    public BlogsResponse()
    {
    }

    public List<BlogPost> getBlogs()
    {
        return blogs;
    }

    public void setBlogs(List<BlogPost> blogs)
    {
        this.blogs = blogs;
    }

    public int getPageNumber()
    {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber)
    {
        this.pageNumber = pageNumber;
    }

    public boolean getHasLess()
    {
        return hasLess;
    }

    public void setHasLess(boolean hasLess)
    {
        this.hasLess = hasLess;
    }

    public boolean getHasMore()
    {
        return hasMore;
    }

    public void setHasMore(boolean hasMore)
    {
        this.hasMore = hasMore;
    }
}
