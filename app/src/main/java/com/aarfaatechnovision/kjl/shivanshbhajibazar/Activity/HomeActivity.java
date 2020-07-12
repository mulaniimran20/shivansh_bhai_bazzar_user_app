package com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.GrocerApplication;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.HomeCategoryFragment;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.LoginSignUpFragment;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.fragment.MenuFragment;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.util.Utils;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    //private EditText etSearch;
    private ImageView ivMenu;
    private ImageView ivCart;
    private TextView tvTitle;
    private RelativeLayout rlToolbar;
    private Fragment mFragment = null;
    private HomeCategoryFragment mainFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

    }

    private void initView() {

        //etSearch = (EditText) findViewById(R.id.activity_home_etSearch);
        ivMenu = (ImageView) findViewById(R.id.activity_home_ivMenu);
        ivCart = (ImageView) findViewById(R.id.activity_home_ivCart);
        tvTitle = (TextView) findViewById(R.id.activity_home_tvTitle);
        rlToolbar = (RelativeLayout) findViewById(R.id.activity_home_rlToolbar);

        ivMenu.setOnClickListener(this);
        ivCart.setOnClickListener(this);

        boolean isLogin = GrocerApplication.getmInstance().getSharedPreferences().getBoolean(getString((R.string.preferances_islogin)), false);

        if (!isLogin)
        {
            rlToolbar.setVisibility(View.INVISIBLE);
            LoginSignUpFragment menuFragment = new LoginSignUpFragment();
            openFragment(menuFragment);
            getFragmentManager().popBackStack();
        }
        else{
            mainFragment = new HomeCategoryFragment();
            openFragment(mainFragment);
            rlToolbar.setVisibility(View.INVISIBLE);
        }



    }



    /**
     * SetUp toolbar & Title & HomeIndicator Image & Navigation drawer lock
     */


    public void setUpToolbar(final String title, final boolean isShowback, boolean isToolbarVisible, boolean isSearch, boolean isCart) {

        rlToolbar.setVisibility(isToolbarVisible ? View.VISIBLE : View.GONE);
        ivMenu.setBackgroundResource(isShowback ? R.drawable.ic_menu : R.drawable.ic_back_arror);
        //etSearch.setVisibility(isSearch ? View.VISIBLE : View.GONE);
        tvTitle.setVisibility(isSearch ? View.GONE : View.VISIBLE);
        ivCart.setVisibility(isCart ? View.VISIBLE : View.GONE);
        tvTitle.setText(title);


    }


    /**
     * Open fragment
     */

    private void openFragment(final Fragment mFragment) {

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.flcontainer, mFragment, mFragment.getClass().getSimpleName());
        transaction.commit();

    }


    @Override
    public void onClick(View v) {

        Utils.hideKeyboard(HomeActivity.this);

        if (v == ivMenu) {
            FragmentManager fragmentManager = getFragmentManager();

            if (fragmentManager.getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();

            } else {

                MenuFragment menuFragment = new MenuFragment();
                Utils.addNextFragmentFadeAnim(HomeActivity.this, menuFragment, mainFragment);
            }


        }
    }
    public ImageView getIvCart() {
        return ivCart;
    }

    public void setIvCart(ImageView ivCart) {
        this.ivCart = ivCart;
    }

}
