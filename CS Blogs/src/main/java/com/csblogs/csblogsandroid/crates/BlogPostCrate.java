package com.csblogs.csblogsandroid.crates;

import android.content.Context;
import com.csblogs.csblogsandroid.api.payloads.BlogPost;
import uk.co.alexpringle.crate.Crate;

public class BlogPostCrate extends Crate<BlogPost>
{
    public BlogPostCrate(Context context)
    {
        super(context);
    }
}
