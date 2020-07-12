package com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.customecomponent.CustomTextView;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.DialogFiiter.DiloagFitterDiscountModel;

import java.util.List;

/**
 * Created by Nishish on 8/6/2018.
 */

public class DialogFillterDiscounttemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DiloagFitterDiscountModel> DiloagFitterDiscountModelList;
    private Context mContext;


    /**
     * setup Constructure
     */

    public DialogFillterDiscounttemAdapter(final List<DiloagFitterDiscountModel> DiloagFitterDiscountModelList, final Context mContext) {
        this.DiloagFitterDiscountModelList = DiloagFitterDiscountModelList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialogfrgment_discounlist, parent, false);
        return new ViewHolderData(v);

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    /**
     * bind ViewHolder
     */

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderData) holder).bindData(DiloagFitterDiscountModelList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return DiloagFitterDiscountModelList.size();
    }

    protected class ViewHolderData extends RecyclerView.ViewHolder {

        private CustomTextView tvDiscount;

        public ViewHolderData(View itemView) {
            super(itemView);

            tvDiscount = (CustomTextView) itemView.findViewById(R.id.row_dialogfiliter_tvDiscount);


        }

        public void bindData(final DiloagFitterDiscountModel item, final int position) {
            tvDiscount.setText("" + item.getDiscount() + mContext.getString(R.string.per));


        }
    }


}
