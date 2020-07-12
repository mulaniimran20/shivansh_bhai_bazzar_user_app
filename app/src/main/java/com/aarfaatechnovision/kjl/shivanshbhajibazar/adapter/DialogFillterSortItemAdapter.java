package com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.customecomponent.CustomTextView;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.DialogFiiter.DiloagFitterItemModel;

import java.util.List;

public class DialogFillterSortItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<DiloagFitterItemModel> diloagFitterItemModelList;
    private Context mContext;


    /**
     * setup Constructure
     */

    public DialogFillterSortItemAdapter(final List<DiloagFitterItemModel> diloagFitterItemModelList, final Context mContext) {
        this.diloagFitterItemModelList = diloagFitterItemModelList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialogfrgment_sortlist, parent, false);
        return new ViewHolderData(v);

    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderData) holder).bindData(diloagFitterItemModelList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return diloagFitterItemModelList.size();
    }

    /**
     * bind ViewHolder
     */

    protected class ViewHolderData extends RecyclerView.ViewHolder {

        private CustomTextView tvSortItem;
        private CustomTextView tvPrice;
        private CustomTextView tvFirst;

        public ViewHolderData(View itemView) {
            super(itemView);

            tvSortItem= (CustomTextView) itemView.findViewById(R.id.row_dialogfrgmnet_sortList_tvsortItem);
            tvPrice= (CustomTextView) itemView.findViewById(R.id.row_dialogfrgmnet_sortList_tvPrice);
            tvFirst= (CustomTextView) itemView.findViewById(R.id.row_dialogfrgmnet_sortList_tvFirst);

        }

        public void bindData(final DiloagFitterItemModel item, final int position)
        {

            tvSortItem.setText(""+item.getSorting());
            tvPrice.setText(""+item.getPrice());
            tvFirst.setText(""+item.getFirst());

        }
    }


}
