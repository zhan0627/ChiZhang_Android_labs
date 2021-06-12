package algonquin.cst2335.chizhangandroidlabs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = findViewById(R.id.nextPageButton);
        Log.w(TAG, "In onCreate() - Loading Widgets" );

        loginBtn.setOnClickListener( clk -> {
            et = findViewById(R.id.emailEditText);
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);

            nextPage.putExtra( "EmailAddress", et.getText().toString() );
            nextPage.putExtra("myFloat",3.14f);
            nextPage.putExtra("isTrue", false);

//            Intent call = new Intent(Intent.ACTION_DIAL);
//            call.setData(Uri.parse("tel: "+ "613-240-3854"));

             Intent call = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
             //Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            //startActivityForResult(nextPage, 123);
            startActivityForResult( call, 123);
            //startActivity( nextPage );

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == 123) {
            if (resultCode == RESULT_OK) {
//
//                String number = data.getStringExtra("Number");
//                et.setText(number);
                Bitmap thumbnail = data.getParcelableExtra("data");
                //profileImage.setImageBitmap( thumbnail );
                FileOutputStream fOut = null;
                try {
                    fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();

                } catch (Exception e) {
                    e.printStackTrace();

                }
                String path = getFilesDir().getPath();

                Log.w(TAG, "come back from next page");
            }
        }


    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "In onStart() - no visible" );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "In onStop() - The application is no longer visible." );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "In onResume() - The application is now responding to user input" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "In onPause() - The application no longer responds to user input" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "In onDestory() - Any memory used by the application is freed." );
    }

}