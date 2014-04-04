package com.dreamteam.hackwaterloo.utils;

import java.text.NumberFormat;
import java.util.Locale;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TextWatcherPrice implements TextWatcher {
    
    private EditText mEditText;
    private String current;
    
    public TextWatcherPrice(EditText editText) {
        mEditText = editText;
        current = "";
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals(current)) {
            mEditText.removeTextChangedListener(this);

            String cleanString = s.toString().replaceAll("[$,. ]", "");

            double parsed = Double.parseDouble(cleanString);
            String formatted = NumberFormat.getCurrencyInstance(Locale.CANADA).format((parsed/100));

            current = formatted;
            mEditText.setText(formatted);
            mEditText.setSelection(formatted.length());

            mEditText.addTextChangedListener(this);
         }
    }

}