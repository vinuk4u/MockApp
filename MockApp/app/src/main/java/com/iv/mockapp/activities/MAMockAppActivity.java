package com.iv.mockapp.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.iv.mockapp.R;
import com.iv.mockapp.fragments.MAScenario1Fragment;
import com.iv.mockapp.fragments.MAScenario2Fragment;
import com.iv.mockapp.utils.view.MAViewUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vineeth on 10/09/16
 */
public class MAMockAppActivity extends MABaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.version_textview)
    TextView mVersionTextView;

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    private MenuItem prevMenuItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mockapp_layout);

        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);

        // init toolbar
        setSupportActionBar(mToolbar);

        initNavDrawer();
    }

    private void initNavDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.app_name, R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setSelected(true);
        (prevMenuItem = mNavigationView.getMenu().getItem(0)).setChecked(true);

        loadVersion();

        // load first fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MAScenario1Fragment.newInstance())
                .commit();
    }

    private void loadVersion() {
        String versionName = null;
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (versionName != null) {
            final String version = getString(R.string.version) + " " + versionName;
            mVersionTextView.setText(version);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.scenario1) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MAScenario1Fragment.newInstance())
                    .commit();
            MAViewUtil.dismissSnackBar();
        } else if (id == R.id.scenario2) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MAScenario2Fragment.newInstance())
                    .commit();
        }

        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        }
        menuItem.setChecked(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        prevMenuItem = menuItem;

        return true;
    }
}
