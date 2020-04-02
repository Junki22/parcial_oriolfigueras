package cat.udl.tidic.a_favour.models;

import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import cat.udl.tidic.a_favour.RetrofitClientInstance;
import cat.udl.tidic.a_favour.UserServices;
import cat.udl.tidic.a_favour.Views.LoginView;
import cat.udl.tidic.a_favour.Views.RegisterView;
import cat.udl.tidic.a_favour.preferences.PreferencesProvider;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel
{  //private UserModel user = new UserModel();

    private UserServices userService;
    private SharedPreferences mPreferences;


    public LoginViewModel()
    {
        mPreferences = PreferencesProvider.providePreferences();
        userService = RetrofitClientInstance.
                getRetrofitInstance().create(UserServices.class);
    }

    public void checkCorrectLogin(String username, String password, LoginView loginView)
    {

        String tokenDecoded = username + ":" + password;
        byte[] bytes = tokenDecoded.getBytes(StandardCharsets.UTF_8);
        String tokenAux = Base64.encodeToString(bytes, Base64.DEFAULT);
        //mPreferences.edit().putString("token", tokenAux).apply();
        tokenAux = ("Authentication: " + tokenAux).trim();
        Call<ResponseBody> call = userService.createToken(tokenAux);
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.body() != null){
                    try {
                        String token = response.body().string().split(":")[1];
                        token = token.substring(2, token.length()-2);
                        setToken(token);
                        Log.d("Login completed", token);
                        loginView.openProfile();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else{
                    try {
                        Log.d("Login error", response.errorBody().string());
                        loginView.sendMessage("Error on Login");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Login failed", t.getMessage());
                loginView.sendMessage("Error on Login");
            }
        });
    }

    public void setToken (String token)
    {
        mPreferences.edit().putString("token", token).apply();
    }
}
