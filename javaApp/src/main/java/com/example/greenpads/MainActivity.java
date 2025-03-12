package com.example.greenpads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.adforus.sdk.greenp.v3.GreenpReward;
import com.adforus.sdk.greenp.v3.OfferwallBuilder;
import com.adforus.sdk.greenp.v3.ui.banner.GreenpBanner;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String appUserId = "someUser";
    private final String appUniqKey = "GreenpOfferwall"; // 매체고유키
    private final String appCode = "ZBhFaS5kxE";

    private LinearLayout miniBannerWrapper;

    private GreenpReward greenpReward;
    private Boolean isInitialize = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String packageName = com.adforus.sdk.adsu.UAdConstant.UAD_PACKAGE_NAME;
        SharedPreferences sharedPreferences = this.getSharedPreferences(packageName, Context.MODE_PRIVATE);
        String result = sharedPreferences.getString("debug", "false");
        Log.d("test-jennet", "SharedPreferences debug mode result : "+ result);

        miniBannerWrapper = findViewById(R.id.container);

        (findViewById(R.id.show_offerwall)).setOnClickListener(this);
        (findViewById(R.id.req_fragment)).setOnClickListener(this);

        initOfferwall();
    }

    @Override
    public void onClick(View view) {

        if(!isInitialize){
            return;
        }
        OfferwallBuilder builder = greenpReward.createOfferwallBuilder();
        builder.setAppUniqKey(appUniqKey);
        builder.setUseGreenpFontStyle(true); // 그린피 폰트 사용여부 ( default : false )

        if(view.getId() == R.id.show_offerwall) {
            builder.showOfferwall(MainActivity.this);
        } else if(view.getId() == R.id.req_fragment) {
            builder.requestBanner(MainActivity.this, OfferwallBuilder.BANNER_FRAGMENT, new OfferwallBuilder.OnRequestBannerListener() {
                @Override
                public void onResult(boolean b, String s, GreenpBanner banner) {
                    if(b) {
                        if(miniBannerWrapper.getChildCount() > 0) {
                            miniBannerWrapper.removeAllViews();
                        }

                        Fragment fragment = banner.getFragment();
                        if(fragment == null)
                            return;

                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment, "");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commitAllowingStateLoss();

                    } else {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initOfferwall() {

        /**greenp_v3-debug.aar
         * @params
         *   - Context context
         *   - String appCode ( 발급받은 매체 코드 )
         *   - String userId ( 매체사 유저 아이디 )
         *   - OnAdbcRewardListener initListener
         * */
        greenpReward = new GreenpReward();
        greenpReward.initialize(MainActivity.this, appCode, appUserId, new GreenpReward.OnGreenpRewardListener() {
            @Override
            public void onResult(boolean result, String msg) {

                if(result) {
                    isInitialize = true;
                    Toast.makeText(getBaseContext(), "SDK가 초기화 되었습니다.", Toast.LENGTH_LONG).show();
                } else {
                    isInitialize = false;
                    Toast.makeText(getBaseContext(), "SDK가 초기화 되지 않았습니다.\n"+msg, Toast.LENGTH_LONG).show();
                    Log.e("tag", msg);
                }
            }
        });
    }

    /** 암호화 된 유저 ID 생성 예제 */
    private String encId() {

        //byte[] bytes = (appUserId + ("adid")).getBytes();
        byte[] bytes = appUserId.getBytes();

        Checksum crc = new CRC32();
        crc.update(bytes, 0, bytes.length);
        return String.format("%08x", crc.getValue());
    }
}