package com.example.lab5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;


public class Mainpage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static TextView stateView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Mainpage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Mainpage newInstance(String param1, String param2) {
        Mainpage fragment = new Mainpage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static void setStateView(String value) {
        //System.out.println("ATTEMPT TO SET VALUE");
        stateView.setText(value);
    }

    public static String getStateView() {
        return stateView.getText().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println("CREATE VIEW");
        View root = inflater.inflate(R.layout.fragment_mainpage, container, false);
        // RECYCLE VIEW
        RecyclerView.LayoutManager viewManager = new LinearLayoutManager(root.getContext());
        MyAdapter viewAdapter = new MyAdapter();
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(viewAdapter);
        recyclerView.setLayoutManager(viewManager);
        // LIFE CYCLE STATE
        stateView = root.findViewById(R.id.lifecycle_text);

        // EDIT Text
        EditText currencyText = root.findViewById(R.id.currency_text);

        RecyclerView recycler = root.findViewById(R.id.recycler_view);
        String currencyTag = currencyText.getText().toString().toUpperCase();

        //SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        String dayLim = sharedPref.getString(getString(R.string.pref_days_count),"20");
        String currencyTo = sharedPref.getString(getString(R.string.pref_currency), "USD");
        System.out.println("MTF" + dayLim + currencyTo);
        updateListAdapter(root, viewAdapter, currencyTag, currencyTo, Integer.parseInt(dayLim));

        // RECYCLER VIEW
        recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.fragment_mainpage_to_fragment_settings);
            }
        });

        // SEARCH BUTTON ACTION
        Button button = root.findViewById(R.id.search_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currencyTag = currencyText.getText().toString().toUpperCase();

                String dayLim = sharedPref.getString(getString(R.string.pref_days_count),"");
                String currencyTo = sharedPref.getString(getString(R.string.pref_currency), "");
                //System.out.println("MTF" + dayLim + currencyTo);

                updateListAdapter(root, viewAdapter, currencyTag, currencyTo, Integer.parseInt(dayLim));
            }
        });

        // LINK TO WEB CLICK LISTENER
        TextView linkText = root.findViewById(R.id.link_web);
        linkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currencyTag = currencyText.getText().toString().toUpperCase();
                String url = "https://www.cryptocompare.com/coins/"+currencyTag.toLowerCase()+"/overview/USDT";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        return root;
    }

    private void updateListAdapter(View view, MyAdapter viewAdapter, String currencyFrom, String currencyTo, int limit) {
        viewAdapter.clearData();
        //EXCHANGE TYPE
        TextView exchangeType = view.findViewById(R.id.exchange_type);
        exchangeType.setText(currencyFrom + " --> " + currencyTo + " with requests limit = " + limit);
        System.out.println(currencyFrom + " " + currencyTo + " " + limit);

        System.out.println("START BINDING!!!!!");

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        RestApi.getApi()
                .getData(currencyFrom, currencyTo, limit)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        Response body = response.body();
                        if (body.data != null) {
                            if (body.data.rows == null) {
                                Toast.makeText(view.getContext(), R.string.invalid_error, Toast.LENGTH_LONG).show();
                            }

                            body.data.rows.forEach( it -> viewAdapter.addData(it,view));
                            recyclerView.getAdapter().notifyDataSetChanged();
                        } else {
                            Toast.makeText(view.getContext(), R.string.runtime_error, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Toast.makeText(view.getContext(), t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}