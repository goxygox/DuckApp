package com.example.logreg;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CarActivityDriver extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter<Trip, TripViewHolder> adapter;

    public static CarActivityDriver newInstance() {
        CarActivityDriver fragment = new CarActivityDriver();
        return fragment;
    }

    public CarActivityDriver() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_driver, container, false);
        TextView text = rootView.findViewById(R.id.drivertext);
        recyclerView = rootView.findViewById(R.id.drivertrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Trips");
        FirebaseRecyclerOptions<Trip> options =
                new FirebaseRecyclerOptions.Builder<Trip>()
                        .setQuery(databaseReference.orderByChild("id_driver").equalTo(firebaseUser.getUid()), Trip.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<Trip, TripViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull TripViewHolder holder, int position, @NonNull Trip model) {
                holder.bind(model, position);
            }
            @NonNull
            @Override
            public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item, parent, false);
                return new TripViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("CarActivityDriver", "Database error: " + error.getMessage());
                Toast.makeText(getContext(), "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
    // ViewHolder class for the RecyclerView
    @Override
    public void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
    // ViewHolder class for the RecyclerView
    public static class TripViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateTextView;
        private final TextView driverNameTextView;
        private final TextView durationTextView;
        private final TextView endCityTextView;
        private final TextView endTimeTextView;
        private final TextView firstPassIdTextView;
        private final TextView idDriverTextView;
        private final TextView passengersTextView;
        private final TextView priceTextView;
        private final TextView secondPassIdTextView;
        private final TextView startCityTextView;
        private final TextView startTimeTextView;
        private final TextView thirdPassIdTextView;
        private final TextView typeTextView;

        public TripViewHolder(View itemView)
        {
            super(itemView);
            // Initialize the TextViews in the ViewHolder
            dateTextView = itemView.findViewById(R.id.dateTextView);
            driverNameTextView = itemView.findViewById(R.id.driverNameTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
            endCityTextView = itemView.findViewById(R.id.endCityTextView);
            endTimeTextView = itemView.findViewById(R.id.endTimeTextView);
            firstPassIdTextView = itemView.findViewById(R.id.firstPassIdTextView);
            idDriverTextView = itemView.findViewById(R.id.idDriverTextView);
            passengersTextView = itemView  .findViewById(R.id.passengersTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            secondPassIdTextView = itemView.findViewById(R.id.secondPassIdTextView);
            startCityTextView = itemView.findViewById(R.id.startCityTextView);
            startTimeTextView = itemView.findViewById(R.id.startTimeTextView);
            thirdPassIdTextView = itemView.findViewById(R.id.thirdPassIdTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
        }
        public void bind(Trip trip, int position)
        {
            // Bind the data to the TextViews in the ViewHolder
            dateTextView.setText("Дата поездки: "+trip.getDate());
            driverNameTextView.setText("Имя водителя: "+trip.getDriverName());
            durationTextView.setText("Длительность: "+String.valueOf(trip.getDuration()));
            endCityTextView.setText("Конец поездки: "+trip.getEndCity());
            endTimeTextView.setText("Время окончания поездки: "+trip.getEndtime());
            firstPassIdTextView.setText("Первый пассажир: "+trip.getFirstPass_id());
            idDriverTextView.setText("ID Водителя: "+trip.getId_driver());
            passengersTextView.setText("Количество пассажиров: "+String.valueOf(trip.getPassengers()));
            priceTextView.setText("Цена поездки: "+String.valueOf(trip.getPrice()));
            secondPassIdTextView.setText("Второй пассажир: "+trip.getSecondPass_id());
            startCityTextView.setText("Начальный адрес: "+trip.getStartCity());
            startTimeTextView.setText("Время начала поездки: "+trip.getStarttime());
            thirdPassIdTextView.setText("Третий пассажир: "+trip.getThirdPass_id());
            typeTextView.setText("Тип поездки: "+trip.getType());
            typeTextView.setTextColor(Color.parseColor("#323232"));
        }
    }
}