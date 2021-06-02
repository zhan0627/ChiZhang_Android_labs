package algonquin.cst2335.chizhangandroidlabs;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mytext = findViewById(R.id.textview);
        Button myButton = findViewById(R.id.mybutton);
        EditText myeditmessgae = findViewById(R.id.myedittext);
        CheckBox mycheckbox = findViewById(R.id.checkBox);
        RadioButton myradiobutton = findViewById(R.id.myradiobutton);
        Switch aSwitch = findViewById(R.id.switch1);
        Switch Switch = findViewById(R.id.switch2);
        ImageView imageview = findViewById(R.id.imageview);
        ImageButton imgbtn = findViewById( R.id.imageButton );
        // myButton.setOnClickListener( vw ->{
        // mytext.setText(get the text from the editText);
        // }
        // );
        String editString = myeditmessgae.getText().toString();
        myButton.setOnClickListener(vw-> {
                    mytext.setText("your edit text has: " + editString);
                }
        );
        mycheckbox.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            if(isChecked)
                mycheckbox.setText("checkbox is on");
            else
                mycheckbox.setText("checkbox is off");
        });
        mycheckbox.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Context context = getApplicationContext();
            CharSequence text = "Hello chelsea!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });
        aSwitch.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Context context = getApplicationContext();
            CharSequence text = "Hello professor!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });
        Switch.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Context context = getApplicationContext();
            CharSequence text = "Have a good day!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });
        myradiobutton.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Context context = getApplicationContext();
            CharSequence text = "Hello everyone!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });
        imgbtn.setOnClickListener ( vw -> {
            Context context = getApplicationContext();
            CharSequence text = "the width = " + imgbtn.getWidth() +"and height = " + imgbtn.getHeight();
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });
    }
}
