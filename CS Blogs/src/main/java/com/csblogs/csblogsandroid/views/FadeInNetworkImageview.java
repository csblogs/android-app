package com.csblogs.csblogsandroid.views;

/**
 * Based on: https://gist.github.com/benvd/5683818
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import com.android.volley.toolbox.NetworkImageView;

public class FadeInNetworkImageview extends NetworkImageView
{
    public FadeInNetworkImageview(Context context) {
        super(context);
    }

    public FadeInNetworkImageview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FadeInNetworkImageview(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setImageBitmap(Bitmap bm)
    {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getContext().getResources(),bm);
        Drawable transparentDrawable = new ColorDrawable(android.R.color.transparent);
        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{transparentDrawable,bitmapDrawable});

        setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(250);
    }
}
