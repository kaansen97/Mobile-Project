package com.example.stepapp.ui.profile;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.stepapp.MainActivity;
import com.example.stepapp.R;
import com.example.stepapp.ui.message.MessageFragment;
import com.example.stepapp.ui.report.HourFragment;
import com.example.stepapp.ui.setting.SettingFragment;


import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private Button ImCh;
    CircleImageView profile_image;
    private Button user;
    private EditText edit ;
    private AlertDialog dialog;
    private TextView Username;
    private ImageView reports;
    private ImageView message;
    private ImageView daily_message;
    private ImageView setting;
    private final int GALLERY_REQ_CODE=1000;
    private Fragment fragmentClass;
    private AlertDialog daily_dialog;
    private MenuItem menuItem;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        ImCh =(Button) root.findViewById(R.id.fab);
        user=(Button) root.findViewById(R.id.EditName);
        profile_image =root.findViewById(R.id.profile_image);
        Username = root.findViewById(R.id.Username);
        reports=root.findViewById(R.id.Reports);
        edit= new EditText(getContext());
        message=root.findViewById(R.id.inbox);
        daily_message=root.findViewById(R.id.daily_message);
        dialog= new AlertDialog.Builder(getContext()).create();
        dialog.setTitle("Edit your username here");
        dialog.setView(edit);
        daily_dialog= new AlertDialog.Builder(getContext()).create();
        daily_dialog.setTitle("Enter Daily Message Here");
        daily_dialog.setView(edit);
        setting=root.findViewById(R.id.settings);






        ImCh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent iGallery = new Intent(Intent.ACTION_PICK);
               iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               startActivityForResult(iGallery,GALLERY_REQ_CODE);
            }
        });
        dialog.setButton(DialogInterface.BUTTON_POSITIVE,"Submit",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface,int i){
                Username.setText(edit.getText());
            }
                });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.setText(Username.getText());
                dialog.show();

            }
        });

        daily_dialog.setButton(DialogInterface.BUTTON_POSITIVE,"Submit",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface,int i){
                Toast.makeText(getActivity(), "Daily Message Saved", Toast.LENGTH_SHORT).show();
            }
        });

        daily_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daily_dialog.show();


            }
        });

        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    fragmentClass =(Fragment) HourFragment.class.newInstance();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }


                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragmentClass).commit();
            }

        });
        message.setOnClickListener(new View.OnClickListener() {
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

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    fragmentClass =(Fragment) SettingFragment.class.newInstance();
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                profile_image.setImageURI(data.getData() );

    }
}
