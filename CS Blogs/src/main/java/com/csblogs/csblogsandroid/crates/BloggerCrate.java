package com.csblogs.csblogsandroid.crates;

import android.content.Context;
import com.csblogs.csblogsandroid.api.payloads.Blogger;
import uk.co.alexpringle.crate.Crate;

public class BloggerCrate extends Crate<Blogger>
{
    public BloggerCrate(Context context)
    {
        super(context);
    }
}
