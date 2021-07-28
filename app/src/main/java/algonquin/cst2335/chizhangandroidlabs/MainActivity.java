package algonquin.cst2335.chizhangandroidlabs;

import android.app.Activity;
import android.app.MediaRouteButton;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.BreakIterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 *thisismainjavaclassforloginandbutton
 *@authorchizhang
 *@version1.0
 */

public class MainActivity extends AppCompatActivity {
/**
 *thisholdsthetextatthecenterofthescreen
 */
        TextView tv= null;
/**
 *thisholdstheeditTextasthepassword
 */
        EditText et= null;



    /**
 *thisholdsthebuttonaslogin
 */


@RequiresApi(api= Build.VERSION_CODES.N)
@Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textview=findViewById(R.id.textView);
        EditText cityText=findViewById(R.id.cityTextField);
        Button forecastBtn=findViewById(R.id.forecastButton);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        forecastBtn.setOnClickListener(clk->{

            String cityName=cityText.getText().toString();

            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("getting forecast")
                    .setMessage("we're calling people in " + cityName + " to look outside their window and tell us what's weather like over there. ")
                    .setView(new ProgressBar(MainActivity.this))
                    .show();

        Executor newThread=Executors.newSingleThreadExecutor();
        newThread.execute(()->{
        /*Thisrunsinaseparatethread*/
        try{


        String stringURL="https://api.openweathermap.org/data/2.5/weather?q="
                        +URLEncoder.encode(cityName,"UTF-8")
                        +"&appid=7e943c97096a9784391a981c4d878b22&units=metric&mode=xml";

        URL url=new URL(stringURL);
        HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
        InputStream in=new BufferedInputStream(urlConnection.getInputStream());

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(false);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput( in  , "UTF-8");

            String description = null;
            String iconName = null;
            String current = null;
            String min = null;
            String max = null;
            String humidity = null;

            while(xpp.next()!=XmlPullParser.END_DOCUMENT){


                switch(xpp.getEventType()){

                    case XmlPullParser.START_TAG:

                        if(xpp.getName().equals("temperature")){

                            current = xpp.getAttributeValue(null, "value");  //this gets the current temperature

                            min = xpp.getAttributeValue(null, "min"); //this gets the min temperature

                            max = xpp.getAttributeValue(null, "max"); //this gets the max temperature

                        }else if(xpp.getName().equals("weather")){

                            description = xpp.getAttributeValue(null, "value");  //this gets the weather description

                            iconName = xpp.getAttributeValue(null, "icon"); //this gets the icon name

                        }else if(xpp.getName().equals("humidity")){

                            humidity = xpp.getAttributeValue(null, "value");

                        }


                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        break;

                }

            }

        String text=(new BufferedReader(
                new InputStreamReader(in,StandardCharsets.UTF_8)))
        .lines()
        .collect(Collectors.joining("\n"));


        Bitmap image=null;

        File file=new File(getFilesDir(),iconName+".png");
            if(file.exists()){

            image=BitmapFactory.decodeFile(getFilesDir()+"/"+iconName+".png");

            }else{
            URL imgUrl=new URL("https://openweathermap.org/img/w/"+iconName+".png");
            HttpURLConnection connection=(HttpURLConnection)imgUrl.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
                if(responseCode == 200){
                image=BitmapFactory.decodeStream(connection.getInputStream());

                image.compress(Bitmap.CompressFormat.PNG,100,openFileOutput(iconName+".png",Activity.MODE_PRIVATE));
        //ImageViewiv=findViewById(R.id.icon);
        //iv.setImageBitmap(image);
        //iv.setVisibility(View.VISIBLE);

                }

            }

            FileOutputStream fOut = null;
            try {
                fOut = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }


        Bitmap finalImage=image;
            String finalCurrent = current;
            String finalMin = min;
            String finalMax = max;
            String finalHumidity = humidity;
            String finalDescription = description;
            runOnUiThread(()->{
                TextView tv=findViewById(R.id.temp);
                tv.setText("The current temperature is"+ finalCurrent);
                tv.setVisibility(View.VISIBLE);

                tv=findViewById(R.id.minTemp);
                tv.setText("The min temperature is"+ finalMin);
                tv.setVisibility(View.VISIBLE);

                tv=findViewById(R.id.maxTemp);
                tv.setText("The max temperature is"+ finalMax);
                tv.setVisibility(View.VISIBLE);

                tv=findViewById(R.id.humidity);
                tv.setText("The humidity is"+ finalHumidity +"%");
                tv.setVisibility(View.VISIBLE);

                tv=findViewById(R.id.description);
                tv.setText(finalDescription);
                tv.setVisibility(View.VISIBLE);

                ImageView iv=findViewById(R.id.icon);
                iv.setImageBitmap(finalImage);
                iv.setVisibility(View.VISIBLE);


                dialog.hide();
                 });
            }catch(IOException | XmlPullParserException ioe){

                Log.e("Connection error:",ioe.getMessage());
                }
            });

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
    return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.hide_views:
                currentTemp.setVisibility(View.INVISIBLE);
                maxTemp.setVisibility(View.INVISIBLE);
                minTemp.setVisibility(View.INVISIBLE);
                humidity.setVisibility(View.INVISIBLE);
                description.setVisibility(View.INVISIBLE);
                icon.setVisibility(View.INVISIBLE);
                cityField.setText("");
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}