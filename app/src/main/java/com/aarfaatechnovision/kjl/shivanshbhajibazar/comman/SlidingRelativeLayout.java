package com.aarfaatechnovision.kjl.shivanshbhajibazar.comman;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;


/**
 * *************************************************************************
 *
 * @ClassdName:ImagePagerAdapter
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class use for custom RelativeLayout
 * <p/>
 * *************************************************************************
 */



public class SlidingRelativeLayout extends RelativeLayout {
    private float yFraction = 0;
    private float xFraction = 0;
    private ViewTreeObserver.OnPreDrawListener preDrawListener = null;

    public SlidingRelativeLayout(Context context) {
        super(context);
    }

    public SlidingRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingRelativeLayout(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
    }



    public void setAccordionPivotWidth(float fraction) {
        setScaleX(fraction);
        setPivotX(getWidth());
    }

    public void setTableHorizontalPivotZero(float fraction) {
        setRotationY(90 * fraction);
        setPivotX(0);
        setPivotY(getHeight() / 2);
    }

    public void setTableHorizontalPivotWidth(float fraction) {
        setRotationY(-90 * fraction);
        setPivotX(getWidth());
        setPivotY(getHeight() / 2);
    }

    public void setTableVerticalPivotZero(float fraction) {
        setRotationX(-90 * fraction);
        setPivotX(getWidth() / 2);
        setPivotY(0);
    }

    public void setTableVerticalPivotHeight(float fraction) {
        setRotationX(90 * fraction);
        setPivotX(getWidth() / 2);
        setPivotY(getHeight());
    }

    public void setZoomFromCornerPivotHG(float fraction) {
        setScaleX(fraction);
        setScaleY(fraction);
        setPivotX(getWidth());
        setPivotY(getHeight());
    }

    public void setZoomFromCornerPivotZero(float fraction) {
        setScaleX(fraction);
        setScaleY(fraction);
        setPivotX(0);
        setPivotY(0);
    }

    public void setZoomFromCornerPivotWidth(float fraction) {
        setScaleX(fraction);
        setScaleY(fraction);
        setPivotX(getWidth());
        setPivotY(0);
    }

    public void setZoomFromCornerPivotHeight(float fraction) {
        setScaleX(fraction);
        setScaleY(fraction);
        setPivotX(0);
        setPivotY(getHeight());
    }

    public void setZoomSlideHorizontal(float fraction) {
        setTranslationX(getWidth() * fraction);
        setPivotX(getWidth() / 2);
        setPivotY(getHeight() / 2);
    }

    public void setZoomSlideVertical(float fraction) {
        setTranslationY(getHeight() * fraction);
        setPivotX(getWidth() / 2);
        setPivotY(getHeight() / 2);
    }
}