package com.kodrnja.javadevelopersongithub;


import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kodrnja.javadevelopersongithub.Model.UserModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import com.google.gson.Gson;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    private ListView lvUsers;
    int currentPage=1;
    userAdapter adapter;
    private String URL = "https://api.github.com/search/users?q=all&score%3E+language:java&type=user&per_page=20";
    ProgressDialog pDialog;
    ArrayList<UserModel> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        lvUsers = (ListView)findViewById(R.id.lvUsers);

        result=new ArrayList<UserModel>();

        // treba dohvatiti prvu stranu; 10 elemenata

        DefaultHttpClient httpClient = new DefaultHttpClient();







        Button btnLoad = new Button(MainActivity.this);
        btnLoad.setText("Load more");

        lvUsers.addFooterView(btnLoad);

        btnLoad.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                new LoadMore.execute();
            }

    });

        lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // list item click opens a new detailed activity
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserModel usermodel = result.get(position); // getting the model
                final View AvatarView = findViewById(R.id.ivAvatar);
                Intent intent = new Intent(MainActivity.this, Details.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,AvatarView,"image");
                intent.putExtra("usermodel", new Gson().toJson(usermodel));
                startActivity(intent, options.toBundle());
            }
        });


    }





























    public class userAdapter extends ArrayAdapter{
        public List<UserModel> usermodellist;
        private int resource;
        private LayoutInflater inflater;
        public userAdapter( Context context, int resource, List<UserModel> objects) {
            super(context, resource, objects);
            usermodellist = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(int position,  View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {holder = new ViewHolder();
                convertView = inflater.inflate(resource, null);
                holder.ivAvatar = (ImageView)convertView.findViewById(R.id.ivAvatar);
                holder.tvUsername = (TextView)convertView.findViewById(R.id.tvUsername);
                holder.tvRegdate = (TextView)convertView.findViewById(R.id.tvRegdate);
                holder.tvFollowers = (TextView)convertView.findViewById(R.id.tvFollowers);
                //holder.btnLoad =(Button)convertView.findViewById(R.id.btnLoad);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }




            holder.tvUsername.setText(usermodellist.get(position).getUsername());
           /* holder.tvFollowers.setText("Number of followers:" + usermodellist.get(position).getFollowers());
            holder.tvRegdate.setText("Date of registration:" + usermodellist.get(position).getReg_date());
*/
            //  holder.tvUsername.setText("Username:" );
            holder.tvFollowers.setText("Number of followers:"+ usermodellist.get(position).getFollowers());
            holder.tvRegdate.setText("Registration date:"+usermodellist.get(position).getReg_date());

            final ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);

            final ViewHolder finalHolder = holder;
            ImageLoader.getInstance().displayImage(usermodellist.get(position).getAvatar(), holder.ivAvatar,new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                    finalHolder.ivAvatar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                    finalHolder.ivAvatar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                    finalHolder.ivAvatar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);
                    finalHolder.ivAvatar.setVisibility(View.INVISIBLE);
                }
            });



            return convertView;
        }


        class ViewHolder{
            private ImageView ivAvatar;
            private TextView tvUsername;
            private TextView tvFollowers;
            private TextView tvRegdate;
            // private Button btnLoad;

        }










    }
