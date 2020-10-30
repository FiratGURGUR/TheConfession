package frt.gurgur.theconfession.di;

import android.app.Application;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import frt.gurgur.theconfession.di.modules.ActivityBuilder;
import frt.gurgur.theconfession.di.modules.AppModule;
import frt.gurgur.theconfession.di.modules.FragmentBuildersModule;
import frt.gurgur.theconfession.di.modules.NetworkModule;
import frt.gurgur.theconfession.util.PreferencesHelper;
import frt.gurgur.theconfession.di.modules.ViewModelFactoryModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, FragmentBuildersModule.class, AppModule.class, ViewModelFactoryModule.class, NetworkModule.class, ActivityBuilder.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
    void inject(App app);
    SharedPreferences sharedPreferences();
    PreferencesHelper preferencesHelper();
}

