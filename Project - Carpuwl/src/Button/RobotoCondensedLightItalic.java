package Button;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.dreamteam.hackwaterloo.Typefaces;

public class RobotoCondensedLightItalic extends Button {
    public RobotoCondensedLightItalic(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            setTypeface(Typefaces.get(context, Typefaces.ROBOTO_CONDENSED_LIGHT_ITALIC));
        }
    }
}