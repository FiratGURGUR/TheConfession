package frt.gurgur.theconfession.util;

import android.content.SharedPreferences;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import frt.gurgur.theconfession.model.user.User;
import frt.gurgur.theconfession.model.user.UserResponse;

@Singleton
public class PreferencesHelper {

    public static final int EMPTY_USER_ID =0;
    public static final String USER_ID = "userId";
    public static final String USER_USERNAME = "userName";
    public static final String USER_FULLNAME = "userFullName";
    public static final String USER_PHOTO = "userPhoto";
    public static final String USER_COVERPHOTO = "userCoverPhoto";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_POST_COUNT = "userPostCount";
    public static final String USER_FOLLOWER_COUNT = "userFolloweCount";
    public static final String USER_FOLLOWING_COUNT = "userFollowingCount";
    public static final String USER_ABOUT = "userAbout";

    private final SharedPreferences preferences;

    @Inject
    PreferencesHelper(SharedPreferences sharedPreferences) {
        preferences = sharedPreferences;
    }


    public void setUser(@Nonnull String key, @Nonnull UserResponse user){
        putInt(USER_ID,user.getUser().getId());
        putString(USER_USERNAME,user.getUser().getUsername());
        putString(USER_FULLNAME,user.getUser().getFullname());
        putString(USER_PHOTO,user.getUser().getPhoto());
        putString(USER_COVERPHOTO,user.getUser().getCoverphoto());
        putString(USER_EMAIL,user.getUser().getEmail());
        putInt(USER_POST_COUNT,user.getUser().getPostCount());
        putInt(USER_FOLLOWER_COUNT,user.getUser().getFollowerCount());
        putInt(USER_FOLLOWING_COUNT,user.getUser().getFollowingCount());
        putString(USER_ABOUT,user.getUser().getAbout());
    }

    public User getUser(){
        User user = new User();

        user.setId(getUserId());
        user.setUsername(getUserUsername());
        user.setFullname(getUserFullname());
        user.setPhoto(getPhoto());
        user.setCoverphoto(getUserCoverphoto());
        user.setEmail(getUserEmail());
        user.setPostCount(getPostCount());
        user.setFollowerCount(getFollowerCount());
        user.setFollowingCount(getFollowingCount());
        user.setAbout(getAbout());

        return user;
    }


    public int getUserId(){
        return preferences.getInt(USER_ID,EMPTY_USER_ID);
    }

    public String getUserUsername(){
        return preferences.getString(USER_USERNAME,"");
    }

    public String getUserFullname(){
        return preferences.getString(USER_FULLNAME,"");
    }

    public String getPhoto(){
        return preferences.getString(USER_PHOTO,"");
    }

    public String getUserCoverphoto(){
        return preferences.getString(USER_COVERPHOTO,"");
    }

    public String getUserEmail(){
        return preferences.getString(USER_EMAIL,"");
    }

    public int getPostCount(){
        return preferences.getInt(USER_POST_COUNT,0);
    }

    public int getFollowerCount(){
        return preferences.getInt(USER_FOLLOWER_COUNT,0);
    }

    public int getFollowingCount(){
        return preferences.getInt(USER_FOLLOWING_COUNT,0);
    }

    public String getAbout(){
        return preferences.getString(USER_ABOUT,"");
    }

    public void putString(@Nonnull String key, @Nonnull String value) {
        preferences.edit().putString(key, value).apply();
    }

    public String getString(@Nonnull String key) {
        return preferences.getString(key, "");
    }

    public void putBoolean(@Nonnull String key, @Nonnull boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(@Nonnull String key) {
        return preferences.getBoolean(key, false);
    }

    public void putInt(@Nonnull String key, @Nonnull int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public int getInt(@Nonnull String key) {
        return preferences.getInt(key, -1);
    }

    public void clear() {
        preferences.edit().clear().apply();
    }
}