package com.example.homeworkhelper.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.homeworkhelper.R;
import com.example.homeworkhelper.utils.JsonUtils;
import com.example.homeworkhelper.utils.common.Img;

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
    private TextView textView;
    private Img contentImg;
    private Img answerImg;
    private Img hintImg;
    private Img remarkImg;

    private ImageView contentImgView;
    private ImageView answerImgView;
    private ImageView hintImgView;
    private ImageView remarkImgView;

    public SearchResult() {
        // Required empty public constructor
    }

    public SearchResult(Img contentImg, Img answerImg, Img hintImg, Img remarkImg) {
        this.contentImg = contentImg;
        this.answerImg = answerImg;
        this.hintImg = hintImg;
        this.remarkImg = remarkImg;
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
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        contentImgView = view.findViewById(R.id.content_img);
        answerImgView = view.findViewById(R.id.answer_img);
        hintImgView = view.findViewById(R.id.hint_img);
        remarkImgView = view.findViewById(R.id.remark_img);

        textView = view.findViewById(R.id.content);
        textView.setText("题目");

        textView = view.findViewById(R.id.answer);
        textView.setText("答案");

        textView = view.findViewById(R.id.hint);
        textView.setText("解析");

        textView = view.findViewById(R.id.remark);
        textView.setText("点评");
        if (contentImg != null) {
            contentImgView.getLayoutParams().width = contentImg.getWidth() * 2;
            contentImgView.getLayoutParams().height = contentImg.getHeight() * 2;
            Glide.with(this).load(contentImg.getSrc()).into(contentImgView);
        }

        if (answerImg != null) {
            answerImgView.getLayoutParams().width = answerImg.getWidth() * 2;
            answerImgView.getLayoutParams().height = answerImg.getHeight() * 2;
            Glide.with(this).load(answerImg.getSrc()).into(answerImgView);
        }

        if (hintImg != null) {
            hintImgView.getLayoutParams().width = hintImg.getWidth() * 2;
            hintImgView.getLayoutParams().height = hintImg.getHeight() * 2;
            Glide.with(this).load(hintImg.getSrc()).into(hintImgView);
        }

        if (remarkImg != null) {
            remarkImgView.getLayoutParams().width = remarkImg.getWidth() * 2;
            remarkImgView.getLayoutParams().height = remarkImg.getHeight() * 2;
            Glide.with(this).load(remarkImg.getSrc()).into(remarkImgView);
        }

        return view;
    }
}