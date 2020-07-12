package com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.HomeCategoryFragment;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.product.ProductListModel;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;


public class HomeProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener {

    private List<ProductListModel> productModelList;
    private OnItemClickListener onItemClickListener;
    private Context mContext;
    private HomeCategoryFragment productListFragment;

    public HomeProductListAdapter(final Context context, final List<ProductListModel> items,
                                  final HomeCategoryFragment fragment) {
        this.productModelList = items;
        this.mContext = context;
        this.productListFragment = fragment;

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_productlist, parent, false);
        v.setOnClickListener(this);

        return new ViewHolderData(v);

    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderData) holder).bindData(productModelList.get(position), position);
    }


    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    @Override
    public void onClick(final View v) {
        // Give some time to the ripple to finish the effect
        if (onItemClickListener != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onItemClickListener.onItemClick(v, (ProductListModel) v.getTag());
                }
            }, 200);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ProductListModel viewModel);


    }


    /**
     * bind ViewHolder
     */

    protected class ViewHolderData extends RecyclerView.ViewHolder {

        private ImageView ivProImg;
        private TextView textviewName;
        public ViewHolderData(View itemView) {
            super(itemView);
            textviewName = itemView.findViewById(R.id.textviewName);
            ivProImg = (ImageView) itemView.findViewById(R.id.row_home_productlist_ivProImg);
        }


        public void bindData(final ProductListModel item, final int position)
        {
            textviewName.setText(item.getCategoryName());

            itemView.setTag(item);

            Picasso.with(mContext).load(item.getCaegoryImageUrl()).into(ivProImg);


            System.out.println(item.getCaegoryImageUrl());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {



                    // Give some time to the ripple to finish the effect
                    if (onItemClickListener != null) { new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onItemClickListener.onItemClick(v, item);
                            }
                        }, 200);
                    }



                }
            });

        }
    }
}
