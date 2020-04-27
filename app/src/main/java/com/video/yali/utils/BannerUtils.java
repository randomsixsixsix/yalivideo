package com.video.yali.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stx.xhb.xbanner.XBanner;
import com.video.yali.GlobConstant;
import com.video.yali.Neturl;
import com.video.yali.R;
import com.video.yali.bean.BannerBean;
import com.video.yali.ui.activity.WebActivity;
import com.video.yali.ui.activity.colum.ColumnListActivity;
import com.video.yali.ui.activity.home.VideoDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;
import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class BannerUtils {

    public static void initBanner(Activity context, XBanner xbanner, List<BannerBean> data, boolean isRoundcorner, LinearLayout llPoints) {

        // data.addAll(data);

        List<String> imageUrls = new ArrayList<>();
        llPoints.removeAllViews();
        for (int i = 0; i < data.size(); i++) {

            final String url = data.get(i).getCorver();
            if (!TextUtils.isEmpty(url)) {
                imageUrls.add(url);
            }

            View bannerview = LayoutInflater.from(context).inflate(R.layout.img_indicator, null);
            ImageView imageView = bannerview.findViewById(R.id.img_points);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            if (i == 0) {
                layoutParams.width = SizeUtils.dp2px(24);
                layoutParams.height = SizeUtils.dp2px(7);
                imageView.setSelected(true);
            } else {
                layoutParams.width = SizeUtils.dp2px(8);
                layoutParams.height = SizeUtils.dp2px(8);
                imageView.setSelected(false);
            }

            imageView.setLayoutParams(layoutParams);
            llPoints.addView(bannerview);

        }

        if (data.size() > 1) {
            //只有一张图片时,不显示指示器
            llPoints.setVisibility(View.VISIBLE);
        } else {
            llPoints.setVisibility(View.GONE);
        }


        xbanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {

                ImageView bannerimg = view.findViewById(R.id.banner_img);
              // LogUtils.e("轮播图片:" + (String) model);
                if (isRoundcorner) {


                    Glide.with(context)
                            .load((String) model)
                            .apply(centerCropTransform()
                                    .placeholder(R.drawable.default_cover_xhsp)
                                    .error(R.drawable.default_cover_xhsp)
                                    .priority(Priority.HIGH)
                                    .transform(new GlideRoundTransform(context, 10))
                                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                    .skipMemoryCache(false))
                            .into(bannerimg);
                } else {


                    Glide.with(context)
                            .load((String) model)
                            .apply(centerCropTransform()
                                    .placeholder(R.drawable.default_cover_xhsp)
                                    .error(R.drawable.default_cover_xhsp)
                                    .priority(Priority.HIGH)
                                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                    .skipMemoryCache(false))
                            .into(bannerimg);
                }

            }
        });
        xbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {


                BannerBean bannerBean = data.get(position);
                String target_url = bannerBean.getLinkurl();
                int type = bannerBean.getType();
                switch (type) {
                    case 1:
                        Intent webIntent = new Intent(context, WebActivity.class);
                        webIntent.putExtra("webType", ConstantUtils.adtype_banner);
                        webIntent.putExtra("webUrl", target_url);
                        ActivityUtils.startActivity(webIntent);
                        break;
                    case 2:
                        Intent intent3 = new Intent();
                        intent3.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(target_url);
                        intent3.setData(content_url);
                        ActivityUtils.startActivity(intent3);
                        break;
                    case 3://电影
                        Intent intent = new Intent(context, VideoDetailsActivity.class);
                        intent.putExtra(GlobConstant.VIDEOID, Integer.parseInt(target_url));
                        startActivity(intent);
                        break;
                    case 4://专题
                        Intent intent2 = new Intent(context, ColumnListActivity.class);
                        intent2.putExtra("id",Integer.parseInt(target_url));
                        startActivity(intent2);
                        break;
                }
            }
        });

        xbanner.setData(R.layout.item_banner, imageUrls, null);

        xbanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {


                for (int i1 = 0; i1 < llPoints.getChildCount(); i1++) {
                    ImageView imageView = llPoints.getChildAt(i1).findViewById(R.id.img_points);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();

                    if (i1 == i) {
                        layoutParams.width = SizeUtils.dp2px(24);
                        layoutParams.height = SizeUtils.dp2px(7);
                        imageView.setSelected(true);

                    } else {
                        layoutParams.width = SizeUtils.dp2px(8);
                        layoutParams.height = SizeUtils.dp2px(8);
                        imageView.setSelected(false);
                    }

                    imageView.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        // xbanner.startAutoPlay();

    }
}
