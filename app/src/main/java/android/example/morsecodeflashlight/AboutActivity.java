package android.example.morsecodeflashlight;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("About");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}