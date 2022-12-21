package com.example.stepapp.ui.message;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorEventListener;
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
import com.example.stepapp.ui.home.HomeFragment;
import com.example.stepapp.ui.profile.ProfileFragment;
import com.example.stepapp.ui.report.HourFragment;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageFragment extends Fragment {
    private ImageView back;
    private Fragment fragmentClass;
    private RelativeLayout messageBox;
    private TextView messageName, messageContent;
    private DBManager dbHandler;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }
        View root = inflater.inflate(R.layout.fragment_message, container, false);
        back=root.findViewById(R.id.back_arrow);
        messageBox=root.findViewById(R.id.messagebox);
        messageName = root.findViewById(R.id.name_chat6);
        messageContent = root.findViewById(R.id.message_6);
        dbHandler = new DBManager(getContext());

//        if (dbHandler.readCoursesString(2,1)!=null){
        messageName.setText(dbHandler.readCoursesString(-1,1));
        messageContent.setText(dbHandler.readCoursesString(-1,2));
//        }

        messageBox.setOnClickListener(new View.OnClickListener() {
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


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    fragmentClass =(Fragment) ProfileFragment.class.newInstance();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragmentClass).commit();


            }
        });



        return root;
    }

}

