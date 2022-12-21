package com.example.stepapp.ui.message;

import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stepapp.CourseModel;
import com.example.stepapp.CourseRVAdapter;
import com.example.stepapp.DBManager;
import com.example.stepapp.MainActivity;
import com.example.stepapp.R;
import com.example.stepapp.ui.profile.ProfileFragment;
import com.example.stepapp.ui.report.HourFragment;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatFragment extends Fragment {
    private ImageView back;
    private Fragment fragmentClass;
    private ArrayList<CourseModel> courseModalArrayList;
    private DBManager dbHandler;
    private CourseRVAdapter courseRVAdapter;
    private RecyclerView coursesRV;
    private TextView user;
    private TextView goal;
    private TextView step;
    private TextView message;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        back=root.findViewById(R.id.back_arrow);
        user=(TextView) root.findViewById(R.id.grinch);
        goal=(TextView) root.findViewById(R.id.goal);
        step=(TextView) root.findViewById(R.id.step);
        message=(TextView) root.findViewById(R.id.textMessage);

//        user.setText(dbHandler.readCoursesString(-1,1));
//        goal.setText(dbHandler.readCoursesString(-1, 4));
//        step.setText(dbHandler.readCoursesString(-1,3));
//        message.setText(dbHandler.readCoursesString(-1,2));



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    fragmentClass =(Fragment) MessageFragment.class.newInstance();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragmentClass).commit();


            }
        });
//        user = dbHandler.readCourses().
//        courseModalArrayList = dbHandler.readCourses();
//        dbHandler = new DBManager(getActivity());
//        dbHandler.open();
//
//        Cursor cursor = dbHandler.fetch();
//        cursor.moveToFirst();
//        user.setText(cursor.getString(0));




        return root;
    }

}
