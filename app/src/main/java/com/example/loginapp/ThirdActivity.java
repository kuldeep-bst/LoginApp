package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ThirdActivity extends AppCompatActivity {

    ImageView imageView;
    Button logOutBtn;
    TextView name;
    TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        imageView=findViewById(R.id.profileImg);
        name=findViewById(R.id.name);
        logOutBtn=findViewById(R.id.signout);
        email=findViewById(R.id.email);

        AccessToken accessToken=AccessToken.getCurrentAccessToken();

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            String url=object.getJSONObject("picture").getJSONObject("data").getString("url");
                            String fullName=object.getString("name");
//                            String user_email = object.getString("email");
                            String user_email = response.getJSONObject().optString("email");
                            String em="kuldeepbisht91856@gmail.com";
                            name.setText(fullName);
                            Picasso.get().load(url).into(imageView);
                            email.setText(em);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,picture.type(large),email");
        request.setParameters(parameters);
        request.executeAsync();



        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                startActivity(new Intent(ThirdActivity.this,MainActivity.class));
                finish();
            }
        });



    }
}