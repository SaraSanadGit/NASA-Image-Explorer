package com.sara.nasaimageexplorer;


import android.os.Bundle;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;





/**
 * Help Activity.
 *
 * Shows application instructions.
 *
 * @author Sara
 * @version 1.0
 */
public class HelpActivity extends AppCompatActivity {



    private TextView tvHelpContent;





    @Override
    protected void onCreate(Bundle savedInstanceState){



        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_help);





        tvHelpContent =

                findViewById(

                        R.id.tvHelpContent

                );





        tvHelpContent.setText(



                "NASA Image Explorer Instructions\n\n" +

                        "1. Home displays NASA Astronomy Picture " +

                        "of the Day.\n\n" +

                        "2. Search NASA Image allows searching " +

                        "by selected date.\n\n" +

                        "3. Save images to Favorites for later viewing.\n\n" +

                        "4. Favorites contains your saved NASA images."



        );




    }




}