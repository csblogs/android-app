package com.csblogs.csblogsandroid.api.payloads;

import uk.co.alexpringle.crate.HasId;

public class Author implements HasId
{
    private String _id;
    private String avatarUrl;
    private String userId;
    private String userProvider;
    private String firstName;
    private String lastName;
    private String feedUrl;
    private String blogWebsiteUrl;
    private String websiteUrl;
    private String vanityUrl;
    private boolean validated;

    public Author()
    {

    }

    @Override
    public String getId()
    {
        return null;
    }

    public String get_id()
    {
        return _id;
    }

    public void set_id(String _id)
    {
        this._id = _id;
    }

    public String getAvatarUrl()
    {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserProvider()
    {
        return userProvider;
    }

    public void setUserProvider(String userProvider)
    {
        this.userProvider = userProvider;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getFeedUrl()
    {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl)
    {
        this.feedUrl = feedUrl;
    }

    public String getBlogWebsiteUrl()
    {
        return blogWebsiteUrl;
    }

    public void setBlogWebsiteUrl(String blogWebsiteUrl)
    {
        this.blogWebsiteUrl = blogWebsiteUrl;
    }

    public String getWebsiteUrl()
    {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl)
    {
        this.websiteUrl = websiteUrl;
    }

    public String getVanityUrl()
    {
        return vanityUrl;
    }

    public void setVanityUrl(String vanityUrl)
    {
        this.vanityUrl = vanityUrl;
    }

    public boolean isValidated()
    {
        return validated;
    }

    public void setValidated(boolean validated)
    {
        this.validated = validated;
    }
}
