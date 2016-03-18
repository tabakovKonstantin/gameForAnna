package ru.nsu.ccfit.tabakov.gameforanna;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Константин on 13.08.2015.
 */
public class Answer extends Activity {

    private TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer);
        initComponents();
    }

    private void initComponents() {
        textView = (TextView) findViewById(R.id.textView2);
        String answer = (String) getIntent().getSerializableExtra("answer");
        textView.setText(answer);
    }
}
