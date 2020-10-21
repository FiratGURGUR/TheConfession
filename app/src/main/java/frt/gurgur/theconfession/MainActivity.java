package frt.gurgur.theconfession;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import frt.gurgur.theconfession.databinding.ActivityMainBinding;
import frt.gurgur.theconfession.ui.confession.ConfessionListFragment;
import frt.gurgur.theconfession.ui.user.login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setFragment(R.id.container, new LoginFragment());
    }


    public void setFragment(int layoutId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(layoutId, fragment)
                .commit();
    }

}
