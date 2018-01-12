package com.appproj.vd.shyambaba;

/**
 * Created by delaroystudios on 10/5/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appproj.vd.shyambaba.helper.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UpdateData extends AppCompatActivity {

    String BASE_URL = "http://www.delaroystudios.com";

    TextView edt_name, edt_age, edt_mobile, edt_mail;
    ImageView btn_update;

    String id, name, age, mobile, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edt_name = (TextView) findViewById(R.id.tvEventNameServer);
        edt_age = (TextView) findViewById(R.id.tvEventPlaceServer);
        edt_mobile = (TextView) findViewById(R.id.tvEventDateServer);
        edt_mail = (TextView) findViewById(R.id.tvEventTimeServer);
        btn_update = (ImageView) findViewById(R.id.viewAllEvents);

        Intent i = getIntent();
        id = i.getStringExtra("id");
        name = i.getStringExtra("name");
        age = i.getStringExtra("age");
        mobile = i.getStringExtra("mobile");
        email = i.getStringExtra("email");

        edt_name.setText(name);
        edt_age.setText(age);
        edt_mobile.setText(mobile);
        edt_mail.setText(email);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*update_data(id);*/
                Intent intent = new Intent(getApplicationContext(),DisplayData.class);
                startActivity(intent);

            }
        });

    }

    public void update_data(String id) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL) //Setting the Root URL
                .build();

        AppConfig.update api = adapter.create(AppConfig.update.class);

        api.updateData(
                id,
                edt_name.getText().toString(),
                edt_age.getText().toString(),
                edt_mobile.getText().toString(),
                edt_mail.getText().toString(),
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {

                        try {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                            String resp;
                            resp = reader.readLine();
                            Log.d("success", "" + resp);

                            JSONObject jObj = new JSONObject(resp);
                            int success = jObj.getInt("success");

                            if (success == 1) {
                                Toast.makeText(getApplicationContext(), "Successfully updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),DisplayData.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (IOException e) {
                            Log.d("Exception", e.toString());
                        } catch (JSONException e) {
                            Log.d("JsonException", e.toString());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(UpdateData.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}

