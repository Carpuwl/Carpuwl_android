package com.dreamteam.hackwaterloo.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class TextWatcherPrice implements TextWatcher {
    
    private static final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
    private static final DecimalFormat decimalFormat = new DecimalFormat("##.##");
    
    private EditText mEditText;
    private String mContentString;

    public TextWatcherPrice(EditText editText) {
        mEditText = editText;
        mContentString = "";
    }

    @Override
    public void afterTextChanged(Editable s) {
        // No implementation
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // No implementation
    }

    // TODO: fix this 
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals(mContentString)) {
            mEditText.removeTextChangedListener(this);

            String cleanString = s.toString().replace(".", "");

            double parsed = Double.parseDouble(cleanString);
            String formatted = numberFormat.format(parsed / 100);
            
            formatted = formatted.replace("$", "");
            Log.d("ryan", "formatted: " + formatted);
            mContentString = formatted;
            mEditText.setText(formatted);
            mEditText.setSelection(formatted.length());

            mEditText.addTextChangedListener(this);
        }
    }
}
