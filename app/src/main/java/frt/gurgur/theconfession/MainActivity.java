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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moos.navigation.BottomBarLayout;
import com.moos.navigation.BottomTabView;
import com.trendyol.medusalib.navigator.MultipleStackNavigator;
import com.trendyol.medusalib.navigator.Navigator;
import com.trendyol.medusalib.navigator.NavigatorConfiguration;
import com.trendyol.medusalib.navigator.transaction.NavigatorTransaction;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import frt.gurgur.theconfession.databinding.ActivityMainBinding;

import frt.gurgur.theconfession.ui.explore.ExploreFragment;
import frt.gurgur.theconfession.ui.favorities.FavoritiesFragment;
import frt.gurgur.theconfession.ui.post.PostFragment;
import frt.gurgur.theconfession.ui.user.profile.ProfileBottomSheetFragment;
import frt.gurgur.theconfession.ui.user.profile.ProfileFragment;
import frt.gurgur.theconfession.ui.user.profile.UserFavoritedPostListFragment;
import frt.gurgur.theconfession.ui.user.profile.UserPostListFragment;
import frt.gurgur.theconfession.ui.user.profile.followpage.FollowFragment;
import frt.gurgur.theconfession.ui.user.profile.followpage.FollowerListFragment;
import frt.gurgur.theconfession.ui.user.profile.followpage.FollowingListFragment;
import frt.gurgur.theconfession.ui.user.register.RegisterFragment;
import frt.gurgur.theconfession.util.PreferencesHelper;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.main.MainFragment;
import frt.gurgur.theconfession.ui.user.login.LoginFragment;
import kotlin.jvm.functions.Function0;

import static frt.gurgur.theconfession.util.PreferencesHelper.EMPTY_USER_ID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , ProfileBottomSheetFragment.ItemClickListener, Navigator.NavigatorListener {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    public MultipleStackNavigator multipleStackNavigator;
    private ActivityMainBinding binding;
    private List<Function0<Fragment>> rootsFragmentProvider = Arrays.asList(
            new Function0<Fragment>() {
                @Override
                public Fragment invoke() {
                    return new MainFragment();
                }
            },
            new Function0<Fragment>() {
                @Override
                public Fragment invoke() {
                    return new ExploreFragment();
                }
            },
            new Function0<Fragment>() {
                @Override
                public Fragment invoke() {
                    return new PostFragment();
                }
            },
            new Function0<Fragment>() {
                @Override
                public Fragment invoke() {
                    return new FavoritiesFragment();
                }
            },
            new Function0<Fragment>() {
                @Override
                public Fragment invoke() {
                    return new ProfileFragment();
                }
            }

    );

    @Inject
    PreferencesHelper preferencesHelper;
    @BindView(R.id.toolbar_lay)
    View toolbar_lay;
    @BindView(R.id.btnBack)
    ImageButton btnBack;
    @BindView(R.id.txAppName)
    TextView txAppName;
    @BindView(R.id.btnProfileDetail)
    ImageButton btnProfileDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(this);

        initNavMenu(savedInstanceState);
        initView();


    }



    public void initNavMenu(Bundle savedInstanceState){
        multipleStackNavigator = new MultipleStackNavigator(
                getSupportFragmentManager(),
                R.id.container,
                rootsFragmentProvider,
                this,
                new NavigatorConfiguration(0, true, NavigatorTransaction.SHOW_HIDE),
                null);
        multipleStackNavigator.initialize(savedInstanceState);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }







    public void initView(){

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setContentInsetsAbsolute(0, 0);
        btnBack.setOnClickListener(this);
        btnProfileDetail.setOnClickListener(this);
        btnBack.setVisibility(View.GONE);



        int userId = preferencesHelper.getUserId();
        if (userId != EMPTY_USER_ID){
            multipleStackNavigator.start(new MainFragment());
        }else{
            multipleStackNavigator.start(new LoginFragment());
        }




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                    this.onBackPressed();
                break;
            case R.id.btnProfileDetail:
                showBottomSheet();
                break;

        }
    }


    public void showBottomSheet() {
        ProfileBottomSheetFragment addPhotoBottomDialogFragment =
                ProfileBottomSheetFragment.newInstance();
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                ProfileBottomSheetFragment.TAG);
    }

    @Override
    public void onItemClick(int position) {
        if (position==4){
            preferencesHelper.clear();
            multipleStackNavigator.start(new LoginFragment());
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    multipleStackNavigator.switchTab(0);
                    return true;
                case R.id.navigation_explore:
                    multipleStackNavigator.switchTab(1);
                    return true;
                case R.id.navigation_post:
                    multipleStackNavigator.switchTab(2);
                    return true;
                case R.id.navigation_fav:
                    multipleStackNavigator.switchTab(3);
                    return true;
                case R.id.navigation_profile:
                    multipleStackNavigator.switchTab(4);
                    return true;

            }
            return false;
        }
    };

    @Override
    public void onTabChanged(int tabIndex) {
        switch (tabIndex) {
            case 0:
                navigation.setSelectedItemId(R.id.navigation_home);
                break;
            case 1:
                navigation.setSelectedItemId(R.id.navigation_explore);
                break;
            case 2:
                navigation.setSelectedItemId(R.id.navigation_post);
                break;
            case 3:
                navigation.setSelectedItemId(R.id.navigation_fav);
                break;
            case 4:
                navigation.setSelectedItemId(R.id.navigation_profile);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (multipleStackNavigator.canGoBack()) {
            multipleStackNavigator.goBack();
        } else {
            super.onBackPressed();
        }
    }


}
