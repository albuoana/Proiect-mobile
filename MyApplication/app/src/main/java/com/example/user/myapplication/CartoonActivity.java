package com.example.user.myapplication;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.myapplication.cartoon.Cartoon;
import com.example.user.myapplication.cartoon.CartoonController;
import com.example.user.myapplication.modelviews.CartoonModelView;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartoonActivity extends AppCompatActivity {
    private EditText textFieldNumber;
    private EditText textFieldTitle;
    private EditText textFieldDescription;
    private EditText textFieldScore;
    private EditText textFieldEpisodes;

    private ListView listView;

    private CartoonController cartoonController;

    private int page_nr = 0;
    private int size = 2;

    private Button btnBack;
    private Button btnForward;

    private CartoonModelView cartoonModelView = new CartoonModelView();

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cartoon);

            this.initializeNavigationButtons();
            this.initializeTextFields();
            this.listView = findViewById(R.id.listView);
            //se verifica conexiunea la net si se face sincronizarea datelor
            syncList();
            token = getIntent().getStringExtra("token");

            cartoonController = ViewModelProviders.of(this, new CartoonController.Factory(getApplicationContext())).get(CartoonController.class);
            cartoonModelView.getCartoonsPaginated(size, page_nr, token).enqueue(new Callback<List<Cartoon>>() {
                @Override
                public void onResponse(Call<List<Cartoon>> call, Response<List<Cartoon>> response) {
                    System.out.println("M-am conectat");
                    if (response.body() != null) {
                        List<Cartoon> cartoons = response.body();

                        if (cartoons != null) {
                            ArrayAdapter<Cartoon> arrayAdapter = new ArrayAdapter<Cartoon>(CartoonActivity.this, android.R.layout.simple_list_item_1, cartoons);
                            listView.setAdapter(arrayAdapter);
                        //    for (Cartoon cartoon:cartoons)
                              //  cartoonController.createCartoon(cartoon);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Cartoon>> call, Throwable t) {
                    System.out.println("Nu m-am conectat");
                    List<Cartoon> cartoons = cartoonController.getCartoons(size, page_nr);
                    ArrayAdapter<Cartoon> arrayAdapter = new ArrayAdapter<Cartoon>(CartoonActivity.this, android.R.layout.simple_list_item_1, cartoons);
                    listView.setAdapter(arrayAdapter);
                    if (cartoons.size() == 0)
                        btnForward.setEnabled(false);
                }
            });
            if (this.page_nr == 0)
                btnBack.setEnabled(false);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void initializeNavigationButtons()
    {
        btnBack = (Button) findViewById(R.id.buttonBack);
        btnBack.setText("<");
        btnForward = (Button) findViewById(R.id.buttonForward);
        btnForward.setText(">");
    }


    public void initializeTextFields()
    {
        this.textFieldNumber = findViewById(R.id.textFieldNumber);
        this.textFieldDescription = findViewById(R.id.textFieldDescription);
        this.textFieldTitle = findViewById(R.id.textFieldTitle);
        this.textFieldScore = findViewById(R.id.textFieldScore);
        this.textFieldEpisodes = findViewById(R.id.textFieldEpisodes);
    }


    public void emailButton(View view) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"oana_albu97@yahoo.ro"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Abonare");
        i.putExtra(Intent.EXTRA_TEXT, "In data de " + new Date() + "v-ati abonat la " + textFieldTitle.getText().toString() + ". O sa va tinem la curent cu toate modificarile.");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void prevList(View view) {
        page_nr += size;
        System.out.println(page_nr);
        cartoonModelView.getCartoonsPaginated(size, page_nr,token).enqueue(new Callback<List<Cartoon>>() {
            @Override
            public void onResponse(Call<List<Cartoon>> call, Response<List<Cartoon>> response) {
                if (response.body() != null) {
                    List<Cartoon> cartoons = response.body();
                    ArrayAdapter<Cartoon> arrayAdapter = new ArrayAdapter<Cartoon>(CartoonActivity.this, android.R.layout.simple_list_item_1, cartoons);
                    listView.setAdapter(arrayAdapter);
                    btnBack.setEnabled(true);
                    if (cartoons.size() == 0)
                        btnForward.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<List<Cartoon>> call, Throwable t) {
                List<Cartoon> cartoons = cartoonController.getCartoons(size, page_nr);
                btnBack.setEnabled(true);
                ArrayAdapter<Cartoon> arrayAdapter = new ArrayAdapter<Cartoon>(CartoonActivity.this, android.R.layout.simple_list_item_1, cartoons);
                listView.setAdapter(arrayAdapter);
                if (cartoons.size() == 0)
                    btnForward.setEnabled(false);
            }
        });
    }

    public void nextList(View view) {
        page_nr -= size;
        System.out.println(page_nr);
        cartoonModelView.getCartoonsPaginated(size, page_nr, token).enqueue(new Callback<List<Cartoon>>() {
            @Override
            public void onResponse(Call<List<Cartoon>> call, Response<List<Cartoon>> response) {
                if (response.body() != null) {
                    List<Cartoon> cartoons = response.body();
                    ArrayAdapter<Cartoon> arrayAdapter = new ArrayAdapter<Cartoon>(CartoonActivity.this, android.R.layout.simple_list_item_1, cartoons);
                    listView.setAdapter(arrayAdapter);
                    btnForward.setEnabled(true);
                    if (page_nr == 0)
                        btnBack.setEnabled(false);

                }
            }

            @Override
            public void onFailure(Call<List<Cartoon>> call, Throwable t) {
                List<Cartoon> cartoons = cartoonController.getCartoons(size, page_nr);
                btnForward.setEnabled(true);
                ArrayAdapter<Cartoon> arrayAdapter = new ArrayAdapter<Cartoon>(CartoonActivity.this, android.R.layout.simple_list_item_1, cartoons);
                listView.setAdapter(arrayAdapter);
                if (page_nr == 0)
                    btnBack.setEnabled(false);
            }
        });
    }


    public void addCartoon(View view) {
        Cartoon cartoon = new Cartoon(Integer.parseInt(textFieldNumber.getText().toString()), textFieldTitle.getText().toString(), textFieldDescription.getText().toString(), Integer.parseInt(textFieldScore.getText().toString()), Integer.parseInt(textFieldEpisodes.getText().toString()), false);
        cartoonModelView.addCartoon(cartoon, token).enqueue(new Callback<Cartoon>() {
            @Override
            public void onResponse(Call<Cartoon> call, Response<Cartoon> response) {
                if (response.body() != null) {
                    Cartoon cartoon = response.body();
                    cartoonController.createCartoon(cartoon);
                    page_nr = -1 * size;
                    prevList(view);
                    btnBack.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<Cartoon> call, Throwable t) {
                Cartoon cartoon = new Cartoon(Integer.parseInt(textFieldNumber.getText().toString()), textFieldTitle.getText().toString(), textFieldDescription.getText().toString(), Integer.parseInt(textFieldScore.getText().toString()), Integer.parseInt(textFieldEpisodes.getText().toString()), false);
                cartoonController.createCartoon(cartoon);
                page_nr = -1 * size;
                prevList(view);
                btnBack.setEnabled(false);
            }
        });
    }

    public void syncList() throws InterruptedException {
        Thread requestThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Cartoon> cartoons = cartoonController.getAllCartoons();
                for (Cartoon cartoon : cartoons) {
                    if (cartoon.getAdded() != null && cartoon.getAdded().equals(false)) {
                        cartoonModelView.addCartoon(cartoon,token).enqueue(new Callback<Cartoon>() {
                            @Override
                            public void onResponse(Call<Cartoon> call, Response<Cartoon> response) {
                                if (response.body() != null) {
                                    cartoonController.updateCartoon(cartoon.getId(), true);
                                }
                            }

                            @Override
                            public void onFailure(Call<Cartoon> call, Throwable t) {

                            }
                        });
                    }
                }

            }
        });
        requestThread.start();
    }


}
