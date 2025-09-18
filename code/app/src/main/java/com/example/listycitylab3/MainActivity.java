package com.example.listycitylab3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    public void addCity(City city)
    {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    // Find the city in the dataList and change the name and province and notify changes
    @Override
    public void editCityList(String name, String province, City city)
    {
        for (int i = 0; i < dataList.size(); i++)
        {
            if (city == dataList.get(i))
            {
                dataList.get(i).setName(name);
                dataList.get(i).setProvince(province);
                cityAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Store the cities and provinces in arrays
        String[] cities = { "Edmonton", "Vancouver", "Toronto"};
        String[] provinces = {"AB", "BC", "ON"};

        // Add the cities and provinces to the dataList
        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++)
        {
            dataList.add(new City(cities[i], provinces[i]));
        }

        // Set the references by ID
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // Add a click listener to the floating button
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v ->
        {
            new AddCityFragment().show(getSupportFragmentManager(), "Add City");
        });

        // Add a click listener for the selection of a city in the list
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                // Get the item at the position and cast it into its object, getSelectedItem() doesn't work
                City selectedCity = (City)cityList.getItemAtPosition(i);
                // Make a new fragment to access the dialog box
                AddCityFragment editCity = new AddCityFragment(selectedCity);

                // Call the show method with an Edit City tag
                editCity.show(getSupportFragmentManager(), "Edit City");
            }
        });
    }
}