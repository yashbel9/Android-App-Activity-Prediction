package com.example.android.group23_ass3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;

import java.io.File;

import static com.example.android.group23_ass3.DatabaseHelper.DATABASE_FILE_PATH;

public class MainActivity extends AppCompatActivity {
    Boolean activityFlag=false;
    public GraphView graph;
    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*if(!isStoragePermissionGranted()) {
            System.out.println("Storage Permission Denied!");
            Toast.makeText(MainActivity.this, "Storage Permission Denied!", Toast.LENGTH_SHORT).show();
            return;
        }*/
        checkPerm();
        final DatabaseHelper db=new DatabaseHelper(this);
        db.createDatabase(this);
        Button power_tutor=(Button)findViewById(R.id.power_tutor);
        Button visualize=(Button)findViewById(R.id.button_viz);
        Button startActivity=findViewById(R.id.btn_start);
        final RadioGroup activityType = (RadioGroup) findViewById(R.id.radioGroup);
        Button predict = (Button)findViewById(R.id.btn_svm);
        graph = (GraphView) findViewById(R.id.graph);
        imageView=(ImageView)findViewById(R.id.imageView);
        Viewport viewport=graph.getViewport();
        viewport.setXAxisBoundsManual(true);
        viewport.setMaxX(1000);
        viewport.setMinX(0);

        power_tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Toast.makeText(getApplicationContext(),"Refer to readme.txt, data plotted for 10 iterations", Toast.LENGTH_SHORT).show();
                graph.setVisibility(v.VISIBLE);
                imageView.setVisibility(v.INVISIBLE);

                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                        // these points are recorded using the powertutor application... ( execution time,power consumption)
                        new DataPoint(73, 24),
                        new DataPoint(186, 50),
                        new DataPoint(269, 40),
                        new DataPoint(368, 27),
                        new DataPoint(488, 29),
                        new DataPoint(596, 29),
                        new DataPoint(711, 31),
                        new DataPoint(771, 21),
                        new DataPoint(849, 26),
                        new DataPoint(959, 26)
                });
                GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
                gridLabel.setHorizontalAxisTitle("Execution time");
                gridLabel.setVerticalAxisTitle("Power Consumption");
                graph.addSeries(series);*/
                SvmPredict svmPredict = new SvmPredict();
                Toast.makeText(MainActivity.this, "Prediction:Subject is "+svmPredict.predictAction(), Toast.LENGTH_SHORT).show();
            }
        });

        visualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Visualization.class);
                MainActivity.this.startActivity(intent);
            }
        });

        startActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = activityType.getCheckedRadioButtonId();
                RadioButton r_selected = (RadioButton) findViewById(selected);
                String activity = "";
                if(r_selected != null){
                    activity = r_selected.getText().toString().trim();
                }
                if(!activityFlag){
                    SQLiteDatabase dbhelper = null;
                    Boolean mSuccess = false;
                    File  mFolderStructure = new File(DATABASE_FILE_PATH);
                    if (!mFolderStructure.exists()) {
                        mSuccess = mFolderStructure.mkdir();
                    } else {
                        System.out.println("Directory already exists");
                        mSuccess = true;
                    }
                    if(!mSuccess) {
                        System.out.println("Cannot create Directory");
                    }
                    db.insertRows(v.getContext(),activity);
                    Intent startSenseService = new Intent(v.getContext(), SensorService.class);
                    v.getContext().startService(startSenseService);

                }
            }
        });

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String fileName = Environment.getExternalStorageDirectory().getAbsolutePath()+"/trainingData.txt";
                String fileName = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "Android"+ File.separator +"Data"+ File.separator + "CSE535_ASSIGNMENT3/trainingData.txt";
                SQLiteDatabase dbhelper = null;
                Boolean mSuccess = false;
                File  mFolderStructure = new File(DATABASE_FILE_PATH);
                if (!mFolderStructure.exists()) {
                    mSuccess = mFolderStructure.mkdir();
                } else {
                    System.out.println("Directory already exists");
                    mSuccess = true;
                }
                if(!mSuccess) {
                    System.out.println("Cannot create Directory");
                }
                DatabaseHelper.convertFile(v.getContext(),fileName,DatabaseHelper.extractData(v.getContext())) ;
                Intent intent = new Intent(MainActivity.this,SvmPredict.class);
                startActivity(intent);
            }
        });
    }

    private void checkPerm() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "App requires permission to work", Toast.LENGTH_SHORT).show();
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Toast.makeText(this, "App requires permission to work", Toast.LENGTH_SHORT).show();

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        2);
            }
        }
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                System.out.println("WRITE_EXTERNAL_STORAGE Permission is granted");
                return true;
            } else {

                System.out.println("WRITE_EXTERNAL_STORAGE Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            System.out.println("WRITE_EXTERNAL_STORAGE Permission is granted");
            return true;
        }
    }

    public static void stopService(Context context) {
        Intent startSenseService = new Intent(context, SensorService.class);
        Log.v("Sensor - main activity","stopping service");
        context.stopService(startSenseService);
    }
}



