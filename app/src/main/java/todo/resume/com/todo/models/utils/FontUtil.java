package todo.resume.com.todo.models.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Jason on 1/3/2017.
 */

public class FontUtil {

    public enum RobotoFont {
        NORMAL,
        BOLD,
        THIN
    }

    /**
     * returns normal roboto font as default typeface
     * @param context your context
     * @param font typeface enum
     * @return
     */

    public static Typeface getFont(Context context, RobotoFont font) {
        Typeface normal = Typeface.createFromAsset(context.getAssets(), "fonts/roboto.ttf");
        Typeface bold = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_bold.ttf");
        Typeface thin = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_thin.ttf");

        switch (font) {
            case NORMAL:
                return normal;
            case BOLD:
                return bold;
            case THIN:
                return thin;
            default: return normal;
        }
    }
}