package com.fengshihao.xframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private TextView mSumView;
    private EditText mNumView1;
    private EditText mNumView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNumView1 = findViewById(R.id.edit_num1);
        mNumView2 = findViewById(R.id.edit_num2);
        mSumView = findViewById(R.id.view_sum);

        findViewById(R.id.btn_sum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float num1 = Float.valueOf(mNumView1.getText().toString());
                float num2 = Float.valueOf(mNumView2.getText().toString());
                mSumView.setText(String.valueOf(num1 + num2));
            }
        });
    }
}
