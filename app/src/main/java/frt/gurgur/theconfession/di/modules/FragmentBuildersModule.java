package frt.gurgur.theconfession.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import frt.gurgur.theconfession.ui.confession.ConfessionListFragment;
import frt.gurgur.theconfession.ui.user.login.LoginFragment;
import frt.gurgur.theconfession.ui.user.register.RegisterFragment;

@Module(includes = {PostModule.class})
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ConfessionListFragment confessionListFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract LoginFragment loginFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract RegisterFragment registerFragment();
}