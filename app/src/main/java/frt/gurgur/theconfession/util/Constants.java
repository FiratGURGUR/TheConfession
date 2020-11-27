package frt.gurgur.theconfession.util;

public class Constants {


    public static final String BASE_URL = "http://10.0.2.2/social/v1/";

    public static final String GET_USER = "user/getUser.php";
    public static final String LOGIN = "user/login.php";
    public static final String REGISTER = "user/createUser.php";
    public static final String POST_LIST = "post/getPostList.php";
    public static final String SHARED_POST_LIST = "post/getSharedPost.php";
    public static final String FAVORITED_POST_LIST = "post/getFavoritedPost.php";
    public static final String POST_CREATE = "post/createPost.php";
    public static final String POST_CREATE_WITH_IMAGE = "post/upload.php";
    public static final String GET_FOLLOWERS_LIST = "follow/getFollowers.php";
    public static final String GET_FOLLOWING_LIST = "follow/getFollowings.php";
    public static final String POST_FAV = "favori/addFavori.php";
    public static final String COMMENT_LIST = "comment/gelAllComments.php";
    public static final String COMMENT_ADD = "comment/addComment.php";




    public static final String GENERATED_PHOTO_URL = "https://ui-avatars.com/api/size=256?name=";
    public static final String DEFAULT_COVERPHOTO_URL = "https://timelinecovers.pro/facebook-cover/download/photography-city-lights-facebook-cover.jpg";

    public static String generateUserPhoto(String fullName){
        int idx = fullName.lastIndexOf(' ');
        String firstName = fullName.substring(0, idx);
        String lastName  = fullName.substring(idx + 1);
        return GENERATED_PHOTO_URL + firstName + "+" + lastName;
    }



    public static final int FAV_DELETED = 100;
    public static final int FAV_ADDED = 110;
    public static final int FAV_ERROR = 0;

}
