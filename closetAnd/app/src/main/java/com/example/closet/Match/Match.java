package com.example.closet.Match;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.content.Context;
import com.example.closet.MainActivity;
import com.example.closet.R;
import android.widget.GridView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
/**
 * A simple {@link Fragment} subclass.
 */
public class Match extends DialogFragment implements View.OnClickListener {
    private PopupWindow mPopupWindow;
    View view;
    Button Save, My_pick, Reset;
    GridView gridView;
    Spinner spinner;

    public Match() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_match, container, false);
        Save = (Button) view.findViewById(R.id.Save);
        Save.setOnClickListener(this);
        My_pick = (Button) view.findViewById(R.id.My_pick);
        My_pick.setOnClickListener(this);
        //GridView gridview = (GridView) view.findViewById(R.id.gridview1);
        //gridview.setAdapter(new Match_GridAdapter(getContext()));
        Reset = (Button) view.findViewById(R.id.Reset);
        Reset.setOnClickListener(this);
        //spinner.setEnabled(true);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == Save) {
            //View popupView = getLayoutInflater().inflate(R.layout.match_pop_up, null);
            //mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
            //mPopupWindow.setFocusable(true); // 외부 영역 선택히 PopUp 종료
            //mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            //Button cancel = (Button) popupView.findViewById(R.id.Cancel);
            //cancel.setOnClickListener(new View.OnClickListener() {
             //   @Override
            //    public void onClick(View v) {
             //       mPopupWindow.dismiss();
              //  }
            //});
            //Button ok = (Button) popupView.findViewById(R.id.Ok);
            //ok.setOnClickListener(new View.OnClickListener() {
            //    @Override
            //    public void onClick(View v) {
             //       mPopupWindow.dismiss();
             //   }
            //});

            //Spinner spinner=(Spinner) mPopupWindow.getContentView().findViewById(R.id.spinner);
            //spinner.setOnClickListener(new View.OnClickListener() {
            //    @override
             //   public void onClick(View v) {
              //      AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
               //     builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                 //       public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                   //     }
                    //});
                    //builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                     //   public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                      //  }
                    //});
                    //return builder.create();
               // }

            //setSpinnerData();
        }

        else if (view == My_pick) {
            //View popupView = getLayoutInflater().inflate(match_my_pick, null);
            //mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
            //mPopupWindow.setFocusable(true); // 외부 영역 선택히 PopUp 종료
            //mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            //ImageButton cancel = (ImageButton) popupView.findViewById(R.id.close);
            //cancel.setOnClickListener(new View.OnClickListener() {
            //   public void onClick(View v) {
            //       mPopupWindow.dismiss();
            //   }
            //});
            Intent intent = new Intent(getActivity(), Match_Grid.class);
            startActivityForResult(intent, 30);
            //ImageButton cancel = (ImageButton) popupView.findViewById(R.id.close);
            //cancel.setOnClickListener(new View.OnClickListener() {
            //   public void onClick(View v) {
            //      mPopupWindow.dismiss();
            // });
        }
        else{
            //reset인 경우
        }
    }

    private void setSpinnerData() {
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.match_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}