package com.example.greenpads;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.adforus.sdk.greenp.v3.GreenpReward;
import com.adforus.sdk.greenp.v3.OfferwallBuilder;
import com.adforus.sdk.greenp.v3.ui.banner.GreenpBanner;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private final String appUniqKey = "ZBhFaS5kxE";

    private String appUserId = "someUser13";

    private EditText appUid;

    private LinearLayout bannerWrapper;
    private LinearLayout miniBannerWrapper;

    private Switch fontSwitch;
    public static Drawable mCoinImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bannerWrapper = findViewById(R.id.banner_wrapper);
        miniBannerWrapper = findViewById(R.id.container);

        (findViewById(R.id.btn_init)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initOfferwall();
            }
        });
        (findViewById(R.id.show_offerwall)).setOnClickListener(this);
        (findViewById(R.id.show_popup)).setOnClickListener(this);
        (findViewById(R.id.req_320x50)).setOnClickListener(this);
        (findViewById(R.id.req_mini)).setOnClickListener(this);
        (findViewById(R.id.req_fragment)).setOnClickListener(this);

        fontSwitch = findViewById(R.id.switch_font);
        appUid = findViewById(R.id.app_uid);
    }

//    private static void scaleImage (Context context) {
//
//        int px = ScreenUtil.dpToPixel(context, 15);
//
//        Bitmap b = ((BitmapDrawable) mCoinImg).getBitmap();
//        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, px, px, false);
//        mCoinImg = new BitmapDrawable(context.getResources(), bitmapResized);
//    }

    @Override
    public void onClick(View view) {

        OfferwallBuilder builder = GreenpReward.getOfferwallBuilder();
        if(builder == null) {
            return;
        }

        builder.setAppUniqKey(appUniqKey); // 매체고유키
        builder.setUseGreenpFontStyle(!fontSwitch.isChecked());

        switch (view.getId()) {

            case R.id.show_offerwall:
                builder.showOfferwall(MainActivity.this);

                break;

            case R.id.show_popup:
                builder.requestBanner(MainActivity.this, OfferwallBuilder.BANNER_POPUP, new OfferwallBuilder.OnRequestBannerListener() {
                    @Override
                    public void onResult(boolean b, String s, GreenpBanner banner) {
                        if(b) {
                            banner.showPopupBanner();
                        } else {
                            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            case R.id.req_320x50:

                if(bannerWrapper.getChildCount() > 0) {
                    bannerWrapper.removeAllViews();
                }

                builder.requestBanner(MainActivity.this, OfferwallBuilder.BANNER_320x50, new OfferwallBuilder.OnRequestBannerListener() {
                    @Override
                    public void onResult(boolean b, String s, GreenpBanner banner) {
                        if(b) {
                            bannerWrapper.addView(banner.getView());
                        } else {
                            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.req_mini:

                if(miniBannerWrapper.getChildCount() > 0) {
                    miniBannerWrapper.removeAllViews();
                }

                builder.requestBanner(MainActivity.this, OfferwallBuilder.BANNER_MINI, new OfferwallBuilder.OnRequestBannerListener() {
                    @Override
                    public void onResult(boolean b, String s, GreenpBanner banner) {
                        if(b) {
                            miniBannerWrapper.addView(banner.getView());
                        } else {
                            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;

            case R.id.req_fragment:

                if(miniBannerWrapper.getChildCount() > 0) {
                    miniBannerWrapper.removeAllViews();
                }

                builder.requestBanner(MainActivity.this, OfferwallBuilder.BANNER_FRAGMENT, new OfferwallBuilder.OnRequestBannerListener() {
                    @Override
                    public void onResult(boolean b, String s, GreenpBanner banner) {
                        if(b) {

                            Fragment fragment = banner.getFragment();

                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container, fragment, fragment.getTag());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commitAllowingStateLoss();

                        } else {
                            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finish();
    }

    private void initOfferwall() {

        appUserId = appUid.getText().toString();
        if(appUserId.isEmpty()) {
            appUserId = "someUser13";
        }

        GreenpReward.init(MainActivity.this, "ZBhFaS5kxE", appUserId, new GreenpReward.OnGreenpRewardListener() {
            @Override
            public void onResult(boolean result, String msg) {

                if(result) {
                    Toast.makeText(getBaseContext(), "SDK가 초기화 되었습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "SDK가 초기화 되지 않았습니다.", Toast.LENGTH_LONG).show();
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
