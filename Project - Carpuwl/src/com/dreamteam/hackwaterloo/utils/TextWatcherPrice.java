package com.dreamteam.hackwaterloo.utils;

import java.text.DecimalFormat;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
            
            Log.d("ryan", "edittext is : " + mEditText.toString());
            String formatted = new DecimalFormat("##.##").format(Double.parseDouble(s.toString()));
            mEditText.setText(formatted);
            mEditText.setSelection(formatted.length());
            
            mEditText.addTextChangedListener(this);
        }
    }
}
