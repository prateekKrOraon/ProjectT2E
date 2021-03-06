package com.think2exam.projectt2e.ui.search;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.ui.activities.CollegeListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SearchFragment extends Fragment {

    public static final String id = "activities_fragment";
    public SearchFragment(){}

    public Context mainActivityContext;

    RelativeLayout categoryRL,stateRL,cityRL;
    TextView categoryText,stateText,cityText;
    LinearLayout categoryll,statell,cityll;

    ArrayList<String> arrayList;
    Dialog dialog;
    private EditText keywordEdit;
    @SuppressLint("ValidFragment")
    public SearchFragment(Context context)
    {
         mainActivityContext = context;
    }


    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_search, container, false);
        categoryRL = root.findViewById(R.id.category_box_layout);
        stateRL = root.findViewById(R.id.state_box_layout);
        cityRL = root.findViewById(R.id.city_box_layout);
        categoryText = categoryRL.findViewById(R.id.search_box_name);
        stateText = stateRL.findViewById(R.id.search_box_name);
        cityText = cityRL.findViewById(R.id.search_box_name);
        categoryll = categoryRL.findViewById(R.id.search_box_ll);
        statell = stateRL.findViewById(R.id.search_box_ll);
        cityll = cityRL.findViewById(R.id.search_box_ll);

        ImageView searchImage = root.findViewById(R.id.search_image);
        Glide.with(root.getContext())
                .load(R.drawable.search_background)
                .into(searchImage);

        categoryText.setText(getString(R.string.all_category));
        stateText.setText(getString(R.string.all_states));
        cityText.setText(getString(R.string.all_cities));

        cityll.setVisibility(View.GONE);
        categoryRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("category");
            }
        });


        stateRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("state");
            }
        });
        cityRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("city");
            }
        });

        keywordEdit = root.findViewById(R.id.keyword_edit);


        final TextView textView = root.findViewById(R.id.field_required);

        MaterialButton searchBtn = root.findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = categoryText.getText().toString().trim();
                String state = stateText.getText().toString().trim();
                String city = cityText.getText().toString().trim();
                String keyword = keywordEdit.getText().toString().trim();
                if(checkValidity(category))//check condition
                {

                        int catId = getCatId(category);
                        Intent intent = new Intent(getContext(), CollegeListActivity.class);
                        intent.putExtra("which","search");
                        intent.putExtra("catId",catId);
                        intent.putExtra("state",state);
                        intent.putExtra("city",city);
                        intent.putExtra("keyword",keyword);
                        if(category.equals(getString(R.string.university)))
                        {
                            if(state.equals(getString(R.string.all_states)))
                                intent.putExtra("title",getString(R.string.university)+" in "+state);
                            else
                            {
                                if(city.equals(getString(R.string.all_cities)))
                                    intent.putExtra("title",getString(R.string.university)+" in "+state);
                                else
                                    intent.putExtra("title",getString(R.string.university)+" in "+city);

                            }

                        }
                        else
                        {
                            if(state.equals(getString(R.string.all_states)))
                                intent.putExtra("title",category+" Colleges in "+state);
                            else
                            {
                                if(city.equals(getString(R.string.all_cities)))
                                    intent.putExtra("title",category+" Colleges in "+state);
                                else
                                    intent.putExtra("title",category+" Colleges in "+city);

                            }
                        }

                    getContext().startActivity(intent);

                    textView.setVisibility(View.GONE);


                }
                else
                {
                       textView.setVisibility(View.VISIBLE);
                }
            }
        });



        return root;
    }

    private int getCatId(String category)
    {
        int catId = -1;
        if(category.equals(getString(R.string.engineering)))
        {
            catId = R.string.engineering;
        }else if(category.equals(getString(R.string.management)))
        {
            catId = R.string.management;

        }else if(category.equals(getString(R.string.education)))
        {
            catId = R.string.education;

        }else if(category.equals(getString(R.string.medical_and_dental)))
        {
            catId = R.string.medical_and_dental;

        }else if(category.equals(getString(R.string.nursing_and_paramedical)))
        {
            catId = R.string.nursing_and_paramedical;

        }else if(category.equals(getString(R.string.pharmacy)))
        {
            catId = R.string.pharmacy;

        }else if(category.equals(getString(R.string.agriculture)))
        {
            catId = R.string.agriculture;

        }else if(category.equals(getString(R.string.university)))
        {
            catId = R.string.university;
        }
        return catId;

    }

    private boolean checkValidity(String category)
    {

        if(category.equals(getString(R.string.all_category)))
            return false;
        else
            return true;

    }

    private void setCategoryList()
    {
        arrayList = new ArrayList<>();
        arrayList.add(getResources().getString(R.string.all_category));
        arrayList.add(getResources().getString(R.string.agriculture));
        arrayList.add(getResources().getString(R.string.education));
        arrayList.add(getResources().getString(R.string.engineering));
        arrayList.add(getResources().getString(R.string.management));
        arrayList.add(getResources().getString(R.string.medical_and_dental));
        arrayList.add(getResources().getString(R.string.nursing_and_paramedical));
        arrayList.add(getResources().getString(R.string.pharmacy));
        arrayList.add(getResources().getString(R.string.university));
    }

    private void setStateList()
    {
            arrayList = new ArrayList<>();
            String json=null;
            try
            {
                InputStream is = getActivity().getAssets().open("states.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
                JSONObject jsonObject=new JSONObject(json);
                JSONArray array=jsonObject.getJSONArray("states");
                arrayList.add(getString(R.string.all_states));
                for(int i=0;i<array.length();i++)
                {
                    arrayList.add(array.getString(i));
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

    }
    private void setCityList(String state)
    {
        arrayList = new ArrayList<>();
        String json=null;
        try
        {
            InputStream is = getActivity().getAssets().open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONObject jsonObject=new JSONObject(json);
            JSONArray array=jsonObject.getJSONArray("array");
            arrayList.add(getString(R.string.all_cities));
            for(int i=0;i<array.length();i++)
            {
                JSONObject jsonObject1 = array.getJSONObject(i);
                if(jsonObject1.getString("state").equals(state))
                {
                    arrayList.add(jsonObject1.getString("name"));
                }
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void showDialog(String which)
    {
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.search_dialog);
        dialog.setCancelable(true);
        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();

        try {
            wm.copyFrom(dialog.getWindow().getAttributes());
            wm.width = WindowManager.LayoutParams.MATCH_PARENT;
            wm.height = WindowManager.LayoutParams.WRAP_CONTENT;

        }catch(NullPointerException e){
            System.out.println(e.toString());
        }


        ListView listView = dialog.findViewById(R.id.search_dialog_list_view);
        if(which.equals("category"))
        {
            setCategoryList();
            listView.setAdapter(new DialogAdapter("category"));

        }
        else if(which.equals("state"))
        {
            setStateList();
            listView.setAdapter(new DialogAdapter("state"));
        }
        else
        {
            setCityList(stateText.getText().toString());
            listView.setAdapter(new DialogAdapter("city"));
        }

        dialog.show();
        dialog.getWindow().setAttributes(wm);
    }

    private class DialogAdapter extends BaseAdapter
    {

        String str;
        public DialogAdapter(String str)
        {
            this.str = str;
        }
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view = getLayoutInflater().inflate(R.layout.select_dialog_item,parent,false);
            final RadioButton radioButton= view.findViewById(R.id.item_radio_btn);
            radioButton.setText(arrayList.get(position));
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(str.equals("category")) {
                        categoryText.setText(arrayList.get(position));
                        categoryText.setTextColor(getResources().getColor(R.color.black));


                    }else if(str.equals("state")){
                        stateText.setText(arrayList.get(position));
                        stateText.setTextColor(getResources().getColor(R.color.black));
                        cityText.setText(getString(R.string.all_cities));
                        if(stateText.getText().toString().equals(getString(R.string.all_states)))
                        {
                            cityll.setVisibility(View.GONE);
                            categoryText.setTextColor(getResources().getColor(R.color.black));

                        }
                        else
                        {
                            cityRL.setBackgroundColor(getResources().getColor(R.color.grey_light));
                            cityll.setVisibility(View.VISIBLE);
                            cityRL.setBackground(getResources().getDrawable(R.drawable.search_box_background));
                        }


                    }else {
                        cityText.setText(arrayList.get(position));
                        cityText.setTextColor(getResources().getColor(R.color.black));

                    }

                    dialog.dismiss();
                }
            });
            return view;
        }
    }

}