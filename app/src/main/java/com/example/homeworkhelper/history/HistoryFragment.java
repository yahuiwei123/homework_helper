package com.example.homeworkhelper.history;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.homeworkhelper.R;
import com.example.homeworkhelper.history.bean.RecordData;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    private View view;
    private List<RecordData> recordDataList;
    private IOnItemClickListener onItemClickListener = new IOnItemClickListener();
    private Activity activity;
    private MyDataAdapter adapter;
    private RecyclerView recyclerView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public HistoryFragment(List<RecordData> recordDataList, Activity activity) {
        this.recordDataList = recordDataList;
        this.activity = activity;
        adapter = new MyDataAdapter(this.recordDataList, onItemClickListener, activity);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryFragment.this.getContext()));
        recyclerView.setAdapter(adapter);

        if(recordDataList.isEmpty()) {
            view.findViewById(R.id.history_background).setVisibility(View.VISIBLE);
            view.findViewById(R.id.dataRecycler).setVisibility(View.INVISIBLE);
        }

        //获取tab名
        String tab = getArguments().getString("tabName");
        return view;
    }

    public void refreshData() {
        adapter.refreshDataList();
        view.findViewById(R.id.history_background).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.dataRecycler).setVisibility(View.VISIBLE);
    }
}