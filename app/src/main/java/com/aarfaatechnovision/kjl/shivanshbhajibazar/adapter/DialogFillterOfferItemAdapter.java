package com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.customecomponent.CustomTextView;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.DialogFiiter.DialogOfferListModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.DialogFiiter.DiloagFitterItemModel;

import java.util.List;

/**
 * Created by Nishish on 8/6/2018.
 */

public class DialogFillterOfferItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<DialogOfferListModel> dialogOfferListModelList ;
    private Context mContext;


    /**
     * setup Constructure
     */

    public DialogFillterOfferItemAdapter(final List<DialogOfferListModel> dialogOfferListModelList, final Context mContext) {
        this.dialogOfferListModelList = dialogOfferListModelList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialogfrgment_offerlist, parent, false);
        return new ViewHolderData(v);

    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderData) holder).bindData(dialogOfferListModelList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dialogOfferListModelList.size();
    }

    /**
     * bind ViewHolder
     */

    protected class ViewHolderData extends RecyclerView.ViewHolder {

        private CustomTextView tvOfferName;
        private CardView cvMainContainer;


        public ViewHolderData(View itemView) {
            super(itemView);

            tvOfferName= (CustomTextView) itemView.findViewById(R.id.row_dialogfrgmnet_offerlist_tvOfferName);
            cvMainContainer=itemView.findViewById(R.id.row_dialogfrgmnet_offerlist_cvMainContainer);

        }

        @SuppressLint("ResourceAsColor")
        public void bindData(final DialogOfferListModel item, final int position)
        {

           tvOfferName.setText(""+item.getOfferName());
            if(position==0)
                cvMainContainer.setCardBackgroundColor(Color.parseColor("#f7e8ff"));
            else if(position==1)
                cvMainContainer.setCardBackgroundColor(Color.parseColor("#3362dc"));
            else if(position==2)
                cvMainContainer.setCardBackgroundColor(Color.parseColor("#c3ece4"));
            else if (position==3)
                cvMainContainer.setCardBackgroundColor(Color.parseColor("#f4eeb7"));



        }
    }


}
