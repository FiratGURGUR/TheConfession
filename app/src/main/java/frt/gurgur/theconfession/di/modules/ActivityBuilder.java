package frt.gurgur.theconfession.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import frt.gurgur.theconfession.MainActivity;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector()
    abstract MainActivity bindMainActivity();

}