
package com.rperryng.roboto.common;

import java.util.Locale;

import android.text.TextUtils;

import com.rperryng.roboto.R;

/**
 * An enum containing the resource ID's of all the roboto fonts.
 * 
 * @author Ryan Perry-Nguyen
 */
public enum RobotoFont {

    // I would use the ordinal values, but if you decide to omit one of the
    // fonts to save space, then the ordinal values no longer match the
    // attribute values as defined in the attrs.xml file. and since the value
    // field for a styleable enum doesn't allow you to reference xml integers,
    // there is no 'one location' where I can define the attribute values.
    REGULAR(R.raw.rv__regular, 0),
    REGULAR_BOLD(R.raw.rv__regular_bold, 1),
    REGULAR_ITALIC(R.raw.rv__regular_italic, 2),
    REGULAR_BOLD_ITALIC(R.raw.rv__regular_bold_italic, 3),

    MEDIUM(R.raw.rv__medium, 4),
    MEDIUM_ITALIC(R.raw.rv__medium_italic, 5),

    BLACK(R.raw.rv__black, 6),
    BLACK_ITALIC(R.raw.rv__black_italic, 7),

    LIGHT(R.raw.rv__light, 8),
    LIGHT_ITALIC(R.raw.rv__light_italic, 9),

    THIN(R.raw.rv__thin, 10),
    THIN_ITALIC(R.raw.rv__thin_italic, 11),

    CONDENSED_REGULAR(R.raw.rv__condensed_regular, 12),
    CONDENSED_REGULAR_BOLD(R.raw.rv__condensed_regular_bold, 13),
    CONDENSED_REGULAR_ITALIC(R.raw.rv__condensed_regular_italic, 14),
    CONDENSED_REGULAR_BOLD_ITALIC(R.raw.rv__condensed_regular_bold_italic, 15),

    CONDENSED_LIGHT(R.raw.rv__condensed_light, 16),
    CONDENSED_LIGHT_ITALIC(R.raw.rv__condensed_light_italic, 17);

    public static final int DEFAULT_ATTRIBUTE_VALUE = 0;
    private int mRawFontResourceId;
    private int mAttributeValue;

    private RobotoFont(int rawFontResourceId, int attributeValue) {
        mRawFontResourceId = rawFontResourceId;
        mAttributeValue = attributeValue;
    }

    /**
     * @return The related .ttf resource id this Roboto Font points to
     */
    public int getRawFontResourceId() {
        return mRawFontResourceId;
    }

    /**
     * @return The attribute value this RobotoFont has
     */
    public int getAttributeValue() {
        return mAttributeValue;
    }

    /**
     * Returns the RobotoFont with the matching AttributeValue
     * 
     * @param attributeValue The attribute value as mapped in the attrs file
     * @return {@link RobotoFont}, or an IllegalArgumentException if there is no
     *         RobotoFont with this attributeValue
     */
    public static RobotoFont getRobotoFontForAttributeValue(int attributeValue) {
        for (RobotoFont robotoFont : RobotoFont.values()) {
            if (robotoFont.getAttributeValue() == attributeValue) {
                return robotoFont;
            }
        }
        throw new IllegalArgumentException("No RobotoFont found with attribute value: "
                + attributeValue);
    }

    // Looks like "Roboto Condensed Light Italic"
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] splitString = TextUtils.split(("Roboto " + this.name().replace("_", " ")), " ");

        for (String target : splitString) {
            stringBuilder
                    .append(Character.toUpperCase(target.charAt(0)))
                    .append(target.substring(1).toLowerCase(Locale.ENGLISH))
                    .append(" ");
        }

        return stringBuilder.toString();
    }

}
