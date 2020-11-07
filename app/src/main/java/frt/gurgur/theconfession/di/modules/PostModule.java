package frt.gurgur.theconfession.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import frt.gurgur.theconfession.data.remote.APIService;
import frt.gurgur.theconfession.data.remote.repo.MainRepo;
import frt.gurgur.theconfession.data.remote.repo.PostRepo;
import frt.gurgur.theconfession.data.remote.repo.UserRepo;

@Module
public class PostModule {

    @Singleton
    @Provides
    static UserRepo provideUserRepo(APIService api){
        return new UserRepo(api);
    }

    @Singleton
    @Provides
    static MainRepo provideMainRepo(APIService api){
        return new MainRepo(api);
    }

    @Singleton
    @Provides
    static PostRepo providePostRepo(APIService api){
        return new PostRepo(api);
    }
}
