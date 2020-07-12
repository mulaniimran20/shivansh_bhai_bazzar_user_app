package com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.customecomponent.CustomTextView;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.CheckOutFragmnet;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.AddressList.AddressListModelNew;

import java.util.List;

public class AddressListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<AddressListModelNew> addressListModelNew;
    private Context mContext;
    private CheckOutFragmnet checkOutFragmnet;

    /**
     * setup Constructure
     */

    public AddressListAdapter(final List<AddressListModelNew> addressListModelNew, final Context mContext, final CheckOutFragmnet checkOutFragmnet) {
        this.addressListModelNew = addressListModelNew;
        this.mContext = mContext;
        this.checkOutFragmnet = checkOutFragmnet;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_address_list, parent, false);
        return new ViewHolderData(v);

    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderData) holder).bindData(addressListModelNew.get(position), position);
    }

    @Override
    public int getItemCount() {
        return addressListModelNew.size();
    }

    /**
     * bind ViewHolder
     */

    protected class ViewHolderData extends RecyclerView.ViewHolder {

        private CustomTextView tvAddress;

        public ViewHolderData(View itemView) {
            super(itemView);

            tvAddress= (CustomTextView) itemView.findViewById(R.id.row_addresslistnew_tvAddress);

        }

        public void bindData(final AddressListModelNew item, final int position)
        {

            tvAddress.setText(""+item.getAddress());

        }
    }


}
