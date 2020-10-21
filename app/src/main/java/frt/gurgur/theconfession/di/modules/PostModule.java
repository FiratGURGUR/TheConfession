package frt.gurgur.theconfession.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import frt.gurgur.theconfession.data.remote.APIService;
import frt.gurgur.theconfession.data.remote.repo.ConfessionRepo;

@Module
public class PostModule {

    @Singleton
    @Provides
    static ConfessionRepo provideNoteRepo(APIService noteAPI){
        return new ConfessionRepo(noteAPI);
    }
}
