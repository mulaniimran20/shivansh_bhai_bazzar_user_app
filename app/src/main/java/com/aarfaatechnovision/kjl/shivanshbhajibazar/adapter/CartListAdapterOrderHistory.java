package com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.customecomponent.CustomTextView;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.CartListFragment;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.OrderDetailsFragment;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.cart.CartlistModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartListAdapterOrderHistory extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CartlistModel> productModelList;
    private Context mContext;
    private OrderDetailsFragment orderListFragmentNew;
    CartListAdapter cartListAdapter;

    public CartListAdapterOrderHistory(final Context context, final List<CartlistModel> items,
                           final OrderDetailsFragment fragment) {
        this.productModelList = items;
        this.mContext = context;
        this.orderListFragmentNew = fragment;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart_list, parent, false);
        return new CartListAdapterOrderHistory.ViewHolderData(v);

    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CartListAdapterOrderHistory.ViewHolderData) holder).bindData(productModelList.get(position), position);
    }


    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    protected class ViewHolderData extends RecyclerView.ViewHolder {

        private CustomTextView tvProductName;
        private CustomTextView tvProductPrice;
        private CustomTextView tvKg;
        private CustomTextView tvTotalKg;
        private ImageView ivProImg;
        private ImageView ivPlus;
        private ImageView ivMins;
        private ImageView ivDelete;


        public ViewHolderData(View itemView) {
            super(itemView);

            tvProductName =  itemView.findViewById(R.id.row_cartlist_tvName);
            tvProductPrice =  itemView.findViewById(R.id.row_cartlist_tvPrice);
            tvTotalKg = (CustomTextView) itemView.findViewById(R.id.row_cartlist_tvTotalKg);
            tvKg = (CustomTextView) itemView.findViewById(R.id.row_cartlist_tvKg);
            ivProImg = (ImageView) itemView.findViewById(R.id.row_cartlist_ivProImg);
            ivPlus = (ImageView) itemView.findViewById(R.id.row_cartlist_ivPlus);
            ivMins = (ImageView) itemView.findViewById(R.id.row_cartlist_ivMins);
            ivDelete = (ImageView) itemView.findViewById(R.id.btndelete);


        }


        public void bindData(final CartlistModel item, final int position)
        {

            System.out.println(item);

            tvProductName.setText("" + item.getProductName());

            tvKg.setText(item.getProductPrice());
            tvTotalKg.setText("Ordered Quantity: "+item.getProductQuantity());
            itemView.setTag(item);

            String[] splitm = item.getProductPrice().split("/");
            String[] split1 = splitm[0].split(". ");


            Picasso.with(mContext).load(item.getProductImages()).into(ivProImg);

            int pr = Integer.parseInt(split1[1]) * item.getProductQuantity();

            tvProductPrice.setText("Rs. "+pr);

            ivPlus.setVisibility(View.GONE);
            ivMins.setVisibility(View.GONE);
            ivDelete.setVisibility(View.GONE);

        }

    }

}
