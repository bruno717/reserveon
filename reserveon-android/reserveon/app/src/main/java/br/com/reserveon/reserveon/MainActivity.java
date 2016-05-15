package br.com.reserveon.reserveon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import br.com.reserveon.reserveon.fragments.RestaurantsFragment;
import br.com.reserveon.reserveon.models.User;
import br.com.reserveon.reserveon.models.managers.AuthManager;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {

    @Bind(R.id.reserveon_toolbar)
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();
        setupNavigationDrawer(savedInstanceState);
        showFragment();
    }

    private void setupNavigationDrawer(Bundle bundle) {

        View view = getLayoutInflater().inflate(R.layout.navigation_drawer_menu_header, null);
        TextView textViewName = (TextView) view.findViewById(R.id.navigation_drawer_header_user_name);
        TextView textViewEmail = (TextView) view.findViewById(R.id.navigation_drawer_header_user_email);
        User user = AuthManager.getUser();
        if (user != null) {
            textViewName.setText(user.getName());
            textViewEmail.setText(user.getEmail());
        }

        new DrawerBuilder().withActivity(this)
                .withToolbar(mToolBar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.START)
                .withSavedInstance(bundle)
                .withHeader(view)
                .addDrawerItems(new PrimaryDrawerItem().withName(R.string.navigation_drawer_logout).withIcon(R.mipmap.ic_menu_logout).withIdentifier(R.id.navigation_drawer_logout))
                .withOnDrawerItemClickListener(this)
                .build();
    }

    private void showFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.reserveon_container, new RestaurantsFragment())
                .commit();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolBar);
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

        switch ((int) drawerItem.getIdentifier()) {
            case R.id.navigation_drawer_logout:
                AuthManager.logoutUser();
                startActivity(new Intent(this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
        }
        return false;
    }
}
