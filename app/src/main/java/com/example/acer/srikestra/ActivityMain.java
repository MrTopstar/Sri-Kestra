package com.example.acer.srikestra;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ActivityMain extends AppCompatActivity {
    ArrayList<HashMap<String,String>> contactlist;
    Button btnParseJson;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.activitymain);
        Toast.makeText(this,"11",Toast.LENGTH_SHORT).show();

        //json eg
        contactlist=new ArrayList<>();
        btnParseJson=(Button)findViewById(R.id.idbtnparsejson);
        btnParseJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginJsonParsing();
            }
        });
    }

    //json eg
    private void beginJsonParsing() {
        Toast.makeText(this,"11",Toast.LENGTH_SHORT).show();
        try
        {
            JSONObject reader=new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray=reader.getJSONArray("rootnode");
            for (int i=0; i< jsonArray.length();i++){
                try{
                    JSONObject obj=new JSONObject(loadJSONFromAsset());
                    int id= obj.getInt("Id");
                    String name=obj.getString("Name");
                    String surname=obj.getString("Surname");
                    int age=obj.getInt("Age");
                    addingValuesToHasMap(id,name,surname,age);

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
            ListView lv=(ListView)findViewById(R.id.idLvjson);
            ListAdapter adapter=new SimpleAdapter(ActivityMain.this,contactlist,R.layout.list_item,
                    new String[]{"Id","Name","Surname","Age"},
                    new int[]{R.id.idsno,R.id.idName,R.id.idsurname,R.id.idAge});
            lv.setAdapter(adapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    private String loadJSONFromAsset() {
        String json=null;
        try{
            InputStream inputStream=this.getAssets().open(
                    "data.json");
            int size= inputStream.available();
            byte[] buffer=new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json=new String(buffer,"UTF-8");
        }catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void addingValuesToHasMap(int id, String name, String surname, int age) {
        HashMap<String,String> contact=new HashMap<>();
        contact.put("Id",Integer.toString(id));
        contact.put("Name",name);
        contact.put("Surname",surname);
        contact.put("Age",Integer.toString(age));
        contactlist.add(contact);
    }
}
