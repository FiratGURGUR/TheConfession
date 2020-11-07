package frt.gurgur.theconfession.util;

public class Constants {


    public static final String BASE_URL = "http://10.0.2.2/social/v1/";

    public static final String GET_USER = "user/getUser.php";
    public static final String LOGIN = "user/login.php";
    public static final String REGISTER = "user/createUser.php";
    public static final String POST_LIST = "post/getPostList.php";
    public static final String POST_CREATE = "post/createPost.php";



    public static final String GENERATED_PHOTO_URL = "https://ui-avatars.com/api/size=256?name=";
    public static final String DEFAULT_COVERPHOTO_URL = "https://timelinecovers.pro/facebook-cover/download/photography-city-lights-facebook-cover.jpg";

    public static String generateUserPhoto(String fullName){
        int idx = fullName.lastIndexOf(' ');
        String firstName = fullName.substring(0, idx);
        String lastName  = fullName.substring(idx + 1);
        return GENERATED_PHOTO_URL + firstName + "+" + lastName;
    }

}
