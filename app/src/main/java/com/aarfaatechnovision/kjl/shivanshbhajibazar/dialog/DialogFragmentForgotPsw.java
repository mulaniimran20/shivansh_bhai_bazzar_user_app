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
import com.aarfaatechnovision.kjl.shivanshbhajibazar.util.Utils;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.util.Utils;

/**
 * *************************************************************************
 *
 * @ClassdName:DialogFragmentForgotPsw
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class is use to display Forgot password dialog
 * <p/>
 * *************************************************************************
 */
public class DialogFragmentForgotPsw extends DialogFragment implements View.OnClickListener
{
    private TextView tvSave;
    private TextView tvCancel;
    private EditText etEmail;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimationTultip;
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog.setContentView(R.layout.dialog_forgot_psw);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
        initializeComponent(dialog);
        return dialog;
    }


    protected void initializeComponent(Dialog v) {
        tvCancel = (TextView) v.findViewById(R.id.dailog_forgot_tvCancel);
        tvSave = (TextView) v.findViewById(R.id.dailog_forgot_tvSubmit);
        etEmail = (EditText) v.findViewById(R.id.dailog_forgot_etEmail);


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
            }
            else if (!Utils.isValidEmail(etEmail.getText().toString())) {
                Toast.makeText(getActivity(), R.string.val_enter_valid_email, Toast.LENGTH_SHORT).show();
            } else {
                dismiss();
            }
        }

    }
}
