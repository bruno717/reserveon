package br.com.reserveon.reserveon.fragments;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;

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

        final MaterialDialog progress = showModalProgress();

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

    private MaterialDialog showModalProgress() {
        return new MaterialDialog.Builder(getActivity())
                .autoDismiss(false)
                .cancelable(false)
                .title(R.string.progress_dialog_title)
                .content(R.string.frag_restaurants_progress_loading_list_restaurants)
                .progress(true, 0)
                .show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem item = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getString(R.string.frag_restaurants_hint_search));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        //close keyboard
        if (getView() != null)
            ((InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);

        new InstituteService().getInstitutesByName(query, new IServiceResponse<List<Institute>>() {
            @Override
            public void onSuccess(List<Institute> data) {
                mAdapter.setInstitutes(data);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {
                mAdapter.setInstitutes(new ArrayList<Institute>());
                mAdapter.notifyDataSetChanged();
            }
        });

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
}
