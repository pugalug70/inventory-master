package com.example.aleya.inventory;

/**
 * Created by TiemLe on 11/15/17.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
public class UsersActivity extends Activity {
    private static final String database_url = "jdbc:mysql://pugpen.org:3306/pugpen67_inventory_mobile";
    private static final String database_user = "pugpen67_dev";
    private static final String database_pass = "Group7&";
    // placeholder that you will be updating with the database data
    private TextView getData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_layout);
        // find the screen element that you need
        Button scan = (Button) findViewById(R.id.scanbutton);
        scan.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), ScanActivity.class));
            }

        });
        Button home = (Button) findViewById(R.id.homebutton);
        home.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), inventory.class));
            }

        });
        getData = (TextView) findViewById(R.id.queryResults1);


        Button loadDataButton = (Button) findViewById(R.id.getDataButton1);
        //set the onClick listener for the button
        loadDataButton.setOnClickListener(new View.OnClickListener()
                                          {
                                              @Override
                                              public void onClick(View v){
                                                  new getDataFromDatabase().execute();
                                              }//end getDataFromDatabase
                                          }//end OnClickListener
        );//end loadDataButton.setOnClickListener
    }//end onCreate

    private class getDataFromDatabase extends AsyncTask<Void, Void, Void> {
        //references: http://developer.android.com/reference/android/os/AsyncTask.html
        //            https://www.youtube.com/watch?v=N0FLT5NdSNU (about the 7 min mark)
        private String queryResult;
        protected Void doInBackground(Void... arg0)  {
            try {
                queryResult = "Database connection success\n";

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(database_url, database_user, database_pass);
                String queryString = "select * from pugpen67_inventory_mobile.user_lst";
                Statement st = con.createStatement();
                final ResultSet rs = st.executeQuery(queryString);
                ResultSetMetaData rsmd = rs.getMetaData();
                //do some things with the data you've retrieved
                while (rs.next()) {
                    // queryResult += rsmd.getColumnName(1)+ " : " + rs.getString(1) + "\n";
                    // queryResult += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
                   // queryResult += rsmd.getColumnName(3) + ": " + rs.getString(3) + "\n";
                   // queryResult += rsmd.getColumnName(4) + ": " + rs.getString(4) + "\n";
                    //queryResult += rsmd.getColumnName(5) + ": " + rs.getString(5) + "\n";
                }
                con.close(); //close database connection
            } catch (Exception e) {
                e.printStackTrace();
                //put the error into the TextView on the app screen
                queryResult = "Database connection failure\n" +  e.toString();
            }

            return null;
        }
        //after processing is completed, post to the screen
        protected void onPostExecute(Void result) {
            //put the results into the TextView on the app screen
            getData.setText(queryResult);
        }

    }

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

