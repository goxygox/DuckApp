package com.example.logreg;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DeployActivity extends Fragment {
    public EditText starttime,endtime;
    private boolean isStartCitySelected = false;
    private boolean isEndCitySelected = false;
    public String selectedDate;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String userId = currentUser.getUid();
    public String startCity,endCity;
    public Integer price,passcnt;
    public Gson gson;
    private ArrayList<String> cities = new ArrayList<>();
    private ArrayList<String> filteredCities = new ArrayList<>();

    public DeployActivity()
    {
        //пустой конструктор
    }

    public static DeployActivity newInstance() {
        DeployActivity fragment = new DeployActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.tb_city);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
            }.getType();
            ArrayList<ArrayList<String>> data = gson.fromJson(reader, type);
            reader.close();
            inputStream.close();
            for (ArrayList<String> cityData : data) {
                cities.add(cityData.get(0));
            }
        } catch (IOException e) {
            Toast.makeText(getContext(), "Ошибка получения данных городов", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_deploy, container, false);
        SearchView firstPoint = view.findViewById(R.id.startAdress);
        SearchView endPoint = view.findViewById(R.id.endAdress);
        EditText pricebox = view.findViewById(R.id.priceBox);
        EditText passcntbox = view.findViewById(R.id.passcntBox);
        starttime = view.findViewById(R.id.starttimeBox);
        endtime = view.findViewById(R.id.endtimeBox);
        firstPoint.setIconified(false);//убрали иконки у поисковых полей
        endPoint.setIconified(false);//убрали иконки у поисковых полей
        firstPoint.onActionViewExpanded();
        endPoint.onActionViewExpanded();
        Button depButton = view.findViewById(R.id.deployButton);
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        ListView citiesListView = view.findViewById(R.id.cities_list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, filteredCities);
        citiesListView.setAdapter(adapter);
        citiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // получаем текст выбранного элемента
                String selectedCity = filteredCities.get(i);
                // устанавливаем текст в SearchView
                if (firstPoint.hasFocus()) {
                    startCity = filteredCities.get(i);
                    firstPoint.setQuery(selectedCity, true);
                    isStartCitySelected = true;
                } else if (endPoint.hasFocus()) {
                    endCity = filteredCities.get(i);
                    endPoint.setQuery(selectedCity, true);
                    isEndCitySelected = true;
                }
                if (isStartCitySelected && isEndCitySelected)
                {
                    // Оба города выбраны
                    citiesListView.setVisibility(View.GONE);
                    // Передаём фокус на другой элемент
                    if (firstPoint.hasFocus()) {
                        endPoint.requestFocus();
                    } else {
                        firstPoint.requestFocus();
                    }
                }
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                selectedDate = new StringBuilder().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" ").toString();
            }
        });
        depButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!passcntbox.getText().toString().isEmpty() && !pricebox.getText().toString().isEmpty() && !starttime.getText().toString().isEmpty() && !endtime.getText().toString().isEmpty() && !userId.isEmpty() && !startCity.isEmpty() && !endCity.isEmpty() &&!selectedDate.isEmpty())
                {
                    price = Integer.parseInt(pricebox.getText().toString());
                    passcnt = Integer.parseInt(passcntbox.getText().toString());
                    String sttime = starttime.getText().toString();
                    String edtime = endtime.getText().toString();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                    String firstid = "Свободно";
                    String secondid = "Свободно";
                    String thirdid = "Свободно";
                    String DriverName = "Владимир";
                    Integer Duration = 5;
                    String typeTrip = "Активно";
                    Trip trip = new Trip(userId,passcnt,firstid,secondid,thirdid,startCity,endCity,selectedDate,sttime,edtime,Duration,price,DriverName,typeTrip);
                    trip.saveToFirebase();

                }
            }
        });

        // слушатель изменений текста для первого элемента
        firstPoint.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterCities(newText);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        // слушатель изменений текста для второго элемента
        endPoint.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (citiesListView.getVisibility() == View.GONE) {
                    citiesListView.setVisibility(View.VISIBLE);
                }
                filterCities(newText);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return view;
    }
    private void filterCities(String text) {
        filteredCities.clear();
        if (text.isEmpty()) {
            filteredCities.addAll(cities);
        }
        else
        {
            text = text.toLowerCase();
            for (String city : cities) {
                if (city.toLowerCase().contains(text)) {
                    filteredCities.add(city);
                }
            }
        }
    }
}