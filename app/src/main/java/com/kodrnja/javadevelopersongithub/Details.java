package com.kodrnja.javadevelopersongithub;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kodrnja.javadevelopersongithub.Model.UserModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by Iva Kodrnja on 29.4.2017..
 */

public class Details extends AppCompatActivity {

    private ImageView ivAvatar;
    private TextView tvUsername;
    private TextView tvEmail;
    private TextView tvRegdate;
    private TextView tvFollowers;

    private TextView tvLocation;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        // Showing and Enabling clicks on the Home/Up button
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // setting up text views and stuff
        setUpUIViews();

        // recovering data from MainActivity, sent via intent
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String json = bundle.getString("usermodel"); // getting the model from MainActivity send via extras
            UserModel usermodel = new Gson().fromJson(json, UserModel.class);

            // Then later, when you want to display image
            ImageLoader.getInstance().displayImage(usermodel.getAvatar(), ivAvatar, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);
                }
            });

            tvUsername.setText(usermodel.getUsername());
            if (usermodel.getEmail()!=null){
                tvEmail.setText(usermodel.getEmail());}
            else{
                tvEmail.setText("Email unknown");
            }
            tvFollowers.setText("Number of followers: " + usermodel.getFollowers().toString());
            tvRegdate.setText("Registration date: "+usermodel.getReg_date());
            if (usermodel.getLocation()!=null){
                tvLocation.setText("Location: "+usermodel.getLocation());}
            else{
                tvLocation.setText("Location unknown");
            }


            final TextView email = (TextView) findViewById(R.id.tvEmail);


            if (email.getText()!="Email unknown"){
                email.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (MotionEvent.ACTION_UP == event.getAction()) {
                            String em = email.getText().toString();
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                            emailIntent.setData(Uri.parse("mailto:" + em));
                            //emailIntent.putExtra(Intent.EXTRA_EMAIL, R.id.editText);
                            //emailIntent.putExtra(Intent.EXTRA_SUBJECT,"subject");

                            PackageManager packageManager = getPackageManager();
                            List activities = packageManager.queryIntentActivities(emailIntent, PackageManager.MATCH_DEFAULT_ONLY);
                            boolean isIntentSafe = activities.size() > 0;

                            if (isIntentSafe == true) {
                                startActivity(emailIntent);
                            }
                        }
                        return true; // return is important...
                    }
                });}
        }

    }

    private void setUpUIViews() {
        ivAvatar = (ImageView)findViewById(R.id.ivAvatar);
        tvUsername = (TextView)findViewById(R.id.tvUsername);
        tvEmail = (TextView)findViewById(R.id.tvEmail);
        tvFollowers = (TextView)findViewById(R.id.tvFollowers);
        tvRegdate = (TextView)findViewById(R.id.tvRegdate);
        tvLocation = (TextView)findViewById(R.id.tvLocation);


        progressBar = (ProgressBar)findViewById(R.id.progressBar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }






}