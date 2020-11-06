package frt.gurgur.theconfession;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.moos.navigation.BottomBarLayout;
import com.moos.navigation.BottomTabView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Stack;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import frt.gurgur.theconfession.databinding.ActivityMainBinding;

import frt.gurgur.theconfession.ui.user.profile.ProfileFragment;
import frt.gurgur.theconfession.ui.user.register.RegisterFragment;
import frt.gurgur.theconfession.util.PreferencesHelper;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.main.MainFragment;
import frt.gurgur.theconfession.ui.user.login.LoginFragment;

import static frt.gurgur.theconfession.util.PreferencesHelper.EMPTY_USER_ID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private BottomTabView tab_home, tab_explore,tab_share_post,tab_favorities, tab_profile;

    private ActivityMainBinding binding;

    private boolean firstStart = true;
    public static final String FRAGMENTS = "Fragments";
    private static WeakReference<FragmentActivity> wrActivity = null;
    private HashMap<String, Stack<Fragment>> mStacks;
    private Fragment currentFragment;
    private String animStyle = "";

    @BindView(R.id.toolbar_lay)
    View toolbar_lay;
    @BindView(R.id.btnBack)
    ImageButton btnBack;

    @BindView(R.id.txAppName)
    TextView txAppName;

    @Inject
    PreferencesHelper preferencesHelper;

    @BindView(R.id.bottom_bar)
    BottomBarLayout bottomBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(this);
        wrActivity = new WeakReference<>(this);
        mStacks = new HashMap<String, Stack<Fragment>>();
        mStacks.put(FRAGMENTS, new Stack<Fragment>());
        initView();
        navvv();
    }


    public void navvv(){
        tab_home = new BottomTabView(this);
        tab_home.setTabIcon(R.drawable.m_home);
        tab_home.setTabTitle("Home");
        ////////////////////////////
        tab_explore = new BottomTabView(this);
        tab_explore.setTabIcon(R.drawable.m_ecplore);
        tab_explore.setTabTitle("Keşfet");
        ////////////////////////////
        tab_share_post = new BottomTabView(this);
        tab_share_post.setTabIcon(R.drawable.m_post);
        tab_share_post.setTabTitle("Paylaş");
        ////////////////////////////
        tab_favorities = new BottomTabView(this);
        tab_favorities.setTabIcon(R.drawable.m_fav);
        tab_favorities.setTabTitle("Beğeni");
        ////////////////////////////
        tab_profile = new BottomTabView(this);
        tab_profile.setTabIcon(R.drawable.m_profile);
        tab_profile.setTabTitle("Profil");
       //tab_mine.setUnreadCount(100);


        bottomBarLayout
                .addTab(tab_home)
                .addTab(tab_explore)
                .addTab(tab_share_post)
                .addTab(tab_favorities)
                .addTab(tab_profile)
                .create(new BottomBarLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(BottomTabView tab) {
                        //you can switch the fragment here
                        Log.e("xx", "onTabSelected: ====="+tab.getTabPosition() );
                        switch (tab.getTabPosition()){
                            case 0:
                                pushFragment(new MainFragment(),MainFragment.FRAGMENT_TAG);
                                break;
                            case 1:
                                break;
                            case 2:

                                break;
                            case 3:
                                break;
                            case 4:
                                pushFragment(new ProfileFragment(),ProfileFragment.FRAGMENT_TAG);
                                break;
                        }
                    }

                    @Override
                    public void onTabUnselected(BottomTabView tab) {

                    }

                    @Override
                    public void onTabReselected(BottomTabView tab) {

                    }
                });
    }


    public void setTitleScreen(Fragment fragment) {
        currentFragment = fragment;
        if (fragment instanceof LoginFragment){
            bottomBarLayout.setVisibility(View.GONE);
            btnBack.setVisibility(View.GONE);
            txAppName.setText(getString(R.string.app_name));
        }else if (fragment instanceof RegisterFragment){
            bottomBarLayout.setVisibility(View.GONE);
            btnBack.setVisibility(View.GONE);
            txAppName.setText(getString(R.string.app_name));
        }else if(fragment instanceof MainFragment){
            bottomBarLayout.setVisibility(View.VISIBLE);
            btnBack.setVisibility(View.GONE);
            txAppName.setText(getString(R.string.app_name));
        }else if (fragment instanceof ProfileFragment){
            bottomBarLayout.setVisibility(View.VISIBLE);
            btnBack.setVisibility(View.GONE);
            txAppName.setText(preferencesHelper.getUserUsername());
        }
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.container);
    }

    public void addFragment(@NonNull Fragment fragment,
                            @NonNull String fragmentTag) {
        mStacks.get(FRAGMENTS).push(fragment);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commitAllowingStateLoss();

    }


    public void pushFragment(@NonNull Fragment fragment,
                             @NonNull String fragmentTag) {
        pushFragment(fragment, fragmentTag, true);
    }

    public void pushFragment(@NonNull Fragment fragment,
                             @NonNull String fragmentTag, boolean animationActive) {
        mStacks.get(FRAGMENTS).push(fragment);

        if (!firstStart) {
            if (animationActive)
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.container, fragment, fragmentTag)
                        .disallowAddToBackStack()
                        .commitAllowingStateLoss();
            else
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment, fragmentTag)
                        .disallowAddToBackStack()
                        .commitAllowingStateLoss();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment, fragmentTag)
                    .disallowAddToBackStack()
                    .commitAllowingStateLoss();
            firstStart = false;
        }
        setTitleScreen(fragment);
    }

    public void pushFragment(@NonNull Fragment fragment,
                             @NonNull String fragmentTag, boolean animationActive, String animTransition) {
        mStacks.get(FRAGMENTS).push(fragment);
        if (!firstStart) {
            if (animationActive) {
                if (animTransition.equals("downToUp")) {
                    animStyle = animTransition;
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_up)
                            .replace(R.id.container, fragment, fragmentTag)
                            .disallowAddToBackStack()
                            .commitAllowingStateLoss();
                } else {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                            .replace(R.id.container, fragment, fragmentTag)
                            .disallowAddToBackStack()
                            .commitAllowingStateLoss();
                }

            } else
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment, fragmentTag)
                        .disallowAddToBackStack()
                        .commitAllowingStateLoss();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment, fragmentTag)
                    .disallowAddToBackStack()
                    .commitAllowingStateLoss();
            firstStart = false;
        }
        setTitleScreen(fragment);
    }


    public void popFragments(int count) {
        if (mStacks != null && mStacks.get(FRAGMENTS) != null && mStacks.get(FRAGMENTS).size() > count) {

            Stack<Fragment> fragments = mStacks.get(FRAGMENTS);
            for (int i = count + 1; i > 1; i--) {
                Fragment fragment = fragments.get(fragments.size() - i);
                fragments.remove(fragment);
            }
        }

    }

    public void popFragments() {
        popFragments(true);
    }

    public void popFragments(boolean isExistAnimation) {
        /*
         *    Select the second last fragment in current tab's stack..
         *    which will be shown after the fragment transaction given below
         */
        /* We have the target fragment in hand.. Just show it.. Show a standard navigation animation*/
        Fragment fragment = mStacks.get(FRAGMENTS).elementAt(mStacks.get(FRAGMENTS).size() - 2);
        setTitleScreen(fragment);
        mStacks.get(FRAGMENTS).pop();

        final FragmentActivity activity = wrActivity.get();
        if (activity != null && !activity.isFinishing()) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            if (isExistAnimation) // exit animation
                if (animStyle.equals("downToUp")) {
                    ft.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down);
                    animStyle = "";

                } else {
                    ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

                }
            else    // push animation
                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

            ft.replace(R.id.container, fragment);
            ft.commitAllowingStateLoss();

        }
    }

    public void popAllFragments() {
        mStacks.get(FRAGMENTS).clear();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        screenBackCallPreventDefault();
    }


    private void screenBackCallPreventDefault() {
        if (mStacks != null && mStacks.get(FRAGMENTS) != null && mStacks.get(FRAGMENTS).size() > 0) {
            if (mStacks.get(FRAGMENTS).lastElement() != null) {

                if (!((BaseFragment) mStacks.get(FRAGMENTS).lastElement()).onBackPressed()) {
                    if (mStacks.get(FRAGMENTS).size() == 1) {
                        try {
                            super.onBackPressed();  // or call finish..
                        } catch (Exception e) {
                            //Crashlytics.logException(e);
                        }
                    } else {
                        popFragments();
                    }
                } else {
                    //do nothing.. fragment already handled back button press.
                }
            }
        }
    }


    public void initView(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setContentInsetsAbsolute(0, 0);
        btnBack.setOnClickListener(this);
        btnBack.setVisibility(View.GONE);



        int userId = preferencesHelper.getUserId();
        if (userId != EMPTY_USER_ID){
            pushFragment(new MainFragment(), MainFragment.FRAGMENT_TAG);
        }else{
            pushFragment(new LoginFragment(), LoginFragment.FRAGMENT_TAG);
        }




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                    this.onBackPressed();
                break;
        }
    }


}
