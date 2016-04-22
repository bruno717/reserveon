package br.com.reserveon.reserveon.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import br.com.reserveon.reserveon.R;
import br.com.reserveon.reserveon.adapters.CardListRestaurantAdapter;
import br.com.reserveon.reserveon.interfaces.IServiceResponse;
import br.com.reserveon.reserveon.models.Institute;
import br.com.reserveon.reserveon.rest.InstituteService;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 22/04/2016.
 */
public class RestaurantsFragment extends Fragment {

    @Bind(R.id.reserveon_recyclerview)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_restaurants, container, false);
        ButterKnife.bind(this, view);

        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        final MaterialDialog progress = showModalProgress();

        new InstituteService().getInstitutes(new IServiceResponse<List<Institute>>() {
            @Override
            public void onSuccess(List<Institute> data) {
                progress.dismiss();
                mRecyclerView.setAdapter(new CardListRestaurantAdapter(data, getActivity()));
            }

            @Override
            public void onError(String error) {
                progress.dismiss();
                mRecyclerView.setAdapter(new CardListRestaurantAdapter(new ArrayList<Institute>(), getActivity()));
            }
        });
    }

    private MaterialDialog showModalProgress() {
        return new MaterialDialog.Builder(getActivity())
                .autoDismiss(false)
                .cancelable(false)
                .title(R.string.progress_dialog_title)
                .content(R.string.frag_restaurants_progress_loading_list_restaurants)
                .progress(true, 0)
                .show();
    }
}
