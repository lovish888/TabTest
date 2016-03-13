package xyz.wheretopark.tabtest;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import java.lang.*;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

import com.parse.ParseUser;

public class second extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabHost tabHost;
    //Calendar c = Calendar.getInstance();
    //int hour = c.get(Calendar.HOUR_OF_DAY);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabHost = (TabHost)findViewById(R.id.tabhost);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState); // state will be bundle your activity state which you get in onCreate

        tabHost.setup(mLocalActivityManager);

        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");

        tab2.setIndicator("Corporate");//Name of the Tab.
        tab2.setContent(new Intent(this, corporateSelect.class));
        tab1.setIndicator("Public");
        tab1.setContent(new Intent(this, publicSelect.class));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
    }

    //Exit the Activity on pressing back button twice.

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 1000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Toast.makeText(second.this, "Feature Coming Soon", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(second.this,MyProfile.class));

        } else if (id == R.id.nav_gallery) {
            Toast.makeText(second.this, "Feature Coming Soon", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_slideshow) {
            Intent market = new Intent(Intent.ACTION_VIEW);
            market.setData(Uri.parse("market://details?id=xyz.wheretopark.tabtest&hl=en"));
            startActivity(Intent.createChooser(market,"Launch Market"));

        } else if (id == R.id.nav_manage) {

            // Logging out and Clearing user data via shared Preference. Maybe needed in future.
            /*UserLocalStore  userLocalStore;
            userLocalStore = new UserLocalStore(this);
            userLocalStore.clearUserData();
            userLocalStore.setUserLoggedIn(false);
            */
            ParseUser.logOut();
            startActivity(new Intent(second.this, LoginActivity.class));
            //Toast.makeText(second.this, "Feature Coming Soon", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "I am using this awesome android app.";
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody +"\nDownload: https://play.google.com/store/apps/details?id=xyz.wheretopark.tabtest&hl=en");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));


        } else if (id == R.id.nav_send) {
            Intent contact = new Intent(Intent.ACTION_SEND);
            contact.setData(Uri.parse("mailto:"));
            String[] to = {"hello@wheretopark.xyz"};
            contact.putExtra(Intent.EXTRA_EMAIL, to);

            // Name of specification for email.
            contact.setType("message/rfc822"); //To handle the putExtra methods. MIME type of the email. App will crash otherwise.
            startActivity(Intent.createChooser(contact, "Send Email"));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
