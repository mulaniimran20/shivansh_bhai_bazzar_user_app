package com.aarfaatechnovision.kjl.shivanshbhajibazar.customecomponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.util.FontCache;


public class CustomButton extends AppCompatButton {

    private Typeface fontType;



    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode())
            return;
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String fontName = ta.getString(R.styleable.CustomTextView_font_name);
        if (fontName != null) {
            final Typeface font = FontCache.getInstance().getFont(context, "fonts/" + fontName);
            setTypeface(font);
        }
        ta.recycle();
    }


}