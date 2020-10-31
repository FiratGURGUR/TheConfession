package frt.gurgur.theconfession.di.modules;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import frt.gurgur.theconfession.di.keys.ViewModelKey;
import frt.gurgur.theconfession.ui.main.MainViewModel;
import frt.gurgur.theconfession.ui.user.login.LoginViewModel;
import frt.gurgur.theconfession.ui.user.profile.ProfileViewModel;
import frt.gurgur.theconfession.ui.user.register.RegisterViewModel;

@Module
public abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel profileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel.class)
    public abstract ViewModel bindRegisterViewModel(RegisterViewModel registerViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);
}
