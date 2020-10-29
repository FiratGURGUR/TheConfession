package frt.gurgur.theconfession.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import frt.gurgur.theconfession.data.remote.APIService;
import retrofit2.Retrofit;

@Module
public class AppModule {

    @Provides
    @Singleton
    static APIService provideApi(Retrofit retrofit) {
        return retrofit.create(APIService.class);
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreference(Context context) {
        return context.getSharedPreferences("myShared", Context.MODE_PRIVATE);
    }

}

