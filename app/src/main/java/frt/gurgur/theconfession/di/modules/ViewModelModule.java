package frt.gurgur.theconfession.di.modules;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import frt.gurgur.theconfession.di.keys.ViewModelKey;
import frt.gurgur.theconfession.ui.confession.ConfessionViewModel;
import frt.gurgur.theconfession.ui.user.UserViewModel;
import frt.gurgur.theconfession.ui.user.login.LoginViewModel;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ConfessionViewModel.class)
    public abstract ViewModel bindPostViewModel(ConfessionViewModel postViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    public abstract ViewModel bindUserViewModel(UserViewModel postViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindLoginViewModel(LoginViewModel postViewModel);

}
