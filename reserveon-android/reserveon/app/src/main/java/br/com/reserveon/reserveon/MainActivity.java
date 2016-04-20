package br.com.reserveon.reserveon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.reserveon.reserveon.adapters.CardListRestaurantAdapter;
import br.com.reserveon.reserveon.interfaces.IServiceResponse;
import br.com.reserveon.reserveon.models.Institute;
import br.com.reserveon.reserveon.rest.InstituteService;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.reserveon_toolbar)
    Toolbar mToolBar;
    @Bind(R.id.reserveon_recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        new InstituteService().getInstitutes(new IServiceResponse<List<Institute>>() {
            @Override
            public void onSuccess(List<Institute> data) {
                Log.i("script", "onSuccess");
                mRecyclerView.setAdapter(new CardListRestaurantAdapter(data, MainActivity.this));
            }

            @Override
            public void onError(String error) {
                Log.i("script", "onError");
                mRecyclerView.setAdapter(new CardListRestaurantAdapter(new ArrayList<Institute>(), MainActivity.this));
            }
        });


    }

    private void setupToolbar() {
        setSupportActionBar(mToolBar);
    }
}
