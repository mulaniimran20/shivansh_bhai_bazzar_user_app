package com.aarfaatechnovision.kjl.shivanshbhajibazar.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;

/**
 * @purpose commonly used functions
 * @purpose
 */
public class Utils {


    /**
     * @param context
     * @param message
     * @return
     * @description use to check internet network connection if network
     * connection not available than alert for open network
     * settings
     */
    public static boolean isOnline(final Activity context, boolean message) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
        if (netInfo != null) {
            if (netInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        if (message) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(context.getString(R.string.app_name_new));
            dialog.setCancelable(false);
            dialog.setMessage(context.getString(R.string.check_connection));
            dialog.setPositiveButton(context.getString(R.string.settings), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                }
            });
            dialog.setNegativeButton(context.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            dialog.show();

            return false;
        }
        return false;
    }


    /**
     * @param inputEmail
     * @return
     * @purpose validate email
     */
    public final static boolean isValidEmail(CharSequence inputEmail) {
        if (inputEmail == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches();
        }
    }


    /**
     * Hide KeyBoard Using CurrentFocus
     *
     * @return
     */
    public static void hideKeyboard(Context mContext) {
        InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        View focus = ((Activity) mContext).getCurrentFocus();

        if (focus != null) {

            inputManager.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public static void snackbar(final View view, final String msg, boolean isSnakbar, Context mContext) {

        try {
            if (isSnakbar) {
                Snackbar snack = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
                snack.getView().setBackgroundColor(Color.parseColor("#84af2a"));
                View viewNew = snack.getView();
                TextView tv = (TextView) viewNew.findViewById(android.support.design.R.id.snackbar_text);
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                snack.show();
            } else {
                Toast.makeText(mContext, "" + msg, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * @param mActivity
     * @param targetedFragment
     * @param shooterFragment
     * @purpose for call targeted fragment from current fragment
     */

    public static void addNextFragment(Activity mActivity, Fragment targetedFragment, Fragment shooterFragment, boolean isDownToUp) {
        final FragmentTransaction transaction = mActivity.getFragmentManager().beginTransaction();


        if (isDownToUp) {
            transaction.setCustomAnimations(R.animator.slide_fragment_vertical_right_in, R.animator.slide_fragment_vertical_left_out, R.animator.slide_fragment_vertical_left_in,
                    R.animator.slide_fragment_vertical_right_out);

        } else {
            transaction.setCustomAnimations(R.animator.slide_fragment_horizontal_right_in, R.animator.slide_fragment_horizontal_left_out, R.animator.slide_fragment_horizontal_left_in,
                    R.animator.slide_fragment_horizontal_right_out);

        }


        transaction.add(R.id.flcontainer, targetedFragment, targetedFragment.getClass().getSimpleName());

        transaction.hide(shooterFragment);
        transaction.addToBackStack(targetedFragment.getClass().getSimpleName());
        transaction.commit();
    }

    public static void addNextFragmentFadeAnim(Activity mActivity, Fragment targetedFragment, Fragment shooterFragment) {
        final FragmentTransaction transaction = mActivity.getFragmentManager().beginTransaction();

        transaction.setCustomAnimations(R.animator.enter,R.animator.exit,R.animator.pop_enter, R.animator.pop_exit);
        transaction.add(R.id.flcontainer, targetedFragment, targetedFragment.getClass().getSimpleName());
        transaction.hide(shooterFragment);
        transaction.addToBackStack(targetedFragment.getClass().getSimpleName());
        transaction.commit();
    }




}
