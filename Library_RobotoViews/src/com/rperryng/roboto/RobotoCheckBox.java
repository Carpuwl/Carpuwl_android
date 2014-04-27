
package com.rperryng.roboto;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.rperryng.roboto.common.RobotoFont;
import com.rperryng.roboto.common.RobotoUtils;

/**
 * An extended {@link CheckBox} that uses the desired Roboto font
 * 
 * @author Ryan Perry-Nguyen
 */
public class RobotoCheckBox extends CheckBox {

    private RobotoFont mRobotoFont;

    /**
     * Use this constructor when creating a {@link RobotoCheckBox}
     * programmatically
     * 
     * @param context
     * @param robotoFont
     */
    public RobotoCheckBox(Context context, RobotoFont robotoFont) {
        super(context);
        mRobotoFont = robotoFont;
        setRoboto();
    }

    /**
     * Called when the view is inflated from an xml file
     */
    public RobotoCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRobotoFromAttributeSet(attrs);
    }

    /**
     * Called when the view is inflated from an xml file with a style attached
     */
    public RobotoCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setRobotoFromAttributeSet(attrs);
    }

    /**
     * @param attrs The attribute set the font value is set in
     */
    private void setRobotoFromAttributeSet(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RobotoCheckBox);
        int attributeValue = a.getInteger(R.styleable.RobotoCheckBox_font,
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
     * @return The {@link RobotoFont} associated with this view
     */
    public RobotoFont getRobotoFont() {
        return mRobotoFont;
    }
}
