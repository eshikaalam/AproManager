package com.example.AproManager;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class signUpActivity extends AppCompatActivity {

    Button signUp ;
    CardView signUp_google,signUp_microsoft ;
    EditText userEmail ,userPassword ;
    private SignInClient oneTapClient;
    private BeginSignInRequest signUpRequest;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUp=findViewById(R.id.signupbtn);
        signUp_google=findViewById(R.id.card_google);


        oneTapClient = Identity.getSignInClient(this);
        signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.clientID))
                        // Show all accounts on the device.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();

        ActivityResultLauncher<IntentSenderRequest> ActivityResultLauncher=
                registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                          if(result.getResultCode()== Activity.RESULT_OK){
                                try {
                                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                                    String idToken = credential.getGoogleIdToken();
                                    if (idToken !=  null) {
                                       String email=credential.getId();
                                       String name=credential.getDisplayName();
                                       Uri profileLink=credential.getProfilePictureUri();
                                        Toast.makeText(signUpActivity.this, email+"\n"+name, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (ApiException e) {
                                    e.printStackTrace();
                                }

                        }


                    }
                });

        signUp_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneTapClient.beginSignIn(signUpRequest)
                        .addOnSuccessListener(signUpActivity.this, new OnSuccessListener<BeginSignInResult>() {
                            @Override
                            public void onSuccess(BeginSignInResult result) {
                                //                                    startIntentSenderForResult(
//                                            result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
//                                            null, 0, 0, 0);
                                IntentSenderRequest intentSenderRequest=
                                        new IntentSenderRequest.Builder(result.getPendingIntent().getIntentSender()).build();
                                ActivityResultLauncher.launch(intentSenderRequest);
                            }
                        })
                        .addOnFailureListener(signUpActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // No Google Accounts found. Just continue presenting the signed-out UI.

                                //Log.d(TAG, e.getLocalizedMessage());
                            }
                        });


            }
        });



    }
}