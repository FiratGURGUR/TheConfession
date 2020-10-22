package frt.gurgur.theconfession;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import frt.gurgur.theconfession.databinding.ActivityMainBinding;
import frt.gurgur.theconfession.ui.confession.ConfessionListFragment;
import frt.gurgur.theconfession.ui.user.login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private boolean firstStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        pushFragment(new LoginFragment(), LoginFragment.FRAGMENT_TAG);
    }






    public void pushFragment(@NonNull Fragment fragment,
                             @NonNull String fragmentTag) {
        pushFragment(fragment, fragmentTag, true);
    }

    public void pushFragment(@NonNull Fragment fragment,
                             @NonNull String fragmentTag, boolean animationActive) {

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

    }

    public void pushFragment(@NonNull Fragment fragment,
                             @NonNull String fragmentTag, boolean animationActive, String animTransition) {
        if (!firstStart) {
            if (animationActive) {
                if (animTransition.equals("downToUp")) {

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

    }

}
