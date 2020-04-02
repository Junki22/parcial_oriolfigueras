package cat.udl.tidic.a_favour.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatSideChannelService;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cat.udl.tidic.a_favour.R;
import cat.udl.tidic.a_favour.models.LoginViewModel;
import cat.udl.tidic.a_favour.models.ProfileViewModel;
import cat.udl.tidic.a_favour.models.UserModel;
import cat.udl.tidic.a_favour.preferences.PreferencesProvider;

public class LoginView extends AppCompatActivity {

    private EditText userTxt;
    private EditText pwdTxt;
    private Button registerBtn;
    private Button loginBtn;
    private TextView title;
    private SharedPreferences preferences;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniComponents();
        checkLogin();
        setButtonListeners();
    }

    public void iniComponents()
    {
        loginViewModel = new LoginViewModel();
        preferences = PreferencesProvider.providePreferences();

        userTxt = findViewById(R.id.user_txt);
        pwdTxt = findViewById(R.id.pwd_txt);
        registerBtn = findViewById(R.id.register_btn);
        loginBtn = findViewById(R.id.login_btn);
        title = findViewById(R.id.title);
        String a = getColoredSpanned("A","#ff3b5b");
        title.setText(Html.fromHtml(a + " Favour"));
    }

    public void checkLogin()
    {
        //Comprobar si hi ha token guardat
        if (PreferencesProvider.existToken("token")) { openProfile(); }
    }

    public void setButtonListeners()
    {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnRegister(v);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnLogin(v);
            }
        });
    }

    public void openProfile()
    {
        Intent intent = new Intent (getApplicationContext(), ProfileView.class);
        startActivity(intent);
    }

    private String getColoredSpanned(String text, String color)
    {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    public void clickOnRegister(View v)
    {
        Intent intent = new Intent (v.getContext(), RegisterView.class);
        startActivityForResult(intent, 0);
    }

    public void clickOnLogin(View v)
    {
        String username = userTxt.getText().toString();
        String password = pwdTxt.getText().toString();
        loginViewModel.checkCorrectLogin(username, password, this);
    }

    public void sendMessage(String message)
    {
        Toast.makeText(LoginView.this,message, Toast.LENGTH_SHORT).show(); //enviem missatge a la pantalla
    }


}
