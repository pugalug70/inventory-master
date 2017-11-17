package com.example.aleya.inventory;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;

import com.mysql.jdbc.Driver;

public class inventory extends AppCompatActivity {

    private static final String database_url = "jdbc:mysql://pugpen.org:3306/pugpen67_inventory_mobile";
    private static final String database_user = "pugpen67_dev";
    private static final String database_pass = "Group7&";
    private TextView getData;
     Button submit;
     EditText userName;
     EditText password;
    Button register1;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);


                register1 = (Button) findViewById(R.id.registerbutton1);
                submit = (Button) findViewById(R.id.submit);
                userName = (EditText) findViewById(R.id.userName);
                password = (EditText) findViewById(R.id.password);


                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getDataFromDatabase getDataFromData = new getDataFromDatabase();// this is the Asynctask, which is used to process in background to reduce load on app process
                        getDataFromData.execute();
                        startActivity(new Intent(getApplicationContext(), secondpage.class));


                    }//end getDataFromDatabase
                    //end OnClickListener
                });

                //End Setting up the function when button login is clicked
                submit.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  startActivity(new Intent(getApplicationContext(), secondpage.class));
                                              }//end getDataFromDatabase
                                          }//end OnClickListener
                );//end firstQueryButton.setOnClickListener


            ///end onCreate

    //End Setting up the function when button login is clicked
                register1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), UserActivity.class));
        }//end getDataFromDatabase
    }//end OnClickListener
                );//end firstQueryButton.setOnClickListener


}///end onCreate



private class getDataFromDatabase extends AsyncTask<String, String, String> {
                //references: http://developer.android.com/reference/android/os/AsyncTask.html
                //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
                private String queryResult;
                String z = " ";
                Boolean isSuccess = false;

                protected String doInBackground(String... params) {
                    String usernam = userName.getText().toString();
                    String passwordd = password.getText().toString();
                    if (usernam.trim().equals("") || passwordd.trim().equals(""))
                        z = "Please enter Username and Password";
                    else {


                        try {
                            // queryResult = "Database connection success\n";


                            Class.forName("com.mysql.jdbc.Driver");
                            Connection con = DriverManager.getConnection(database_url, database_user, database_pass);
                            String queryString = "Select * from pugpen67_inventory_mobile where pugpen67_inventory_mobile .User_lst='1' and pugpen67_inventory_mobile .password='tester'";

                            Statement st = con.createStatement();
                            final ResultSet rs = st.executeQuery(queryString);

                            if (rs.next()) {
                                z = "Login successful";
                                isSuccess = true;
                                con.close();
                            } else {
                                z = "Invalid Credentials!";
                                isSuccess = false;
                            }


                            // con.close(); //close database connection
                        } catch (Exception ex) {
                            isSuccess = false;
                            z = ex.getMessage();

                        }
                    }

                    return z;
                }

                protected void onPreExecute() {
                    progressBar.setVisibility(View.VISIBLE);
                }

                //end database connection via doInBackground


                //after processing is completed, post to the screen

                protected void onPostExecute(String result) {
                    //put the results into the TextView on the app screen
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(inventory.this, result, Toast.LENGTH_SHORT).show();
                    if (isSuccess) {
                        Toast.makeText(inventory.this, "Login Successfull", Toast.LENGTH_LONG).show();
                        //finish();
                    }
                }
            }//end getDataFromDatabase()


            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_db, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    return true;
                }

                return super.onOptionsItemSelected(item);
            }
        }


