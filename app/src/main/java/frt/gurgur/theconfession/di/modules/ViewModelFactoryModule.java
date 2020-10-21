package frt.gurgur.theconfession.di.modules;

import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import frt.gurgur.theconfession.ui.ViewModelFactory;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory modelProvider);


}

