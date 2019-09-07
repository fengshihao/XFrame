package com.fengshihao.xframework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fengshihao.calculator.ICalculator;
import com.fengshihao.xframe.XFrame;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private TextView mSumView;
    private EditText mNumView1;
    private EditText mNumView2;

    //只依赖接口
    private ICalculator mCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNumView1 = findViewById(R.id.edit_num1);
        mNumView2 = findViewById(R.id.edit_num2);
        mSumView = findViewById(R.id.view_sum);

      mCalculator =  XFrame.getInstance().getModule(ICalculator.class);

        findViewById(R.id.btn_sum).setOnClickListener(view -> {
            try {
                float num1 = Float.valueOf(mNumView1.getText().toString());
                float num2 = Float.valueOf(mNumView2.getText().toString());
                float sum = mCalculator.sum(num1, num2); // 只有这里是业务逻辑其他的都是UI
                mSumView.setText(String.valueOf(sum));
            } catch (Exception err) {
                Log.e(TAG, "onClick: ", err);
                Toast.makeText(MainActivity.this, "wrong input ", Toast.LENGTH_LONG).show();
            }
        });


        findViewById(R.id.btn_async).setOnClickListener(view ->
                mCalculator.asyncWork("hello async work done!"));

        mCalculator.getListeners().addListener((errorCode, msg) -> {
            Log.d(TAG, "onGetAsyncWorkResult: errorCode=" + errorCode + " msg=" + msg
                    + " thread=" + Thread.currentThread().getId());
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCalculator.getListeners().clean();
    }
}
