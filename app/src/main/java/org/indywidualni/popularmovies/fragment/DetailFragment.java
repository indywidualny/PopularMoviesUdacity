package org.indywidualni.popularmovies.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.indywidualni.popularmovies.ImageAdapter;
import org.indywidualni.popularmovies.R;
import org.indywidualni.popularmovies.model.Results;

public class DetailFragment extends Fragment {

    private Results results;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        if (bundle != null)
            results = bundle.getParcelable("results");
        else
            Log.e("DetailFragment", "DetailFragment 'bundle' is null!");

        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (results != null) {
            CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)
                    getActivity().findViewById(R.id.collapsing_toolbar);
            ImageView poster = (ImageView) view.findViewById(R.id.imageView);
            TextView tv1 = (TextView) view.findViewById(R.id.textView);
            TextView tv2 = (TextView) view.findViewById(R.id.textView2);
            TextView tv3 = (TextView) view.findViewById(R.id.textView3);
            TextView tv4 = (TextView) view.findViewById(R.id.textView4);
            Button fav = (Button) view.findViewById(R.id.button);

            String rt = String.format("%.1f", Float.parseFloat(results.getVote_average())) + "/10";

            collapsingToolbar.setTitle(results.getTitle());  // title
            tv1.setText(results.getRelease_date().substring(0, 4));  // year

            // TODO: there should be length but length is not provided by the API (?)
            tv2.setText("Not provided?");  // length

            tv3.setText(rt);  // rating
            tv4.setText(results.getOverview());  // overview

            Picasso.with(view.getContext()).load(ImageAdapter.BASE_URL + results.getPoster_path())
                    .into(poster);  // poster

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(view.getContext(), "Click!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
