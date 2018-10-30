package com.fengshihao.xframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fengshihao.calculator.Calculator;
import com.fengshihao.calculator.ICalculator;
import com.fengshihao.calculator.ICalculatorListener;

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

        mCalculator = Calculator.createCalculator();

        findViewById(R.id.btn_sum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    float num1 = Float.valueOf(mNumView1.getText().toString());
                    float num2 = Float.valueOf(mNumView2.getText().toString());
                    float sum = mCalculator.sum(num1, num2); // 只有这里是业务逻辑其他的都是UI
                    mSumView.setText(String.valueOf(sum));
                } catch (Exception err) {
                    Log.e(TAG, "onClick: ", err);
                    Toast.makeText(MainActivity.this, "wrong input ", Toast.LENGTH_LONG).show();
                }
            }
        });


        findViewById(R.id.btn_async).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalculator.asyncWork("hello async work done!");
            }
        });

        mCalculator.setCalculatorListener(new ICalculatorListener() {
            @Override
            public void onGetAsyncWorkResult(int errorCode, final String msg) {
                Log.d(TAG, "onGetAsyncWorkResult: errorCode=" + errorCode + " msg=" + msg);
                //因为异步的回调是在非UI线程,所以到了这里需要做线程切换
                //切换线程后需要检查是否activity还存在,这里省略
                //所有的这些不便利. 线程切换, 检查activity, 多个listener管理. 
                //都可以用XListener这个工具来简化
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
