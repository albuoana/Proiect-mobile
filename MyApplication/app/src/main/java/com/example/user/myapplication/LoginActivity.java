package com.example.user.myapplication;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.user.myapplication.modelviews.LoginModelView;
import com.example.user.myapplication.user.User;
import com.example.user.myapplication.user.UserController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameField;
    private EditText passwordField;
    private EditText newUsernameField;
    private EditText newPasswordField;
    private UserController userController;
    private LoginModelView loginModelView = new LoginModelView();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.usernameField = findViewById(R.id.editText2);
        this.passwordField = findViewById(R.id.passwordField);
        this.newUsernameField = findViewById(R.id.newUsernameField);
        this.newPasswordField = findViewById(R.id.newpasswordField);
        userController = ViewModelProviders.of(this, new UserController.Factory(getApplicationContext())).get(UserController.class);
    }


    public void loginButton(View view) {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        final Intent redirect = new Intent(this, CartoonActivity.class);

        loginModelView.loginUser(username, password).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()&&response.code()==200) {
                    String token = response.headers().get("Authorization");
                    System.out.println(token);
                    redirect.putExtra("token",token);
                    startActivity(redirect);
                }
                        else {
                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                        alertDialog.setTitle("Info");
                        alertDialog.setMessage("Invalid credentials");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("Info");
                alertDialog.setMessage("Server not connected! ");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }

    public void signUpButton(View view) {
        String username = newUsernameField.getText().toString();
        String password = newPasswordField.getText().toString();
        User user = new User(username, password, "aa");
        loginModelView.addUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null) {
                    User user2 = response.body();
                    userController.createUser(username, password);
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Info");
                    alertDialog.setMessage("S-a adaugat user-ul " + user2.getUsername() + " cu parola " + user.getPassword());
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    System.out.println("Succes");
                } else
                    System.out.println("Insucces");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("Info");
                alertDialog.setMessage("Server not connected! ");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }

}
