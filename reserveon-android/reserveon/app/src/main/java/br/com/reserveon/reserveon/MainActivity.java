package br.com.reserveon.reserveon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.com.reserveon.reserveon.fragments.RestaurantsFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.reserveon_toolbar)
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();
        showFragment();
    }

    private void showFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.reserveon_container, new RestaurantsFragment())
                .commit();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolBar);
    }
}
