package com.example.epicness;

import android.widget.TextView;

public class AnimatedTextView {
    private final TextView textView;

    public AnimatedTextView(TextView textView) {this.textView = textView;}
    public String getText() {return textView.getText().toString();}
    public void setText(String text) {textView.setText(text);}
}