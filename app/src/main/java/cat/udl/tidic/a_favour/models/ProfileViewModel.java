package cat.udl.tidic.a_favour.models;

import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cat.udl.tidic.a_favour.RetrofitClientInstance;
import cat.udl.tidic.a_favour.UserServices;
import cat.udl.tidic.a_favour.Utils;
import cat.udl.tidic.a_favour.Views.LoginView;
import cat.udl.tidic.a_favour.Views.RegisterView;
import cat.udl.tidic.a_favour.preferences.PreferencesProvider;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel
{
    //private UserModel user = new UserModel();
    private UserServices userService;
    private SharedPreferences mPreferences;
    private String token;
    private UserModel userModel;

    //TODO: He introduït errors, has d'arreglar-los
    //TODO: Explica'm amb les teves paraules, i en l'ambit de la vostra aplicació
    // la diferencia entre LiveData i MutableLive data que heu
    // fet anar en la línia 43 i 44,


    public MutableLiveData<UserModel> user = new MutableLiveData<>();
    public LiveData<UserModel> getUserProfile(){ return user; }

    public ProfileViewModel()
    {
        mPreferences = PreferencesProvider.providePreferences();
        token = mPreferences.getString("token", "");
        Log.d("Token:", token);
        userModel = new UserModel();
        getUser();
    }

   public void getUser()
   {
       Map<String, String> map = new HashMap<>();
       Log.d("IS TOKEN EMPTY? TOKEN = ", mPreferences.getString("token", ""));
       map.put("Authorization", mPreferences.getString("token", ""));

       //Esta hardcodejat perque falta fer la gestió d'errors al agafar els valors
       //De moment mostra les dades de l'usuari 1
       //map.put("Authorization", "656e50e154865a5dc469b80437ed2f963b8f58c8857b66c9bf");

       userService = RetrofitClientInstance.getRetrofitInstance().create(UserServices.class);
       Call<Void> call = userService.getUserProfile(map);

       call.enqueue(new Callback<Void>()
       {
           @Override
           public void onResponse(Call<Void> call, Response<Void> response)
           {
               try
               {
                   user.setValue(response.body());
               }
               catch (Exception e) { Log.e("ProfileViewModel", e.getMessage() + "ERROR");}
           }

           @Override
           public void onFailure(Call<Void> call, Throwable t)
           {

               user.setValue(null);
               Log.e("ProfileViewModel",  t.getMessage());
               //Toast.makeText(ProfileViewModel.this, t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
   }

   public String getUsername()
   {
       return this.user.getValue().getUsername();
   }

   public String getPassword(){
        return this.user.getValue().getPassword();
   }

    public float getStars()
    {
        return this.user.getValue().getStars();
    }

    public String getFavoursDone()
    {
        int favoursDone = this.user.getValue().getFavoursDone();
        return Integer.toString(favoursDone);
    }

    public String getTimesHelped()
    {
        int timesHelped = this.user.getValue().getTimesHelped();
        return Integer.toString(timesHelped);
    }

    public String  getLocation()
    {
        String location = this.user.getValue().getLocation() == null ? "No location" : this.user.getValue().getLocation();
        return location;
    }

    public void showLocationBtn()
    {
        Log.d("Profile", "S'ha premut l'opció SHOW LOCATION");
    }
    public void backArrowBtn()
    {
        Log.d("Profile", "S'ha premut l'opció DE TIRAR ENRRERE");
    }
    public void favoursBtn() { Log.d("Profile", "S'ha premut l'opció DE FAVOURS"); }
    public void favouritesBtn() { Log.d("Profile", "S'ha premut l'opció FAVOURITES"); }
    public void opinionsBtn()
    {
        Log.d("Profile", "S'ha premut l'opció OPINIONS");
    }


}
