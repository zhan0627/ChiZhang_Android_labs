package algonquin.cst2335.chizhangandroidlabs;

import android.app.Activity;
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
        TextView currentTemp = null;
        TextView minTemp = null;
        TextView maxTemp = null;
        TextView description = null;
        TextView iconName = null;
        TextView humidity = null;
        EditText cityField = null;

    EditText et= null;
    /**
 *thisholdsthebuttonaslogin
 */

        ImageView icon = null;


@RequiresApi(api= Build.VERSION_CODES.N)
@Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=findViewById(R.id.textView);
        cityField=findViewById(R.id.cityTextField);
        Button forecastBtn=findViewById(R.id.forecastButton);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        forecastBtn.setOnClickListener(clk->{

            String cityName=cityField.getText().toString();

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

            String description1 = null;
            String iconName = null;
            String current = null;
            String min = null;
            String max = null;
            String humidity1 = null;


            while(xpp.next()!=XmlPullParser.END_DOCUMENT){


                switch(xpp.getEventType()){

                    case XmlPullParser.START_TAG:

                        if(xpp.getName().equals("temperature")){

                            current = xpp.getAttributeValue(null, "value");  //this gets the current temperature

                            min = xpp.getAttributeValue(null, "min"); //this gets the min temperature

                            max = xpp.getAttributeValue(null, "max"); //this gets the max temperature

                        }else if(xpp.getName().equals("weather")){

                            description1 = xpp.getAttributeValue(null, "value");  //this gets the weather description

                            iconName = xpp.getAttributeValue(null, "icon"); //this gets the icon name

                        }else if(xpp.getName().equals("humidity")){

                            humidity1 = xpp.getAttributeValue(null, "value");

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
            String finalHumidity = humidity1;
            String finalDescription = description1;
            runOnUiThread(()->{

                currentTemp =findViewById(R.id.temp);
                currentTemp.setText("The current temperature is"+ finalCurrent);
                currentTemp.setVisibility(View.VISIBLE);

                minTemp=findViewById(R.id.minTemp);
                minTemp.setText("The min temperature is"+ finalMin);
                minTemp.setVisibility(View.VISIBLE);

                maxTemp=findViewById(R.id.maxTemp);
                maxTemp.setText("The max temperature is"+ finalMax);
                maxTemp.setVisibility(View.VISIBLE);

                humidity=findViewById(R.id.humidity);
                humidity.setText("The humidity is"+ finalHumidity +"%");
                humidity.setVisibility(View.VISIBLE);

                description=findViewById(R.id.description);
                description.setText((CharSequence) finalDescription);
                description.setVisibility(View.VISIBLE);

                icon =findViewById(R.id.icon);
                icon.setImageBitmap(finalImage);
                icon.setVisibility(View.VISIBLE);


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
        float oldSize = 14;

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
            case R.id.id_increase:
                oldSize++;
                currentTemp.setTextSize(oldSize);
                minTemp.setTextSize(oldSize);
                maxTemp.setTextSize(oldSize);
                humidity.setTextSize(oldSize);
                description.setTextSize(oldSize);
                cityField.setTextSize(oldSize);
                break;

            case R.id.id_decrease:
                oldSize = Float.max(oldSize-1, 5);
                currentTemp.setTextSize(oldSize);
                minTemp.setTextSize(oldSize);
                maxTemp.setTextSize(oldSize);
                humidity.setTextSize(oldSize);
                description.setTextSize(oldSize);
                cityField.setTextSize(oldSize);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}