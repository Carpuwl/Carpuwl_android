package Button;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.rperryng.robotofonts.Typefaces;

public class RobotoRegular extends Button {
    public RobotoRegular(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            setTypeface(Typefaces.get(context, Typefaces.ROBOTO_REGULAR));
        }
    }
}