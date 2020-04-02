package cat.udl.tidic.a_favour;

import com.google.gson.JsonObject;

import java.util.Map;

import cat.udl.tidic.a_favour.models.UserModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserServices
{

    //@Headers("Authorization:656e50e154865a5dc469b80437ed2f963b8f58c8857b66c9bf")
    @GET("/account/profile")
    Call<UserModel> getUserProfile(@HeaderMap Map<String, String> headers);


    @Headers({"Content-Type:application/json"})
    @POST("/users/register")
    Call<Void> registerUser(@Body UserModel user);

    @Headers({"Content-Type:application/json"})
    @POST("/users/register")
    Call<Void> registerUser2(@Body String userJson);

    @Headers({"Content-Type:application/json"})
    @POST("/users/register")
    Call<Void> registerUser3(@Body JsonObject userJson);



    Call<UserModel> getUserProfile();

    @POST ("/account/create_token")
    Call<ResponseBody> createToken(@Header ("Authorization") String tokenAuth);

}
