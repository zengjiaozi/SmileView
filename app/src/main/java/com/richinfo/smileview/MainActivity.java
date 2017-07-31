package com.richinfo.smileview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import view.SmileVeiw;

public class MainActivity extends AppCompatActivity {

    private LinearLayout backGround;
    private ImageView smileFace;
    private SeekBar seekBar;
    private SmileVeiw smileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backGround = (LinearLayout) findViewById(R.id.backGround);
        smileFace = (ImageView) findViewById(R.id.smileFace);
        seekBar = (SeekBar) findViewById(R.id.seekBar);


        smileView = (SmileVeiw) findViewById(R.id.smileView);
        smileView.setNum(50,50);

//         通过对seekbar 的监听  来改变imageview在LinearLayout中的显示效果

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
//                拿到 linearLayout中的laiyoutParmas 的参数
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) smileFace.getLayoutParams();
                layoutParams.bottomMargin = i*3;
                smileFace.setLayoutParams(layoutParams);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
