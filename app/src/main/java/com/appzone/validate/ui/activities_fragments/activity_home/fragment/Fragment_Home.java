package com.appzone.validate.ui.activities_fragments.activity_home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.appzone.validate.R;
import com.appzone.validate.ui.activities_fragments.activity_home.HomeActivity;

import java.util.Locale;

public class Fragment_Home extends Fragment {

    private ConstraintLayout co_back;
    private ImageView arrow;
    private HomeActivity activity;
    private Button btn_history,btn_scan;



    public static Fragment_Home newInstance()
    {
        return new Fragment_Home();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        initView(view);
        setRetainInstance(true);
        return view;
    }




    private void initView(View view) {
        activity = (HomeActivity) getActivity();
        co_back = view.findViewById(R.id.co_back);
        arrow = view.findViewById(R.id.arrow);
        btn_history = view.findViewById(R.id.btn_history);
        btn_scan = view.findViewById(R.id.btn_scan);

        if (Locale.getDefault().getLanguage().equals("ar")) {
            arrow.setImageResource(R.drawable.white_right_arrow);
        } else {
            arrow.setImageResource(R.drawable.white_left_arrow);

        }

        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_history.setBackgroundResource(R.color.colorPrimary);
                btn_scan.setBackgroundResource(R.color.gray2);
                activity.DisplayFragmentHistory();
            }
        });

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_scan.setBackgroundResource(R.color.colorPrimary);
                btn_history.setBackgroundResource(R.color.gray2);
                activity.DisplayFragmentScan();
            }
        });


        co_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Back();
            }
        });
    }

    public void updateView()
    {
        btn_scan.setBackgroundResource(R.color.colorPrimary);
        btn_history.setBackgroundResource(R.color.gray2);
    }
}
