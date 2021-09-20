package com.example.demoapplication.mvp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.demoapplication.R;


public class TextViewCustomFontBold extends TextView {
    public TextViewCustomFontBold(Context context) {
        super(context);
    }

    public TextViewCustomFontBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCustomTextView(context,attrs);
    }

    public TextViewCustomFontBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomTextView(context,attrs);
    }

    public TextViewCustomFontBold(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initCustomTextView(context,attrs);
    }

   /* private void initCustomTextView(Context context) {

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-Light.otf");
//            Typeface typeface = Typeface.createFromFile("font/poppins_light.ttf");//.createFromAsset(context.getAssets(), "poppins_light.ttf");
        setTypeface(typeface);
//        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/poppins_light.ttf");
//        Typeface typeface = FontCache.getTypeface("Poppins-Light.otf", context);

    }*/


    private void initCustomTextView(Context context, AttributeSet attrs) {


        try {


            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.CustomeFont,
                    0, 0);

            try {

                int value = a.getInt(R.styleable.CustomeFont_textFont, 0);

                switch (value) {
                    case 0:
                        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Muli.ttf");
                        setTypeface(typeface,Typeface.BOLD);
                        break;
                    case 1:
                        Typeface typeface1 = Typeface.createFromAsset(context.getAssets(), "fonts/Muli-Light.ttf");
                        setTypeface(typeface1,Typeface.BOLD);
                        break;
                    case 2:
                        Typeface typeface2 = Typeface.createFromAsset(context.getAssets(), "fonts/Muli-SemiBold.ttf");
                        setTypeface(typeface2,Typeface.BOLD);
                        break;
                }

            } finally {
                a.recycle();
            }


        } catch (Exception e) {
        }

    }

}