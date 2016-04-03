package org.indywidualni.popularmovies.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.indywidualni.popularmovies.ImageAdapter;
import org.indywidualni.popularmovies.R;
import org.indywidualni.popularmovies.RestClient;
import org.indywidualni.popularmovies.activity.DetailActivity;
import org.indywidualni.popularmovies.model.Movies;
import org.indywidualni.popularmovies.model.Results;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private GridView gridview;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridview = (GridView) rootView.findViewById(R.id.gridview);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridview.setAdapter(new ImageAdapter(getContext(), new Results[0]));
        getDataWrapper();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        gridview = null;
    }

    private void loadingCompleted() {
        if (gridview == null)
            return;

        getActivity().findViewById(R.id.loading).setVisibility(View.GONE);
        gridview.setVisibility(View.VISIBLE);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("results", (Results) gridview.getAdapter().getItem(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getDataWrapper() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        getData(preferences.getBoolean("top_rated", false));
    }

    private void getData(boolean topRated) {
        Call<Movies> call;

        if (topRated)
            call = RestClient.getClient().getTopRatedMovies(RestClient.API_KEY);
        else
            call = RestClient.getClient().getPopularMovies(RestClient.API_KEY);

        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                if (response.isSuccess()) {  // tasks available
                    Results[] results = response.body().getResults();

                    if (gridview != null)
                        gridview.setAdapter(new ImageAdapter(getActivity(), results));

                    loadingCompleted();
                } else {
                    // error response, no access to resource?
                    Log.e(TAG, "getData: error response " + response.code());
                    loadingCompleted();
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Log.e(TAG, "getData: " + t.getLocalizedMessage());
                loadingCompleted();
            }
        });
    }

}
