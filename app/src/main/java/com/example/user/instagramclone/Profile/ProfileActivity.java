package com.example.user.instagramclone.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.instagramclone.Models.Photo;
import com.example.user.instagramclone.Models.User;
import com.example.user.instagramclone.R;
import com.example.user.instagramclone.Utils.ViewComentsFragment;
import com.example.user.instagramclone.Utils.ViewPostFragment;
import com.example.user.instagramclone.Utils.ViewProfileFragment;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by user on 04/02/2018.
 */

public class ProfileActivity extends AppCompatActivity implements
        ProfileFragment.OnGridImageSelectedListener ,
        ViewPostFragment.OnCommentThreadSelectedListener, ViewProfileFragment.OnGridImageSelectedListener{


    private static final String TAG = "ProfileActivity";

    @Override
    public void OnCommentThreadSelectedListener(Photo photo) {
        Log.d(TAG, "OnCommentThreadSelectedListener: selected a comment thread.");

        ViewComentsFragment fragment = new ViewComentsFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.photo),photo);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(getString(R.string.view_componens_fragment));
        transaction.commit();
    }

    @Override
    public void onGridImageSelected(Photo photo, int activityNumber) {

         Log.d(TAG, "onGridSelected: selected an image gridview: " + photo.toString());

         ViewPostFragment fragment = new ViewPostFragment();
         Bundle args = new Bundle();
         args.putParcelable(getString(R.string.photo),photo);
         args.putInt(getString(R.string.activity_number), activityNumber);
         fragment.setArguments(args);

         FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         transaction.replace(R.id.container, fragment);
         transaction.addToBackStack(getString(R.string.view_post_fragment));
         transaction.commit();                                                                                                                                  

    }

    @Override
    public void onGridSelected(Photo photo, int activityNumber) {
        Log.d(TAG, "onGridSelected: selected an image gridview: " + photo.toString());

        ViewPostFragment fragment = new ViewPostFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.photo),photo);
        args.putInt(getString(R.string.activity_number), activityNumber);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(getString(R.string.view_post_fragment));
        transaction.commit();
    }

    private static final int ACTIVITY_NUM = 4;
    public static final int NUM_GRID_COLUMNS = 3;
    private Context mContext = ProfileActivity.this;
    private ProgressBar mProgressBar;
    private ImageView profilePhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started.");

        init();

    }

    private void init(){
        Log.d(TAG, "init: inflating " + getString(R.string.profile_fragment));

        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.calling_activity))){
            Log.d(TAG, "init: searching for user object attached as intent extra");
            if (intent.hasExtra(getString(R.string.intent_user))){
                User user = intent.getParcelableExtra(getString(R.string.intent_user));
                if (!user.getUser_id().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    Log.d(TAG, "init: inflating  view Profile");
                    ViewProfileFragment fragment = new ViewProfileFragment();
                    Bundle args = new Bundle();
                    args.putParcelable(getString(R.string.intent_user),
                            intent.getParcelableExtra(getString(R.string.intent_user)));
                    fragment.setArguments(args);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragment);
                    transaction.addToBackStack(getString(R.string.view_profile_fragment));
                    transaction.commit();
                }else {
                    Log.d(TAG, "init: inflating  Profile");
                    ProfileFragment fragment =  new ProfileFragment();
                    FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,fragment);
                    transaction.addToBackStack(getString(R.string.profile_fragment));
                    transaction.commit();
                }

            }else {
                Toast.makeText(mContext, "something went wrog", Toast.LENGTH_SHORT).show();
            }
        }else {
            Log.d(TAG, "init: inflating  Profile");
            ProfileFragment fragment =  new ProfileFragment();
            FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container,fragment);
            transaction.addToBackStack(getString(R.string.profile_fragment));
            transaction.commit();
        }
    }
}
