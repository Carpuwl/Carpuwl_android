package TextView;

import com.dreamteam.hackwaterloo.Typefaces;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class RobotoCondensedRegular extends TextView {
    public RobotoCondensedRegular(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            setTypeface(Typefaces.get(context, Typefaces.ROBOTO_CONDENSED_REGULAR));
        }
    }
}
