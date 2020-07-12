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
 * @ClassdName:DialogFragmentFeedback
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class is use to display Feedback dialog
 * <p/>
 * *************************************************************************
 */

public class DialogFragmentFeedback extends DialogFragment implements View.OnClickListener {

    private TextView tvCancel;
    private TextView tvSend;
    private EditText etFeedback;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimationTultip;
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog.setContentView(R.layout.dialog_feedback);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
        initializeComponent(dialog);
        return dialog;
    }


    protected void initializeComponent(Dialog v) {


        tvCancel = (TextView) v.findViewById(R.id.dailog_send_feedback_tvCancel);
        tvSend = (TextView) v.findViewById(R.id.dailog_send_feedback_tvSubmit);
        etFeedback = (EditText) v.findViewById(R.id.dailog_send_feedback_etDes);
        tvCancel.setOnClickListener(this);
        tvSend.setOnClickListener(this);
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
        }
        else if (v == tvSend) {

            if (!etFeedback.getText().toString().isEmpty()) {
                dismiss();
            } else {
                Toast.makeText(getActivity(), R.string.enter_description, Toast.LENGTH_SHORT).show();
            }
        }


    }
}
