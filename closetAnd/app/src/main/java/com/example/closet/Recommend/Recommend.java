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
        setSpinner_history();
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

    private void setSpinner_history() {
        Spinner spinner_history = (Spinner) view.findViewById(R.id.recommend_spinner_history);
        String[] recommend_array2 = getResources().getStringArray(R.array.recommend_array2);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, recommend_array2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_history.setAdapter(dataAdapter);
        spinner_history.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String history = parent.getItemAtPosition(position).toString();
                if (!history.equals("History")) {
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
        Spinner spinner_like = (Spinner) view.findViewById(R.id.recommend_spinner_like);
        String[] items = getResources().getStringArray(R.array.recommend_array3);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_like.setAdapter(dataAdapter);
        spinner_like.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String like = parent.getItemAtPosition(position).toString();
                if (like.equals("Like") == false) {
                    Toast.makeText(parent.getContext(), "Selected: " + like, Toast.LENGTH_LONG).show();
                    //photoUrls.clear();
                    //String colorUrl;
                    //if (like.equals("All"))
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

    public void onClick(View view) {

    }
}