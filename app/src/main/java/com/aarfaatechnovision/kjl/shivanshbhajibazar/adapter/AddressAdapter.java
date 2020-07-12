package com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.customecomponent.CustomTextView;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.CheckOutFragmnet;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.AddressList.AddressListModelNew;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.addressBook.Address;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends BaseAdapter {
    Context context;
    ArrayList<Address> countryNames;
    LayoutInflater inflter;

    public AddressAdapter(Context applicationContext,  ArrayList<Address> countryNames) {
        this.context = applicationContext;
        this.countryNames = countryNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return countryNames.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.row_address, null);
        TextView names = (TextView) view.findViewById(R.id.txtssname);
        names.setText(countryNames.get(i).getSociety_name());
        return view;
    }
}
