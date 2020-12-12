package frt.gurgur.theconfession.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import frt.gurgur.theconfession.ui.explore.ExploreFragment;
import frt.gurgur.theconfession.ui.favorities.FavoritiesFragment;
import frt.gurgur.theconfession.ui.main.MainFragment;
import frt.gurgur.theconfession.ui.main.StoryFragment;
import frt.gurgur.theconfession.ui.post.PostFragment;
import frt.gurgur.theconfession.ui.post.comments.CommentFragment;
import frt.gurgur.theconfession.ui.post.giphy.GiphyListFragment;
import frt.gurgur.theconfession.ui.user.login.LoginFragment;
import frt.gurgur.theconfession.ui.user.profile.UserFavoritedPostListFragment;
import frt.gurgur.theconfession.ui.user.profile.UserPostListFragment;
import frt.gurgur.theconfession.ui.user.profile.followpage.FollowFragment;
import frt.gurgur.theconfession.ui.user.profile.ProfileFragment;
import frt.gurgur.theconfession.ui.user.profile.followpage.FollowerListFragment;
import frt.gurgur.theconfession.ui.user.profile.followpage.FollowingListFragment;
import frt.gurgur.theconfession.ui.user.register.RegisterFragment;

@Module(includes = {PostModule.class})
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract MainFragment mainFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract LoginFragment loginFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract RegisterFragment registerFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ProfileFragment profileFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract PostFragment postFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract FollowFragment followFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract FollowerListFragment followerListFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract FollowingListFragment followingListFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract UserPostListFragment userPostListFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract UserFavoritedPostListFragment userFavoritedPostListFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract FavoritiesFragment favoritiesFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract ExploreFragment exploreFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract CommentFragment commentFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract GiphyListFragment giphyListFragment();

    @ContributesAndroidInjector(modules = {ViewModelModule.class})
    abstract StoryFragment storyFragment();
}