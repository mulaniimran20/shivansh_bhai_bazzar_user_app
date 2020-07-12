package com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.customecomponent.CustomTextView;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.CheckOutFragmnet;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.OrderList.OrderListModelNew;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.cart.CartlistModel;

import java.util.List;

/**
 * Created by Kailash on 8/6/2018.
 */

public class OrderSummaryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CartlistModel> orderListModelNewList;
    private Context mContext;
    private CheckOutFragmnet checkOutFragmnet;


    /**
     * setup Constructure
     */
    public OrderSummaryListAdapter(List<CartlistModel> orderListModelNewList, Context mContext, CheckOutFragmnet checkOutFragmnet) {
        this.orderListModelNewList = orderListModelNewList;
        this.mContext = mContext;
        this.checkOutFragmnet = checkOutFragmnet;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order_summary_list, parent, false);
        return new ViewHolderData(v);

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderData) holder).bindData(orderListModelNewList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return orderListModelNewList.size();
    }


    /**
     * bind ViewHolder
     */
    protected class ViewHolderData extends RecyclerView.ViewHolder {

        private CustomTextView tvOrederName;
        private CustomTextView tvOrderQuntity;
        private CustomTextView tvOrderPrice;

        public ViewHolderData(View itemView) {
            super(itemView);

            tvOrederName = (CustomTextView) itemView.findViewById(R.id.row_orderlist_new_tvOrederName);
            tvOrderQuntity = (CustomTextView) itemView.findViewById(R.id.row_orderlist_new_tvQuntity);
            tvOrderPrice = (CustomTextView) itemView.findViewById(R.id.row_orderlist_new_tvPrice);

        }

        public void bindData(final CartlistModel item, final int position) {


            tvOrederName.setText("" + item.getProductName());
            tvOrderQuntity.setText("" + item.getProductQuantity());
            tvOrderPrice.setText("" + item.getProductPrice());


        }
    }
}



