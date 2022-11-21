package com.example.mealerapp.screens;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mealerapp.R;
import com.example.mealerapp.objects.Complaint;
import com.example.mealerapp.objects.Database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ComplaintListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Complaint> complaints;
    LayoutInflater inlater;
    ListView lv;
    Database database;

    public ComplaintListAdapter(Context ctx, ArrayList<Complaint> complaints, ListView lv) {
        this.context = ctx;
        this.complaints = complaints;
        this.lv = lv;
        database = new Database();
        inlater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return complaints.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int complaintIndex, View view, ViewGroup viewGroup) {
        view = inlater.inflate(R.layout.content_complaint_list_view, null);

        TextView complaintFrom = view.findViewById(R.id.mealTitle);
        TextView complaintTo = view.findViewById(R.id.mealPrice);
        TextView complaintMessage = view.findViewById(R.id.mealDescription);

        String toEmail = complaints.get(complaintIndex).getToEmail();

        complaintFrom.setText(complaints.get(complaintIndex).getFromEmail());
        complaintTo.setText(toEmail);
        complaintMessage.setText(complaints.get(complaintIndex).getMessage());

        // Dismiss button
        Button dismiss = view.findViewById(R.id.deleteMeal);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complaints.get(complaintIndex).deleteFromDatabase();
                complaints.remove(complaintIndex);
                lv.invalidateViews();
                Toast.makeText(view.getContext(), "Complaint Dismissed!", Toast.LENGTH_LONG).show();
                return;
            }
        });

        // Permanent Suspension onClick
        Button permanentSuspension = view.findViewById(R.id.permSuspension);
        permanentSuspension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getFirestore().collection("users").whereEqualTo("email", toEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().update("permanentSuspension", true);
                                complaints.get(complaintIndex).deleteFromDatabase();
                                complaints.remove(complaintIndex);
                                lv.invalidateViews();
                                Toast.makeText(view.getContext(), "User Permanently Suspended", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });

        // Temporary Suspension onClick
        Button temporarySuspension = view.findViewById(R.id.addRemoveOffered);
        temporarySuspension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePicker = new DatePickerDialog(view.getContext());
                    datePicker.setMessage("Choose the end date of the temporary suspension");

                    datePicker.setButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
                            c.set(datePicker.getDatePicker().getYear(), datePicker.getDatePicker().getMonth(), datePicker.getDatePicker().getDayOfMonth());

                            // Change temporary suspension of user
                            database.getFirestore().collection("users").whereEqualTo("email", toEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            document.getReference().update("tempSuspension", sdf.format(c.getTime()));
                                            complaints.get(complaintIndex).deleteFromDatabase();
                                            complaints.remove(complaintIndex);
                                            lv.invalidateViews();
                                            Toast.makeText(view.getContext(), "User Temporarily Suspended", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });

                        }
                    });
                    datePicker.show();
                }

            }
        });


        return view;
    }
}
