package todo.resume.com.todo.views.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import todo.resume.com.todo.R;
import todo.resume.com.todo.models.utils.FontUtil;

import static android.content.ContentValues.TAG;

/**
 * Created by Jason on 1/3/2017.
 */

public class RobotoEditText extends EditText {

    public RobotoEditText(Context context) {
        super(context);
    }

    public RobotoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public RobotoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs){
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.RobotoEditText);
        String customFont = a.getString(R.styleable.RobotoEditText_font);
        setCustomFont(customFont);
        a.recycle();
    }

    private void setCustomFont(String font) {
        Typeface tf;
        if(font != null) {
            font = font.toUpperCase();
            if(font.equals(FontUtil.RobotoFont.NORMAL.toString()))
                tf = FontUtil.getFont(getContext(), FontUtil.RobotoFont.NORMAL);
            else if(font.equals(FontUtil.RobotoFont.BOLD.toString()))
                tf = FontUtil.getFont(getContext(), FontUtil.RobotoFont.BOLD);
            else if(font.equals(FontUtil.RobotoFont.THIN.toString()))
                tf = FontUtil.getFont(getContext(), FontUtil.RobotoFont.THIN);
            else tf = FontUtil.getFont(getContext(), FontUtil.RobotoFont.NORMAL);
            setTypeface(tf);
        } else Log.d(TAG, "setCustomFont(): font null");

    }
}
