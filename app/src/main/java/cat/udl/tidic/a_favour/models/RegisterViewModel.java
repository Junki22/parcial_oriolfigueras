package cat.udl.tidic.a_favour.models;

import android.content.Intent;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Objects;

import cat.udl.tidic.a_favour.RetrofitClientInstance;
import cat.udl.tidic.a_favour.UserServices;
import cat.udl.tidic.a_favour.Utils;
import cat.udl.tidic.a_favour.Views.LoginView;
import cat.udl.tidic.a_favour.Views.ProfileView;
import cat.udl.tidic.a_favour.Views.RegisterView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel
{

    UserServices userService;
    public RegisterViewModel()
    {
        userService = RetrofitClientInstance.
                getRetrofitInstance().create(UserServices.class);
    }

    public void registerUser(String user, String password1, String password2, String email, String phone, RegisterView extra){
        if (!password1.equals(password2))
        { //comprovem que les contrasenyes siguin iguals
            sendMessage("Las contraseñas no coincideixen", extra);
        }
        else if(password1.length() < 5)
        {
            sendMessage("La contraseña tiene que tener mínimo 5 caracteres", extra);
        }
        else
            {
            // Course API requires passwords in sha-256 in passlib format so:
            String p = password1;
            String salt = "16";
            String encodeHash = Utils.encode(p,salt,29000);
            System.out.println("PASSWORD_ENCRYPTED " + encodeHash);

            JsonObject user_json = new JsonObject();
            user_json.addProperty("username", user);
            user_json.addProperty("email", email);
            user_json.addProperty("phone", phone);
            user_json.addProperty("password", encodeHash);

            Call<Void> call = userService.registerUser3(user_json);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response)
                {
                    if (response.code() == 200)
                    {
                        sendMessage("Usuario registrado", extra);
                    }
                    else
                        {
                        try
                        { //Atrapar error usuari existent / correu existent
                            sendMessage(Objects.requireNonNull(response.errorBody().string()), extra);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    sendMessage("Error", extra);
                }
            });
        }
    }

    public void sendMessage(String message, RegisterView extra)
    {
        Toast.makeText(extra,message, Toast.LENGTH_SHORT).show(); //enviem missatge a la pantalla
    }
}
