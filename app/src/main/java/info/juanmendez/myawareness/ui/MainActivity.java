package info.juanmendez.myawareness.ui;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import info.juanmendez.myawareness.OutAndAboutReceiver;
import info.juanmendez.myawareness.R;
import info.juanmendez.myawareness.dependencies.AwarenessClient;
import info.juanmendez.myawareness.dependencies.SnackMePlease;

/**
 * This activity lists buttons to open each fragment for learning Google Awareness
 */
@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @Bean
    AwarenessClient mAwarenessClient;

    @Bean
    SnackMePlease mSnackmeplease;

    @AfterViews
    void afterViews(){
        mSnackmeplease.setSnackView( findViewById(R.id.main_coordinatorLayout) );
    }

    @Override
    public void onResume(){
        super.onResume();

        boolean shouldOpenBack = getIntent().getBooleanExtra(OutAndAboutReceiver.FENCE_KEY, false);

        if( shouldOpenBack ){
            mainBackComboFence();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Click
    void mainShowHeadphoneSnapshot(){
        showFragment( HeadphoneSnapshotFragment_.builder().build(), "headphoneSnapshot" );
    }

    @Click
    void mainShowHeadphoneFence(){
        showFragment( HeadphoneFenceFragment_.builder().build(), "headphoneFence" );
    }

    @Click
    void mainShowLocationSnapshot(){
        showFragment( LocationSnapshotFragment_.builder().build(), "locationSnapshot" );
    }

    @Click
    void mainComboFence(){
        showFragment( ComboFenceFragment_.builder().build(), "mComboParam" );
    }

    @Click
    void mainBackComboFence(){
        showFragment( BackComboFenceFragment_.builder().build(), "backComboFence" );
    }

    private void showFragment(@NonNull Fragment fragment, @NonNull  String tag ) {
        FragmentManager fm = getSupportFragmentManager();

        //for simplicity we kill, and make the fragment once again.
        Fragment prevFragment = fm.findFragmentByTag( tag );

        if( prevFragment != null  ){
            fm.beginTransaction()
               .remove( prevFragment )
               .commit();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, fragment)
                .addToBackStack(null)
                .commit();
    }
}