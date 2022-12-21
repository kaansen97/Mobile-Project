package com.example.stepapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stepapp.ui.message.ChatFragment;

import java.util.ArrayList;


public class ViewCourses extends Fragment {

    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<CourseModel> courseModalArrayList;
    private DBManager dbHandler;
    private CourseRVAdapter courseRVAdapter;
    private RecyclerView coursesRV;
    private Button button;
    private Fragment fragmentClass;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }
//        setContentView(R.layout.fragment_emp_list);

        View root = inflater.inflate(R.layout.fragment_view_database, container, false);
        // initializing our all variables.
        courseModalArrayList = new ArrayList<>();
        dbHandler = new DBManager(getContext());

        // getting our course array
        // list from db handler class.
        courseModalArrayList = dbHandler.readCourses();

        // on below line passing our array lost to our adapter class.
        courseRVAdapter = new CourseRVAdapter(courseModalArrayList, getContext());
        coursesRV = root.findViewById(R.id.idRVCourses);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        coursesRV.setLayoutManager(linearLayoutManager);

        button=root.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    fragmentClass =(Fragment) ChatFragment.class.newInstance();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragmentClass).commit();


            }
        });

        // setting our adapter to recycler view.
        coursesRV.setAdapter(courseRVAdapter);
        return root;
    }
}
