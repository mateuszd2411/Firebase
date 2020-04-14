package com.mat.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    private EditText inputSearch;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingBtn;
    private FirebaseRecyclerOptions<CarModel> options;
    private FirebaseRecyclerAdapter<CarModel,MyViewHolder> adapter;
    private DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dataRef = FirebaseDatabase.getInstance().getReference().child("Car");
        inputSearch = findViewById(R.id.inputSearch);
        recyclerView = findViewById(R.id.recyclerView);
        floatingBtn = findViewById(R.id.floatingbtn);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        LoadData();

    }

    private void LoadData() {

        options = new FirebaseRecyclerOptions.Builder<CarModel>().setQuery(dataRef,CarModel.class).build();
        adapter = new FirebaseRecyclerAdapter<CarModel, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull CarModel carModel) {

                holder.textView.setText(carModel.getCarName());
                Picasso.get().load(carModel.getImageUrl()).into(holder.imageView);
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
                return new MyViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}
