package com.zk.test.bannerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private BannerView bannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bannerView=findViewById(R.id.banner_view);

        bannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                if (convertView == null) {
                    convertView = new ImageView(MainActivity.this);
                }
                ((ImageView) convertView).setScaleType(ImageView.ScaleType.CENTER_CROP);


                ((ImageView) convertView).setImageResource(R.drawable.beautiful_grils);
//                Glide.with(MainActivity.this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511876582765&di=dd4b1b48a01bb7e310bd0915757d5e4e&imgtype=0&src=http%3A%2F%2Fmmphoto1.91555.com%2FmmUpload%2Fphotos%2F520538%2F201168172952182.jpg").into((ImageView) convertView);
                return convertView;
            }

            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public String getBannerDesc(int position) {
                return "啦啦啦";
            }
        });
        bannerView.setOnBannerItemClickListener(new BannerViewPager.BannerItemClickListener() {
            @Override
            public void click(int position) {
                Toast.makeText(MainActivity.this,"你点了第"+position+"个，但都是一样的",Toast.LENGTH_LONG).show();
            }
        });
    }
}
