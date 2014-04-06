package Button;

import com.rperryng.robotofonts.Typefaces;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class RobotoLight extends Button {
    public RobotoLight(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            setTypeface(Typefaces.get(context, Typefaces.ROBOTO_LIGHT));
        }
    }
}