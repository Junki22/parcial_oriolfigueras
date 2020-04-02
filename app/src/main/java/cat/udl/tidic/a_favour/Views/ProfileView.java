package cat.udl.tidic.a_favour.Views;
import cat.udl.tidic.a_favour.BlankFragment;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import cat.udl.tidic.a_favour.RecyclerViewManager;
import cat.udl.tidic.a_favour.models.ProfileViewModel;
import cat.udl.tidic.a_favour.R;
import cat.udl.tidic.a_favour.databinding.ActivityProfileBinding;
import cat.udl.tidic.a_favour.models.UserModel;

public class ProfileView extends AppCompatActivity
{

    boolean dev;
    ProfileViewModel profileViewModel;
    RelativeLayout loadingbar;
    RecyclerViewManager recyclerManager;

    //Layout elements
    ConstraintLayout layout;
    ImageView backArrow;
    TextView userName;
    RatingBar stars;
    ImageView profilImage;
    TextView favoursInfo;
    TextView userLocation;
    TextView showLocation;
    String favoursDone;
    String timesHelped;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        dev = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Binding Data
        ActivityProfileBinding activityProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        profileViewModel = new ProfileViewModel();
        activityProfileBinding.setProfileViewModel(profileViewModel);

        //The recycle Manager
        recyclerManager = new RecyclerViewManager(getSupportFragmentManager(), ProfileView.this);

        //Get all the layout data
        getAllActivityData();
        setUpRecyclerView();
        getRecyclerData();
        setUpProfileListeners();
    }


    public void backArrowAction(View v){
        Intent intent = new Intent (v.getContext(), LoginView.class);
        startActivityForResult(intent, 0);
    }


    private void setUpRecyclerView()
    {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(recyclerManager);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void getAllActivityData()
    {
        layout = findViewById(R.id.constraint);
        loadingbar = findViewById(R.id.loadingPanel);
        backArrow = findViewById(R.id.back_arrow);
        userName = findViewById(R.id.name);
        stars = findViewById(R.id.stars);
        profilImage = findViewById(R.id.profile_image);
        favoursInfo = findViewById(R.id.favoursInfo);
        userLocation = findViewById(R.id.user_location);
        showLocation = findViewById(R.id.show_location);

        if (dev) {loadingbar.setVisibility(View.GONE);}

        //favoursBtn = findViewById(R.id.favours_btn);
        //favouritesBtn = findViewById(R.id.favourites_btn);
        //opinionsBtn = findViewById(R.id.opinions_btn);
        //Falta crear tot lo relacionat amb els anuncis que ha publicat
    }

    private void getRecyclerData()
    {
        for (int i = 0; i < tabLayout.getTabCount(); i++)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(recyclerManager.getTabView(i));
        }
    }
    private void setUpProfileListeners()
    {
        profileViewModel.getUserProfile().observe(this, this::onGetUserData);
    }

    private void onGetUserData(UserModel u)
    {

        if (dev)
        {
            loadingbar.setVisibility(View.GONE);
        }
        else
        {
            if(u == null)
            {
                Log.d("THE USER IS NULL", "ASD");
                loadingbar.setVisibility(View.GONE);
                setErrorLayout();
            }
            else
            {

                loadingbar.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                //El nom de l'usuari
                userName.setText(profileViewModel.getUsername());

                //Poso les estrelles necessaries
                stars.setRating(profileViewModel.getStars());

                //Informació dels facvors que ha fet i que ha rebut
                favoursDone = profileViewModel.getFavoursDone();
                timesHelped = profileViewModel.getTimesHelped();
                String favoursDoneString = getResources().getString(R.string.favoursDone);
                String timesHelpedString = getResources().getString(R.string.timesHelped);
                favoursInfo.setText(favoursDone + " " + favoursDoneString + ", " + timesHelped +  " " + timesHelpedString);

                //Informació de l'ubicació de l'usuari
                userLocation.setText(profileViewModel.getLocation());
            }
        }
    }


    public void setErrorLayout()
    {
        //Si falla la connexió s'haura de posar un layout de "error"
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.dialogMessage).setTitle(R.string.dialogTitle);

        builder.setPositiveButton(R.string.retry, (dialog, id) ->
        {
            profileViewModel.getUser();
            loadingbar.setVisibility(View.VISIBLE);

        });
        builder.setNegativeButton(R.string.cancel, (dialog, id) ->
        {
            dialog.cancel();
            //TODO : Go to a default layout
            Intent intent = new Intent (this, LoginView.class);
            startActivityForResult(intent, 0);
        });

        AlertDialog dialog = builder.show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("" + item.getItemId() ,"asdDDDDDDDDDD");
        //if (id == R.id.action_settings) {
        //return true;
        //}

        return super.onOptionsItemSelected(item);
    }


}

