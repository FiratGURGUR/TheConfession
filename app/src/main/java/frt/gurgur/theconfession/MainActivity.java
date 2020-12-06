package frt.gurgur.theconfession;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.trendyol.medusalib.navigator.MultipleStackNavigator;
import com.trendyol.medusalib.navigator.Navigator;
import com.trendyol.medusalib.navigator.NavigatorConfiguration;
import com.trendyol.medusalib.navigator.transaction.NavigatorTransaction;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import frt.gurgur.theconfession.databinding.ActivityMainBinding;

import frt.gurgur.theconfession.ui.explore.ExploreFragment;
import frt.gurgur.theconfession.ui.favorities.FavoritiesFragment;
import frt.gurgur.theconfession.ui.post.comments.CommentFragment;
import frt.gurgur.theconfession.ui.user.profile.ProfileBottomSheetFragment;
import frt.gurgur.theconfession.ui.user.profile.ProfileFragment;
import frt.gurgur.theconfession.util.PreferencesHelper;
import frt.gurgur.theconfession.ui.main.MainFragment;
import frt.gurgur.theconfession.ui.user.login.LoginFragment;
import kotlin.jvm.functions.Function0;

import static frt.gurgur.theconfession.util.PreferencesHelper.EMPTY_USER_ID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , ProfileBottomSheetFragment.ItemClickListener, Navigator.NavigatorListener {



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
                    return new FavoritiesFragment();
                }
            },
            new Function0<Fragment>() {
                @Override
                public Fragment invoke() {
                    ProfileFragment fragment = new ProfileFragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt("userId", preferencesHelper.getUserId());
                    fragment.setArguments(arguments);
                    return fragment;
                }
            }
            );

    @Inject
    PreferencesHelper preferencesHelper;

    @BindView(R.id.toolbar_lay)
    View toolbar_lay;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.btnBack)
    public ImageButton btnBack;
    @BindView(R.id.btnProfileDetail)
    ImageButton btnProfileDetail;

    @BindView(R.id.navigation)
    public BottomNavigationView navigation;



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


    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setContentInsetsAbsolute(0, 0);
        btnBack.setOnClickListener(this);
        btnProfileDetail.setOnClickListener(this);
        btnBack.setVisibility(View.GONE);
    }

    public void initView(){
        initToolbar();

        int userId = preferencesHelper.getUserId();
        if (userId == EMPTY_USER_ID){
            multipleStackNavigator.start(new LoginFragment());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                multipleStackNavigator.goBack();
                break;
            case R.id.btnProfileDetail:
                showBottomSheet();
                break;

        }
    }


    public void initBackButton(Fragment fragment){
        if (fragment instanceof ProfileFragment){
            btnBack.setVisibility(View.VISIBLE);
        }else if (fragment instanceof MainFragment){
            btnBack.setVisibility(View.GONE);
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

                    if (multipleStackNavigator.getCurrentFragment() instanceof MainFragment){
                        ((MainFragment) multipleStackNavigator.getCurrentFragment()).recyclerView.smoothScrollToPosition(0);
                    }

                    return true;
                case R.id.navigation_explore:
                    multipleStackNavigator.switchTab(1);
                    return true;
                case R.id.navigation_fav:
                    multipleStackNavigator.switchTab(2);
                    return true;
                case R.id.navigation_profile:
                    multipleStackNavigator.switchTab(3);
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
                btnProfileDetail.setVisibility(View.GONE);
                break;
            case 1:
                navigation.setSelectedItemId(R.id.navigation_explore);
                btnProfileDetail.setVisibility(View.GONE);
                break;
            case 2:
                navigation.setSelectedItemId(R.id.navigation_fav);
                btnProfileDetail.setVisibility(View.GONE);
                break;
            case 3:
                navigation.setSelectedItemId(R.id.navigation_profile);
                btnProfileDetail.setVisibility(View.VISIBLE);
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
