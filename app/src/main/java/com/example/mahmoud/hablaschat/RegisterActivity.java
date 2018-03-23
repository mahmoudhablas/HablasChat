package com.example.mahmoud.hablaschat;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    static final String CHAT_PREFS = "ChatPrefs";
    static final String DISPLAY_NAME_KEY = "username";
    /// var of layout
    EditText email , password, confirmPassword;
    /// create user firbase
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (AutoCompleteTextView) findViewById(R.id.email_text);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mAuth = FirebaseAuth.getInstance();
    }
    private boolean IsVaildPassword(String password)
    {
        return confirmPassword.getText().toString().equals(password) && password.length() > 9;
    }
    private boolean IsVaildEmail(String email)
    {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public void signUp(View v) {
        attemptRegistration();
    }

    private void attemptRegistration() {

        // Reset errors displayed in the form.
//        mEmailView.setError(null);
//        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email_t = email.getText().toString();
        String password_t = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password_t) && !IsVaildPassword(password_t)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email_t)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        } else if (!IsVaildEmail(email_t)) {
            email.setError(getString(R.string.error_invalid_email));
            focusView = email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: Call create FirebaseUser() here
            createFirebaseUser();

        }
    }
    public void createUser(View v)
    {
        createFirebaseUser();
    }
    private void createFirebaseUser()
    {
        String emailS = email.getText().toString();
        String pass = password.getText().toString();
        mAuth.createUserWithEmailAndPassword(emailS,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful())
                {
                    showErrorDialog("Registeration attempt failed");
                }else{
                    saveDiplayName();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    finish();
                    startActivity(intent);

                }
            }
        });
    }
    private void saveDiplayName()
    {
        String displayName = email.getText().toString();
        SharedPreferences prefs = getSharedPreferences(CHAT_PREFS,0);
        prefs.edit().putString(DISPLAY_NAME_KEY,displayName).apply();
    }
    private void showErrorDialog(String messages)
    {
        new AlertDialog.Builder(this)
                .setTitle("Oops!").
                setMessage(messages).
                setPositiveButton(android.R.string.ok,null)
                .setIcon(android.R.drawable.ic_dialog_alert).show();
    }
}
