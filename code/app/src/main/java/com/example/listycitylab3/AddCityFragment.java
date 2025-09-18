package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment
{
	// Constructor to store the city to edit it
	City city;
	public AddCityFragment(City city)
	{
		this.city = city;
	}

	// Default constructor for adding cities
	public AddCityFragment()
	{

	}

	interface AddCityDialogListener
	{
		void addCity(City city);
		void editCityList(String name, String province, City city);
	}

	private AddCityDialogListener listener;

	@Override
	public void onAttach(@NonNull Context context)
	{
		super.onAttach(context);

		if (context instanceof AddCityDialogListener)
		{
			listener = (AddCityDialogListener) context;
		} else
		{
			throw new RuntimeException(context + " must implement AddCityDialogListener");
		}
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
	{
		View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
		EditText editCityName = view.findViewById(R.id.edit_text_city_text);
		EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

		// Set the defaults of the text box to the selected city if one is selected
		if (this.city != null)
		{
			editCityName.setText(this.city.getName());
			editProvinceName.setText(this.city.getProvince());
		}

		return builder
				.setView(view)
				.setTitle("Add/edit a city")
				.setNegativeButton("Cancel", null)
				.setPositiveButton("Add", (dialog, which) ->
				{
					String cityName = editCityName.getText().toString();
					String provinceName = editProvinceName.getText().toString();
					if (this.city == null)
					{
						listener.addCity(new City(cityName, provinceName));
					} else
					{
						listener.editCityList(cityName, provinceName, city);
					}

				})
				.create();
	}
}
