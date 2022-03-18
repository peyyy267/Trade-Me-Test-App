package com.example.peapplication.ui.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;

import com.example.peapplication.ListItem;
import com.example.peapplication.MainActivity;
import com.example.peapplication.R;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private Context context;
    private int latest_listing_size = 20;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();

        View root = inflater.inflate(R.layout.fragment_latest_listings, container, false);

        int[][] ids = {{R.id.v01, R.id.v02, R.id.v03, R.id.v04, R.id.v05, R.id.v06},
                {R.id.v11, R.id.v12, R.id.v13, R.id.v14, R.id.v15, R.id.v16},
                {R.id.v21, R.id.v22, R.id.v23, R.id.v24, R.id.v25, R.id.v26},
                {R.id.v31, R.id.v32, R.id.v33, R.id.v34, R.id.v35, R.id.v36},
                {R.id.v41, R.id.v42, R.id.v43, R.id.v44, R.id.v45, R.id.v46},
                {R.id.v51, R.id.v52, R.id.v53, R.id.v54, R.id.v55, R.id.v56},
                {R.id.v61, R.id.v62, R.id.v63, R.id.v64, R.id.v65, R.id.v66},
                {R.id.v71, R.id.v72, R.id.v73, R.id.v74, R.id.v75, R.id.v76},
                {R.id.v81, R.id.v82, R.id.v83, R.id.v84, R.id.v85, R.id.v86},
                {R.id.v91, R.id.v92, R.id.v93, R.id.v94, R.id.v95, R.id.v96},
                {R.id.v101, R.id.v102, R.id.v103, R.id.v104, R.id.v105, R.id.v106},
                {R.id.v111, R.id.v112, R.id.v113, R.id.v114, R.id.v115, R.id.v116},
                {R.id.v121, R.id.v122, R.id.v123, R.id.v124, R.id.v125, R.id.v126},
                {R.id.v131, R.id.v132, R.id.v133, R.id.v134, R.id.v135, R.id.v136},
                {R.id.v141, R.id.v142, R.id.v143, R.id.v144, R.id.v145, R.id.v146},
                {R.id.v151, R.id.v152, R.id.v153, R.id.v154, R.id.v155, R.id.v156},
                {R.id.v161, R.id.v162, R.id.v163, R.id.v164, R.id.v165, R.id.v166},
                {R.id.v171, R.id.v172, R.id.v173, R.id.v174, R.id.v175, R.id.v176},
                {R.id.v181, R.id.v182, R.id.v183, R.id.v184, R.id.v185, R.id.v186},
                {R.id.v191, R.id.v192, R.id.v193, R.id.v194, R.id.v195, R.id.v196},
        };

        OkHttpClient client = new OkHttpClient();
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(getString(R.string.consumer_key), getString(R.string.consumer_secret));
        Request request = new Request.Builder()
                .url(getString(R.string.latest_listings_url))
                .build();
        try {
            Request signedRequest = (Request)consumer.sign(request).unwrap();
            client.newCall(signedRequest).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String myResponse = response.body().string();
                        LatestListings latestListings = new Gson().fromJson(myResponse, LatestListings.class);
                        List<ListItem> list = latestListings.getList();
                        for (int i = 0; i < latest_listing_size; i++) {
                            if ((list.get(i).getPictureHref()) != null) {
                                list.get(i).setBitmap(BitmapFactory.decodeStream(new BufferedInputStream(
                                        new URL(list.get(i).getPictureHref()).openStream())));
                            }
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < latest_listing_size; i++) {
                                    ImageView i1 = root.findViewById(ids[i][1]);
                                    i1.setImageBitmap(list.get(i).getBitmap());
                                    TextView t1 = root.findViewById(ids[i][2]);
                                    t1.setText(list.get(i).getRegion());
                                    TextView t2 = root.findViewById(ids[i][3]);
                                    t2.setText(list.get(i).getTitle());
                                    TextView t4 = root.findViewById(ids[i][4]);
                                    t4.setText(list.get(i).getPriceDisplay());
                                    TextView t3 = root.findViewById(ids[i][5]);
                                    t3.setText("Buy Now");

                                    LinearLayout linearLayout = root.findViewById(ids[i][0]);
                                    linearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(context, "Item " + v.getTag().toString() + " Clicked", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        } catch (OAuthMessageSignerException e) {
            e.printStackTrace();
        } catch (OAuthExpectationFailedException e) {
            e.printStackTrace();
        } catch (OAuthCommunicationException e) {
            e.printStackTrace();
        }
        return root;
    }
}