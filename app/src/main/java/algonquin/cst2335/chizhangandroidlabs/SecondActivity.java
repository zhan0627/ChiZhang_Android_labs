package algonquin.cst2335.chizhangandroidlabs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent fromPrevious = getIntent();

        String text = fromPrevious.getStringExtra("EmailAddress");


        float fromMain = fromPrevious.getFloatExtra("myFloat", 0.0f);
        float test = fromPrevious.getFloatExtra("aa", 1.0f);
        boolean t = fromPrevious.getBooleanExtra("isTrue", false);

        TextView top = findViewById(R.id.textView);

        top.setText("myFloat" + fromMain +"test =" + test);

        EditText line = findViewById(R.id.editTextPhone);

        line.setText(text);

        Button call = findViewById(R.id.button);
        call.setOnClickListener(v -> {

            Intent sendBack = new Intent();
            sendBack.putExtra("number", line.getText().toString());
            setResult(345, sendBack);
            
            finish();

        });

    }

}