package com.example.capston_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Loginloading extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private SignInButton btn_google;
    public static UserInfo user = new UserInfo();
    public static Weight weight = new Weight();
    public static Result result = new Result();
    public static Tip tip = new Tip();
    public static Info info = new Info();
    private FirebaseAuth auth;
    //    private GoogleApiClient googleApiClient;
    private static final int REQ_SIGN_GOOGLE = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baselogin);
        btn_google = findViewById(R.id.btn_goole);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        auth = FirebaseAuth.getInstance();

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_SIGN_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                resultLogin(account);
            } catch (ApiException e) {
                Log.w("Error", "Google sign in failed", e);
            }
        }
    }

    private void resultLogin(final GoogleSignInAccount account) {
        Log.d("info", "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("info", "signInWithCredential:success");
                            Toast.makeText(Loginloading.this, "로그인성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            user.id=account.getEmail().split("@")[0];
                            getObject();
                        } else {
                            Log.w("info", "signInWithCredential:failure", task.getException());
                            Toast.makeText(Loginloading.this, "로그인실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getObject() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference userRef = database.getReference("User/"+user.id+"/");

       userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userinfo = dataSnapshot.getValue(UserInfo.class);
                if (userinfo == null) {
                    return;
                } else {
                    user = userinfo;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        DatabaseReference resultRef = database.getReference("문진결과/");
        resultRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Result resultinfo = dataSnapshot.getValue(Result.class);
                if (resultinfo == null) {
                    return;
                } else {
                    result = resultinfo;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        DatabaseReference tipRef = database.getReference("팁/");
        tipRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Tip tipinfo = dataSnapshot.getValue(Tip.class);
                if (tipinfo == null) {
                    return;
                } else {
                    tip = tipinfo;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        DatabaseReference infoRef = database.getReference("정보/");
        infoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Info infoinfo = dataSnapshot.getValue(Info.class);
                if (infoinfo == null) {
                    return;
                } else {
                    info = infoinfo;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQ_SIGN_GOOGLE);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}