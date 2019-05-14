package com.dazhi.libroot.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.dazhi.libroot.R;
import com.dazhi.libroot.root.RootSimpActivity;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * 功能：定制化扫描界面
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/5/18 10:33
 * 修改日期：2018/5/18 10:33
 */
@Route(path = "/root/ScanActivity")
public class RootScanActivity extends RootSimpActivity implements View.OnClickListener {
    private Button btLibScanEsc, btLibScanLight;
    private boolean booLight = false; //默认闪光灯是关闭的

    @Override
    protected int getLayoutId() {
        return R.layout.libroot_activity_scan;
    }

    @Override
    protected void initConfig(TextView tvToolTitle) {

    }

    @Override
    protected void initViewAndDataAndEvent() {
        //初始化view
        btLibScanEsc = findViewById(R.id.btLibScanEsc);
        btLibScanEsc.setOnClickListener(this);
        btLibScanLight = findViewById(R.id.btLibScanLight);
        btLibScanLight.setOnClickListener(this);
        //
        initLibScan();
    }

    @Override
    public void onClick(View v) {
        int intId=v.getId();
        if(intId==R.id.btLibScanEsc){
            finish();
            return;
        }
        if(intId==R.id.btLibScanLight){
            booLight = !booLight; //取反
            if (booLight) {
                //开灯
                CodeUtils.isLightEnable(true);
                btLibScanLight.setText(R.string.libroot_scan_lightoff);
            } else {
                //关灯
                CodeUtils.isLightEnable(false);
                btLibScanLight.setText(R.string.libroot_scan_lighton);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭闪光灯
        CodeUtils.isLightEnable(false);
    }

    private void initLibScan() {
        //执行扫面Fragment的初始化操作
        CaptureFragment captureFragment = new CaptureFragment();
        //为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.libroot_scanframe);
        captureFragment.setAnalyzeCallback(new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
                bundle.putString(CodeUtils.RESULT_STRING, result);
                resultIntent.putExtras(bundle);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
            @Override
            public void onAnalyzeFailed() {
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
                bundle.putString(CodeUtils.RESULT_STRING, "");
                resultIntent.putExtras(bundle);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        //替换我们的扫描控件
        getSupportFragmentManager().beginTransaction().replace(R.id.flLibScanContainer, captureFragment).commit();
    }


}
