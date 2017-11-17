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
import android.widget.ScrollView;
import android.widget.TextView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.view.MenuItem;

public class secondpage extends AppCompatActivity {
    private static final String database_url = "jdbc:mysql://pugpen.org:3306/pugpen67_inventory_mobile";
    private static final String database_user = "pugpen67_dev";
    private static final String database_pass = "Group7&";
    private TextView getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondpage);



       // AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.itemText);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Item);
       // autoCompleteTextView.setAdapter(adapter);


        /*getData = (TextView) findViewById(R.id.tableView);
        new getDataFromDatabase().execute();
        Button homebutton = (Button) findViewById(R.id.homebutton);
        homebutton.setOnClickListener(new View.OnClickListener()
                                        {    @Override
                                        public void onClick(View v)
                                        {
                                            startActivity(new Intent(getApplicationContext(), inventory.class));
                                        }//end getDataFromDatabase
                                        }//end OnClickListener
        );//end firstQueryButton.setOnClickListener
*/
        getData = (TextView) findViewById(R.id.tableView);
        Button itemList = (Button) findViewById(R.id.itemList);
        //set the onClick listener for the button
        itemList.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View v){
                                                  new getDataFromDatabase().execute();
                                              }//end getDataFromDatabase
                                          }//end OnClickListener
        );//end loadDataButton.setOnClickListener

        Button btn = (Button) findViewById(R.id.userbutton);
        btn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), UserActivity.class));
            }

        });
        Button scan = (Button) findViewById(R.id.scanbutton);
        scan.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), ScanActivity.class));
            }

        });

    }

    //private static String[] Item = new String[]{"Bread","Milk","Chedder Cheese","Water","Ham","Turkey", "Roast Beef", "Ground Beef", "Ribeye Steak", "Strip Steak","Filet Mignon","Sirloin", "Twinkies","Ho Hos", "Egg Noodles"};



    private class getDataFromDatabase extends AsyncTask<Void, Void, Void> {
        //references: http://developer.android.com/reference/android/os/AsyncTask.html
        //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
        private String queryResult;
        protected Void doInBackground(Void... arg0)  {
            try {
                queryResult = "Database connection success\n";

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(database_url, database_user, database_pass);
                String queryString = "select * from item_lst";

                Statement st = con.createStatement();
                final ResultSet rs = st.executeQuery(queryString);
                ResultSetMetaData rsmd = rs.getMetaData();
               // rs.next();

                //do some things with the data you've retrieved
               /*while (rs.next()) {
                    queryResult += rsmd.getColumnName(1) + ": " + rs.getString(1) + "\n";
                    queryResult += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
                }*/


                while (rs.next()) {
                     int item_id = rs.getInt(1);
                     String item_name = rs.getString(2);
                     int qty = rs.getInt(3);
                     int price = rs.getInt(4);
                     int unit_cost = rs.getInt(5);
                    queryResult += " item_id -" + item_id + "\n item_name -" + item_name + "\n qty -" + qty + "\n unit_cost -" + unit_cost + "\n";


                 }


                con.close(); //close database connection
            } catch (Exception e) {
                e.printStackTrace();
                //put the error into the TextView on the app screen
                queryResult = "Database connection failure\n" +  e.toString();
            }

            return null;
        }//end database connection via doInBackground

        //after processing is completed, post to the screen
        protected void onPostExecute(Void result) {
            //put the results into the TextView on the app screen
            getData.setText(queryResult);
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

