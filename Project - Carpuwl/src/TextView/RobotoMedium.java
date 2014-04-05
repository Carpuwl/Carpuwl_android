package TextView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dreamteam.hackwaterloo.Typefaces;

public class RobotoMedium extends TextView {
    public RobotoMedium(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            setTypeface(Typefaces.get(context, Typefaces.ROBOTO_MEDIUM));
        }
    }
}
