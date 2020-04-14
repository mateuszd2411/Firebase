package com.mat.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
            protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull CarModel carModel) {

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };

    }
}
