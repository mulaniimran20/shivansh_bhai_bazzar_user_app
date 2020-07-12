package com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.DialogFillterDiscounttemAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.DialogFillterOfferItemAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter.DialogFillterSortItemAdapter;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.data.TempListData;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.DialogFiiter.DialogOfferListModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.DialogFiiter.DiloagFitterDiscountModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.DialogFiiter.DiloagFitterItemModel;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

public class DialogFragmnetFiltter extends BottomSheetDialogFragment implements View.OnClickListener
{

    //Declaration
    private RecyclerView rvSortItemList;
   // private RecyclerView rvOfferList;
    private RecyclerView rvDiscount;
    private ImageView ivClose;

    private List<DiloagFitterItemModel> diloagFitterItemModelList;
    private List<DialogOfferListModel> dialogOfferListModelList;
    private List<DiloagFitterDiscountModel> diloagFitterDiscountModels;

    private DialogFillterSortItemAdapter dialogFillterSortItemAdapter;
    private DialogFillterOfferItemAdapter dialogFillterOfferItemAdapter;
    private DialogFillterDiscounttemAdapter dialogFillterDiscounttemAdapter;

    private RangeSeekBar sbRangPrice;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static DialogFragmnetFiltter newInstance() {
        return new DialogFragmnetFiltter();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dailog_fargment_fiter, container, false);

        initComponet(v);
        setSortListItem();
        setOfferList();
        setDiscount();

        return v;
    }


    /**
     * init Components
     */

    private void initComponet(View v)
    {
        rvSortItemList=v.findViewById(R.id.dialog_frgmentFiltter_rvSortList);
//        rvOfferList=v.findViewById(R.id.dialog_fragment_rvOfferList);
        rvDiscount=v.findViewById(R.id.dialog_fragment_rvDiscount);
        ivClose=v.findViewById(R.id.dialog_filter_ivClose);

        rvSortItemList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //rvOfferList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvDiscount.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        sbRangPrice=v.findViewById(R.id.dialog_frgmentFiltter_sbSortingPrice);
        sbRangPrice.setTextAboveThumbsColorResource(R.color.checkout_order_btn);
        ivClose.setOnClickListener(this);
    }

    /**
     * get Sort list data and setUp adapter
     */

    private void setSortListItem()
    {
        TempListData tempListData = new TempListData();
        diloagFitterItemModelList = tempListData.getFilterList();
        dialogFillterSortItemAdapter=new DialogFillterSortItemAdapter(diloagFitterItemModelList,getActivity());
        rvSortItemList.setAdapter(dialogFillterSortItemAdapter);

    }

    /**
     * get offer list data and setUp adapter
     */


    private void setOfferList()
    {
        dialogOfferListModelList=new ArrayList<>();
        TempListData tempListData = new TempListData();
        dialogOfferListModelList = tempListData.getOfferList();

        dialogFillterOfferItemAdapter=new DialogFillterOfferItemAdapter(dialogOfferListModelList,getActivity());
        //rvOfferList.setAdapter(dialogFillterOfferItemAdapter);

    }


    /**
     * get Discount list data and setUp adapter
     */


    private void setDiscount()
    {
        diloagFitterDiscountModels=new ArrayList<>();
        TempListData tempListData = new TempListData();
        diloagFitterDiscountModels = tempListData.getDiscountList();
        dialogFillterDiscounttemAdapter=new DialogFillterDiscounttemAdapter(diloagFitterDiscountModels,getActivity());
        rvDiscount.setAdapter(dialogFillterDiscounttemAdapter);

    }


    @Override
    public void onClick(View v) {

        if(v==ivClose)
        {
            dismiss();
        }
    }
}
