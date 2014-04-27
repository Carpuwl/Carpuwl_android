
package com.rperryng.roboto;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;

import com.rperryng.roboto.common.RobotoFont;
import com.rperryng.roboto.common.RobotoUtils;

/**
 * An extended {@link EditText} that uses the desired Roboto font
 * 
 * @author Ryan Perry-Nguyen
 */
public class RobotoEditText extends EditText {

    private RobotoFont mRobotoFont;

    /**
     * Use this constructor when creating a {@link RobotoEditText}
     * programmatically
     * 
     * @param context
     * @param robotoFont
     */
    public RobotoEditText(Context context, RobotoFont robotoFont) {
        super(context);
        mRobotoFont = robotoFont;
        setRoboto();
    }

    /**
     * Called when the view is inflated from an xml file
     */
    public RobotoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRobotoFromAttributeSet(attrs);
    }

    /**
     * Called when the view is inflated from an xml file with a style attached
     */
    public RobotoEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setRobotoFromAttributeSet(attrs);
    }

    /**
     * @param attrs The attribute set the font value is set in
     */
    private void setRobotoFromAttributeSet(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RobotoEditText);
        int attributeValue = a.getInteger(R.styleable.RobotoEditText_font,
                RobotoFont.DEFAULT_ATTRIBUTE_VALUE);
        mRobotoFont = RobotoFont.getRobotoFontForAttributeValue(attributeValue);
        a.recycle();
        setRoboto();
    }

    /**
     * Sets the current instance to the desired Roboto font
     */
    private void setRoboto() {
        if (!isInEditMode()) {
            setTypeface(RobotoUtils.getTypeface(getContext(), mRobotoFont));
        }
    }

    /**
     * Set this View's typeface to the desired RobotoFont
     * 
     * @param The new RobotoFont to set this view's Typeface to.
     */
    public void setRobotoFont(RobotoFont robotoFont) {
        mRobotoFont = robotoFont;
        setRoboto();
    }

    /**
     * @return The {@link RobotoFont} associated with this View
     */
    public RobotoFont getRobotoFont() {
        return mRobotoFont;
    }
}
