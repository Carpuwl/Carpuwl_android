package TextView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dreamteam.hackwaterloo.Typefaces;

public class RobotoCondensedRegular extends TextView {
    public RobotoCondensedRegular(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            setTypeface(Typefaces.get(context, Typefaces.ROBOTO_CONDENSED_REGULAR));
        }
    }
}
