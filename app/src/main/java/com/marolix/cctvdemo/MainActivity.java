package com.marolix.cctvdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView autoText;
    ListView listView;
    ArrayList<String> searchList;
    DatabaseClass database;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoText = findViewById(R.id.autocomplete);
        listView = findViewById(R.id.listView);
        searchList = new ArrayList<>();


        database = new DatabaseClass(MainActivity.this);

        pref = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        editor = pref.edit();


        autoText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

//                Uri.Builder builder = new Uri.Builder();
                Log.e("OnText changed", "called");
                if (!s.equals("")) {

                    searchList = database.getVideos(s.toString());
                } else {
                    searchList.clear();
                }
//                Log.e("Search list", s+" >>>> "+searchList.toString());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, searchList);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, ""+searchList.get(position), Toast.LENGTH_SHORT).show();
                String url = database.getVideoUrl(searchList.get(position));
                autoText.setText("");
                Intent intent = new Intent(MainActivity.this, VideoPlayerActivity.class);
                intent.putExtra("VID_URL", url);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pref.getBoolean("firstRun", true)) {
            //You can perform anything over here. This will call only first time
            editor = pref.edit();
            database.storeCountry("India");
            database.storeCountry("USA");
            database.storeCountry("China");
            database.storeCountry("Australia");
            database.storeCountry("England");
            database.storeCountry("Japan");
            database.storeCountry("Russia");

            database.storeData("India","","Hyderabad", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
            database.storeData("India","","Bangalore", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
            database.storeData("India","","Delhi", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
            database.storeData("USA","","California", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4");
            database.storeData("USA","","LasVegas", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4");
            database.storeData("India","","Chennai", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4");
            database.storeData("USA","","NYC", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4");
            database.storeData("India","","Gachibowli", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4");
            database.storeData("Japan","","Japan", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
            database.storeData("USA","","CA", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
            database.storeData("Australia","","Sydney", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");

            /* database.storeData("India", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4");
            database.storeData("English", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4");
            database.storeData("Hindi", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4");
            database.storeData("Telugu", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4");
            database.storeData("Tamil", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4");
            database.storeData("Malayalam", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4");
*/
            editor.putBoolean("firstRun", false);
            editor.apply();

        }
    }
}
