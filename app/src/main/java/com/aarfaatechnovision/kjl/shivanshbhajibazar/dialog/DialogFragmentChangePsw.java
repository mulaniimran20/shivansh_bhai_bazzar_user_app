package com.aarfaatechnovision.kjl.shivanshbhajibazar.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;

/**
 * *************************************************************************
 *
 * @ClassdName:DialogFragmentChangePsw
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class is use to display ChangePassword dialog
 * <p/>
 * *************************************************************************
 */
public class DialogFragmentChangePsw extends DialogFragment implements View.OnClickListener
{
    private TextView tvSave;
    private TextView tvCancel;
    private EditText etCurrentPse;
    private EditText etNewPSw;
    private EditText etConfirmPsw;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimationTultip;
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog.setContentView(R.layout.dialog_change_psw);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
        initializeComponent(dialog);
        return dialog;
    }


    protected void initializeComponent(Dialog v) {
        tvCancel = (TextView) v.findViewById(R.id.dailog_change_psw_tvCancel);
        tvSave = (TextView) v.findViewById(R.id.dailog_change_psw_tvSubmit);
        etCurrentPse = (EditText) v.findViewById(R.id.dailog_change_psw_etCurrentPsw);
        etNewPSw = (EditText) v.findViewById(R.id.dailog_change_psw_etNewPsw);
        etConfirmPsw = (EditText) v.findViewById(R.id.dailog_change_psw_etConfirmPsw);


        tvSave.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
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
        } else if (v == tvSave) {
            if (etCurrentPse.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), R.string.enter_first_name, Toast.LENGTH_SHORT).show();
            } else if (etNewPSw.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), R.string.enter_last_name, Toast.LENGTH_SHORT).show();
            } else if (etConfirmPsw.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), R.string.enter_your_phone, Toast.LENGTH_SHORT).show();
            } else {
                dismiss();
            }
        }

    }
}
