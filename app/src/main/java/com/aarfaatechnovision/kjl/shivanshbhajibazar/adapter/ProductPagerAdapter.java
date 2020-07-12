package com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.util.List;

public class ProductPagerAdapter extends PagerAdapter
{
    Context context;
    private List<String> imgList;
    LayoutInflater layoutInflater;

    public ProductPagerAdapter(Context context, List<String> imgList) {
        this.context = context;
        this.imgList = imgList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.row_productdetails_slider, container, false);
        ImageView imageView = itemView.findViewById(R.id.ivCode);
        System.out.println(imgList.get(position));

        Picasso.with(context).load(imgList.get(position)).into(imageView);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
