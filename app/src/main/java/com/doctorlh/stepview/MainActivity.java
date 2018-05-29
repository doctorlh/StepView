package com.doctorlh.stepview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StepView mStepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStepView = findViewById(R.id.step_view);

        List<StepInfo> stepInfos = new ArrayList<>();
        stepInfos.add(new StepInfo("注册登录", R.drawable.login, R.drawable.login));
        stepInfos.add(new StepInfo("进店签约", R.drawable.sign, R.drawable.sign_dis));
        stepInfos.add(new StepInfo("开通支付", R.drawable.pay, R.drawable.pay_dis));
        stepInfos.add(new StepInfo("生成二维码", R.drawable.qrcode, R.drawable.qrcode_dis));
        mStepView.setStepInfos(stepInfos);
        mStepView.setStep(2);
    }
}
