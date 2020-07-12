package com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.customecomponent.CustomTextView;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.FinalPayment;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.HomeCategoryFragment;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.OrderDetailsFragment;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.OrderListFragment;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.PaymentFragment;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.ProductListFragment;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.ClickedOrderId;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.order.OrderListModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.product.ProductListModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.util.Utils;

import java.util.List;

/**
 * Created by Kailash on 8/6/2018.
 */

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<OrderListModel> orderListModels;
    private Context mContext;
    private OrderListFragment orderListFragment;
    private OnItemClickListener onItemClickListener;

    Activity activity;



    /**
     * setup Constructure
     */

    public OrderListAdapter(Activity activity,final List<OrderListModel> addressListModelNew, final Context mContext, final OrderListFragment checkOutFragmnet) {
        this.orderListModels = addressListModelNew;
        this.mContext = mContext;
        this.orderListFragment = checkOutFragmnet;
        this.activity = activity;
    }



    @Override
    public void onClick(final View v) {
        if (onItemClickListener != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onItemClickListener.onItemClick(v, (OrderListModel) v.getTag());
                }
            }, 200);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, OrderListModel viewModel);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order_history_list, parent, false);
        return new ViewHolderData(v);

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderData) holder).bindData(orderListModels.get(position), position);
    }

    @Override
    public int getItemCount() {
        return orderListModels.size();
    }


    /**
     * bind ViewHolder
     */
    protected class ViewHolderData extends RecyclerView.ViewHolder {

        private CustomTextView tvOrderDate;
        private CustomTextView tvDeliveryDate;
        private CustomTextView tvOrderId;
        private CustomTextView tvCancelLable;
        private CustomTextView tvDeliveryLable;
        private RelativeLayout rlReportOrder;
        private RelativeLayout rlCancelOrder;
        private  CustomTextView orderdatetext, orderidtext;
        private CardView cvMain;
        private ImageView ivStatus;

        public ViewHolderData(View itemView) {
            super(itemView);

            cvMain = (CardView) itemView.findViewById(R.id.row_cancel_order_cvMain);
            tvOrderDate = (CustomTextView) itemView.findViewById(R.id.row_cancel_order_tvDate);
            tvDeliveryDate = (CustomTextView) itemView.findViewById(R.id.row_cancel_order_tvDeliveryDate);
            tvOrderId = (CustomTextView) itemView.findViewById(R.id.row_cancel_order_tvOrderID);
            tvCancelLable = (CustomTextView) itemView.findViewById(R.id.row_cancel_order_tvCancelLable);
            tvDeliveryLable = (CustomTextView) itemView.findViewById(R.id.row_cancel_order_tvDeliveryLable);
            rlCancelOrder = (RelativeLayout) itemView.findViewById(R.id.row_cancel_order_rlCancelOrder);
            rlReportOrder = (RelativeLayout) itemView.findViewById(R.id.row_cancel_order_rlReportOrder);
            ivStatus = (ImageView) itemView.findViewById(R.id.row_cancel_order_ivStatus);
            orderdatetext = itemView.findViewById(R.id.orderdatetext);
            orderidtext = itemView.findViewById(R.id.orderidtext);

            tvDeliveryLable.setText("Total Bill : ");


        }

        public void bindData(final OrderListModel item, final int position) {

            tvOrderDate.setText("" + item.getOrderDate());
            tvOrderId.setText("" + item.getOrderId());
            tvDeliveryDate.setText("" + item.getPrice());

            if (item.getStatus().equalsIgnoreCase(mContext.getString(R.string.lbl_pending))) {
                rlCancelOrder.setVisibility(View.GONE);
                rlReportOrder.setVisibility(View.VISIBLE);
                tvCancelLable.setVisibility(View.GONE);
                tvDeliveryDate.setVisibility(View.VISIBLE);
                ivStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_order_pending));
                cvMain.setCardBackgroundColor(mContext.getResources().getColor(R.color.order_pendig_bgcolro));
            } else if (item.getStatus().equalsIgnoreCase(mContext.getString(R.string.lbl_delivery))) {
                rlCancelOrder.setVisibility(View.GONE);
                rlReportOrder.setVisibility(View.VISIBLE);
                tvCancelLable.setVisibility(View.GONE);
                tvDeliveryDate.setVisibility(View.VISIBLE);
                ivStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_order_delivred));
                cvMain.setCardBackgroundColor(mContext.getResources().getColor(R.color.order_delivery_bgcolro));
            }


            if (position == (orderListModels.size() - 1))
            {
                tvOrderDate.setVisibility(View.GONE);
                tvOrderId.setVisibility(View.GONE);
                orderdatetext.setVisibility(View.GONE);
                orderidtext.setVisibility(View.GONE);

            }


            cvMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == (orderListModels.size() - 1)) {

                        if(Float.parseFloat(item.getPrice()) == 0.0)
                        {
                            Toast.makeText(mContext, "No payment pending", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            FinalPayment finalPayment = new FinalPayment();
                            finalPayment.setFinalPayment(item.getPrice());

                            Intent intent = new Intent(mContext, PaymentFragment.class);
                            mContext.startActivity(intent);
                        }
                    }
                    else{
                        OrderDetailsFragment fragmentProductDetails = new OrderDetailsFragment();

                        ClickedOrderId clickedOrderId = new ClickedOrderId();
                        clickedOrderId.setOrderId(item.getOrderId());

                        Utils.addNextFragment(activity, fragmentProductDetails, orderListFragment, false);
                    }

                }
            });





        }
    }


}
