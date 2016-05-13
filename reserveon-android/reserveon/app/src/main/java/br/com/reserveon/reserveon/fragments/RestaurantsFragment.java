package br.com.reserveon.reserveon.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import br.com.reserveon.reserveon.R;
import br.com.reserveon.reserveon.adapters.CardListRestaurantAdapter;
import br.com.reserveon.reserveon.interfaces.IServiceResponse;
import br.com.reserveon.reserveon.models.Institute;
import br.com.reserveon.reserveon.models.managers.InstituteListManager;
import br.com.reserveon.reserveon.rest.InstituteService;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 22/04/2016.
 */
public class RestaurantsFragment extends Fragment implements SearchView.OnQueryTextListener {

    @Bind(R.id.reserveon_recyclerview)
    RecyclerView mRecyclerView;

    private CardListRestaurantAdapter mAdapter;
    private SearchView mSearchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_restaurants, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        final MaterialDialog progress = showModalProgress(R.string.frag_restaurants_progress_loading_list_restaurants);

        new InstituteService().getInstitutes(new IServiceResponse<List<Institute>>() {
            @Override
            public void onSuccess(List<Institute> data) {
                InstituteListManager.institutes = data;
                progress.dismiss();
                mAdapter = new CardListRestaurantAdapter(data, getActivity());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onError(String error) {
                progress.dismiss();
                mAdapter = new CardListRestaurantAdapter(new ArrayList<Institute>(), getActivity());
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }

    private MaterialDialog showModalProgress(Integer content) {
        return new MaterialDialog.Builder(getActivity())
                .autoDismiss(false)
                .cancelable(false)
                .title(R.string.progress_dialog_title)
                .content(content)
                .progress(true, 0)
                .show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem item = menu.findItem(R.id.menu_search);

        mSearchView = (SearchView) item.getActionView();
        mSearchView.setQueryHint(getString(R.string.frag_restaurants_hint_search));
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        mSearchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        mSearchView.clearFocus();
        filterListRestaurants(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText.length() == 0) {
            mAdapter.setInstitutes(InstituteListManager.institutes);
            mAdapter.notifyDataSetChanged();
        }
        return false;
    }

    private void filterListRestaurants(String query) {

        final MaterialDialog progress = showModalProgress(R.string.frag_restaurants_progress_loading_list_filter_restaurants);

        new InstituteService().getInstitutesByName(query, new IServiceResponse<List<Institute>>() {
            @Override
            public void onSuccess(List<Institute> data) {
                progress.dismiss();
                mAdapter.setInstitutes(data);
                mAdapter.notifyDataSetChanged();
                mSearchView.clearFocus();
            }

            @Override
            public void onError(String error) {
                progress.dismiss();
                mAdapter.setInstitutes(new ArrayList<Institute>());
                mAdapter.notifyDataSetChanged();
                mSearchView.clearFocus();
            }
        });
    }
}
