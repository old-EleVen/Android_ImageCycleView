package com.example.sy.mycycleview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.squareup.picasso.Picasso;

/**
 * Created by sy on 2016/5/25.
 */
public class ImageCycleActivity extends AppCompatActivity {
    private ImageCycleView imageCycleView;
    private int[] images;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagecycleactivity);
        final String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
                "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
                "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
                "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
                "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
        imageCycleView = (ImageCycleView)findViewById(R.id.imagecycleview);
        images = new int[]{R.drawable.icon_empty, R.drawable.icon_error, R.drawable.icon_point,R.drawable.icon_point_pre};
        imageCycleView.setImagesCount(imageUrls.length);
        imageCycleView.startImageTimerTask();
        imageCycleView.setOnImageCycleListener(new ImageCycleView.OnImageCycleListener() {
            @Override
            public void OnImageClick(ImageView view,int postion) {
                Snackbar.make(view,"click"+postion,Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void showImageView(ImageView view, int position) {
                //view.setImageResource(images[position]);
                Picasso.with(getApplicationContext()).load(imageUrls[position]).placeholder(R.drawable.icon_empty).into(view);
            }
        });
    }
}
