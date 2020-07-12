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
 * @ClassdName:DialogDeActiveAccount
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class is use to display DeActive account dialog
 * <p/>
 * *************************************************************************
 */


public class DialogDeActiveAccount extends DialogFragment implements View.OnClickListener
{
    private TextView tvSave;
    private TextView tvCancel;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPsw;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimationTultip;
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog.setContentView(R.layout.dialog_deactive_account);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
        initializeComponent(dialog);
        return dialog;
    }


    protected void initializeComponent(Dialog v) {
        tvCancel = (TextView) v.findViewById(R.id.dialog_deactive_account_tvCancel);
        tvSave = (TextView) v.findViewById(R.id.dialog_deactive_account_tvSubmit);
        etEmail = (EditText) v.findViewById(R.id.dialog_deactive_account_etEmail);
        etPhone = (EditText) v.findViewById(R.id.dialog_deactive_account_etPhone);
        etPsw = (EditText) v.findViewById(R.id.dialog_deactive_account_etPsw);


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
            if (etEmail.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), R.string.enter_your_email, Toast.LENGTH_SHORT).show();
            } else if (etPhone.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), R.string.enter_your_phone, Toast.LENGTH_SHORT).show();
            } else if (etPsw.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), R.string.enter_current_password, Toast.LENGTH_SHORT).show();
            } else {
                dismiss();
            }
        }

    }
}
