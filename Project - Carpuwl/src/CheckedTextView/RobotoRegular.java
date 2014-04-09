package CheckedTextView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

import com.dreamteam.hackwaterloo.Typefaces;

public class RobotoRegular extends CheckedTextView {
    public RobotoRegular(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            setTypeface(Typefaces.get(context, Typefaces.ROBOTO_REGULAR));
        }
    }
}