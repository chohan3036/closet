package com.example.closet.Recommend;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.closet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Recommend extends Fragment implements View.OnClickListener {

    View view;
    final String[] recommend_spinnerNames = new String[]{"Like", "Yes", "No", };
    int[] recommend_spinnerImages = new int[]{R.drawable.none,R.drawable.thumb_on, R.drawable.thumb_off};
    int spinner_id = 0;
    public Recommend() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recommend, container, false);
        setSpinner_sex_age();
        setSpinner_look();
        setSpinner_recommend();
        //Spinner.setEnabled(true);
        return view;
    }

    private void  setSpinner_sex_age() {
        Spinner spinner_sex_age = (Spinner) view.findViewById(R.id.recommend_spinner_sex_age);
        String[] recommend_array1 = getResources().getStringArray(R.array.recommend_array1);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, recommend_array1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sex_age.setAdapter(dataAdapter);
        spinner_sex_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sex_age = parent.getItemAtPosition(position).toString();
                if (!sex_age.equals("Sex/Age") ) {
                    Toast.makeText(parent.getContext(), "Selected: " +sex_age, Toast.LENGTH_LONG).show();
                    //photoUrls.clear();
                    //String colorUrl;
                    //if (sex_age.equals("All"))
                    //    colorUrl = net_url;
                    //else
                     //   colorUrl = net_url.concat("&color=" + item1);
                    //getClothings(colorUrl);
                    //loadGridView();}
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void setSpinner_look() {
        Spinner spinner_look = (Spinner) view.findViewById(R.id.recommend_spinner_look);
        String[] recommend_array2 = getResources().getStringArray(R.array.recommend_array2);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, recommend_array2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_look.setAdapter(dataAdapter);
        spinner_look.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String history = parent.getItemAtPosition(position).toString();
                if (!history.equals("Look(Style)")) {
                    Toast.makeText(parent.getContext(), "Selected: " + history, Toast.LENGTH_LONG).show();
                        //photoUrls.clear();
                        //String colorUrl;
                        //if (history.equals("All"))
                        //    colorUrl = net_url;
                        //else
                        //   colorUrl = net_url.concat("&color=" + item1);
                        //getClothings(colorUrl);
                        //loadGridView();}
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }
    private void setSpinner_recommend() {
        final Spinner spinner_like = (Spinner) view.findViewById(R.id.recommend_spinner_like);
        RecommendSpinner_Adapter recommendSpinner_adapter = new RecommendSpinner_Adapter(getContext(), recommend_spinnerNames, recommend_spinnerImages);
        spinner_like.setAdapter(recommendSpinner_adapter);
        spinner_like.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_id = spinner_like.getSelectedItemPosition();
                //spinnerNames[spinner_id] = parent.getItemAtPosition(position).toString();
                System.out.println(recommend_spinnerNames[spinner_id]);
                if (!recommend_spinnerNames[spinner_id].equals("Like")) {
                    // Showing selected spinner item
                    Toast.makeText(parent.getContext(), "Selected: " + recommend_spinnerNames[spinner_id], Toast.LENGTH_LONG).show();
                    //photoUrls.clear();
                    //String colorUrl;
                    //if (spinnerNames[spinner_id].equals("All"))
                    //    colorUrl = net_url;
                    //else
                    //    colorUrl = net_url.concat("&color=" + spinnerNames[spinner_id]);
                    //getClothings(colorUrl);
                    //loadGridView();
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void onClick(View view) {

    }
}