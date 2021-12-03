package com.example.homeworkhelper.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.homeworkhelper.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResult#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResult extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String title;
    private String reference;
    private String analysis;
    private TextView textView;

    public SearchResult() {
        // Required empty public constructor
    }

    public SearchResult(String title, String reference, String analysis) {
        this.title = title;
        this.reference = reference;
        this.analysis = analysis;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchResult.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchResult newInstance(String param1, String param2) {
        SearchResult fragment = new SearchResult();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search_result, container, false);
        textView = view.findViewById(R.id.title);
        textView.setText("题目");
        textView = view.findViewById(R.id.title_content);
        textView.setText(title);
        textView = view.findViewById(R.id.reference);
        textView.setText("答案");
        textView = view.findViewById(R.id.reference_content);
        textView.setText(reference);
        textView = view.findViewById(R.id.analysis);
        textView.setText("解析");
        textView = view.findViewById(R.id.analysis_content);
        textView.setText(analysis);


        return view;
    }
}