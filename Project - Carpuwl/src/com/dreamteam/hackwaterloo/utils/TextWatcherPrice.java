package com.dreamteam.hackwaterloo.utils;

import android.annotation.SuppressLint;
import android.text.TextWatcher;
import java.text.NumberFormat;
import java.util.Locale;
import android.text.Editable;
import android.widget.EditText;

@SuppressLint("DefaultLocale")
public class TextWatcherPrice implements TextWatcher {
    
    private static final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
    
    private EditText mEditText;
    private String mOldValue;

    public TextWatcherPrice(EditText editText) {
        mEditText = editText;
        mOldValue = "";
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!s.toString().equals(mOldValue)) {
            String cleanString = s.toString().replace(".", "");
            double value = Double.parseDouble(cleanString);
            String formatted = numberFormat.format(value / 100);
            formatted = formatted.replace("$", "");
            mOldValue = formatted;
            mEditText.setText(formatted);
            mEditText.setSelection(formatted.length());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // No implementation
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // No implementation
    }
}
