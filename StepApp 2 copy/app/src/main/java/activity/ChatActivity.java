 package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.stepapp.R;

 public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);
    }
}