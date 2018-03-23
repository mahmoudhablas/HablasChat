package com.example.mahmoud.hablaschat;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView ;
    private EditText mPasswordView;
    private Button butn ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email_text);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.password || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mAuth = FirebaseAuth.getInstance();

    }
    public void registerNewUser(View v) {
        Intent intent = new Intent(this,RegisterActivity.class);
        finish();
        startActivity(intent);
    }
    public void signWithNewUser(View v)
    {
        attemptLogin();
    }
    private void attemptLogin(){
        String email = mEmailView.getText().toString();
        String pass = mPasswordView.getText().toString();

        if(email.isEmpty())
        {
            if (email.equals("") || pass.equals(""))
            {
                return;
            }
        }

        Toast.makeText(this,"Login in progress",Toast.LENGTH_SHORT);
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful())
                {
                    Log.d("FlashChat", "Problem signing in: " + task.getException());
                    showErrorDialog("There was a problem signing in");
                }else
                {
                    Intent mainIntent = new Intent(LoginActivity.this,HabalsMain.class);
                    finish();
                    startActivity(mainIntent);
                }
            }
        });


    }
    private void showErrorDialog(String message) {

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
