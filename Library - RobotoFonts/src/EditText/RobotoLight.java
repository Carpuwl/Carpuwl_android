package EditText;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.rperryng.robotofonts.Typefaces;

public class RobotoLight extends EditText {
    public RobotoLight(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            setTypeface(Typefaces.get(context, Typefaces.ROBOTO_LIGHT));
        }
    }
}
