package com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;

/**
 * *************************************************************************
 *
 * @ClassdName:DialogFragmentClearHistory
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class is use to display clear history dialog
 * <p/>
 * *************************************************************************
 */

public class DialogFragmentClearHistory extends DialogFragment implements View.OnClickListener {

    private TextView tvSearchHistory;
    private TextView tvClearAll;
    private TextView tvOrderHistory;
    private TextView tvCancel;
    private ImageView ivClose;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimationTultip;
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog.setContentView(R.layout.dialog_clear_history);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
        initializeComponent(dialog);
        return dialog;
    }


    protected void initializeComponent(Dialog v) {

        tvCancel = (TextView) v.findViewById(R.id.dailog_history_tvCancel);
        tvClearAll = (TextView) v.findViewById(R.id.dailog_history_tvClearAll);
        tvSearchHistory = (TextView) v.findViewById(R.id.dailog_history_tvSearchHistory);
        tvOrderHistory = (TextView) v.findViewById(R.id.dailog_history_tvOrderHistory);
        ivClose = (ImageView) v.findViewById(R.id.dailog_history_ivClose);

        tvClearAll.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        ivClose.setOnClickListener(this);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void onClick(View v) {

        if (v == tvCancel) {
            dismiss();
        } else if (v == ivClose) {
            dismiss();
        }
        else if (v == tvClearAll) {
          dismiss();
        }

    }
}
