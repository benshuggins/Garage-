package com.test.ben.hyperproject;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import com.test.robert.hypergaragesale.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


import static com.test.robert.hypergaragesale.R.id.MyCoordinatorLayout;



public class MainActivity extends AppCompatActivity {
    private CoordinatorLayout myCoordinatorLayout;
    private Button PostButton;
    private SQLiteDatabase db;
    private ContentValues values;

    private EditText titleText;
    private EditText descText;
    private EditText priceText;
    private TextView txtLat;



    private Uri photoURI;
    private Uri selectedImage;
    private String imageToSave;
    private ImageView imgPreview;
    private Button btnPicture;
    private Button btnGallary;
    private String selectTask;
    private String price;

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int PICK_IMAGE_REQUEST = 2;





    protected LocationManager locationManager;
    private double latitude;
    private double longitude;
    private String place;




    TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myCoordinatorLayout = (CoordinatorLayout) findViewById(
                MyCoordinatorLayout);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        }

        titleText = (EditText) findViewById(R.id.TitleText);
        descText = (EditText) findViewById(R.id.DescriptionText);
        priceText = (EditText) findViewById(R.id.PriceText);
        txtLat = (TextView) findViewById(R.id.test);




        PostsDbHelper mDbHelper = new PostsDbHelper(this);
        db = mDbHelper.getWritableDatabase();


        imgPreview=(ImageView) findViewById(R.id.imgPreview);

        btnGallary=(Button)findViewById(R.id.pickPhoto);


        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();

            finish();
        }


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED  ) {

            btnGallary.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION }, 0);

        }


        btnGallary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                getLocation();

                selectImage();
            }
        });


    }


    void getLocation()
    {
        try {

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        }
        catch(SecurityException e)
        {
            e.printStackTrace();
        }

    }

    private void showSnackBar(View v) {
        if (v == null) {
            Snackbar.make(findViewById(MyCoordinatorLayout), R.string.new_post_snackbar,
                    Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(v, R.string.new_post_snackbar,
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    private void addPost() {
        price=priceText.getText().toString();


        if(place!=null){

            values = new ContentValues();
            values.put(Posts.PostEntry.COLUMN_TITLE, titleText.getText().toString());
            values.put(Posts.PostEntry.COLUMN_DESCRIPTION, descText.getText().toString());
            values.put(Posts.PostEntry.COLUMN_PRICE, price);
            values.put(Posts.PostEntry.COLUMN_IMAGE, imageToSave);
            values.put(Posts.PostEntry.COLUMN_LATITUDE,Double.toString(latitude));
            values.put(Posts.PostEntry.COLUMN_LONGITUDE,Double.toString(longitude));
            values.put(Posts.PostEntry.COLUMN_LOCATION,place);
        }
        else{
            values = new ContentValues();
            values.put(Posts.PostEntry.COLUMN_TITLE, titleText.getText().toString());
            values.put(Posts.PostEntry.COLUMN_DESCRIPTION, descText.getText().toString());
            values.put(Posts.PostEntry.COLUMN_PRICE, price);
            values.put(Posts.PostEntry.COLUMN_IMAGE, imageToSave);
        }




            long newRowId;
            newRowId = db.insert(
                    Posts.PostEntry.TABLE_NAME,
                    null,
                    values);



               String toSpeak="Thank you for making an entry " +titleText.getText().toString();


            startActivity(new Intent(this, BrowsePostsActivity.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        EditText descText = (EditText) findViewById(R.id.DescriptionText);
        EditText priceText = (EditText) findViewById(R.id.PriceText);
        EditText titleText = (EditText) findViewById(R.id.TitleText);
        switch (item.getItemId()) {
            case R.id.add: {

                    showSnackBar(null);
                    addPost();
                }

            case R.id.reset: {
                descText.setText("");

                priceText.setText("");

                titleText.setText("");

                imgPreview.setImageResource(0);

                imageToSave=null;

            }

        }
        return super.onOptionsItemSelected(item);
    }



    private boolean isDeviceSupportCamera() {

        if (getApplicationContext().getPackageManager().hasSystemFeature(

                PackageManager.FEATURE_CAMERA)) {

            return true;
        } else {

            return false;
        }
    }


    private void captureImage()
    {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {


            photoURI=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,new ContentValues());

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

        }

    }


    private void getGallery()
    {
        Intent pickIntent= new Intent();

        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        pickIntent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        pickIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                 };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    selectTask ="Take Photo";

                        captureImage();

                } else if (items[item].equals("Choose from Library")) {
                    selectTask ="Choose from Library";

                        getGallery();

                }
              
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_TAKE_PHOTO) {

            if (resultCode == RESULT_OK ) {


                String[] fileColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(photoURI,
                        fileColumn, null, null, null);
                String contentPath = null;
                if (cursor.moveToFirst()) {
                    contentPath = cursor.getString(cursor
                            .getColumnIndex(fileColumn[0]));

                    Bitmap bmp = BitmapFactory.decodeFile(contentPath);
                    ImageView img = (ImageView) findViewById(R.id.imgPreview);
                    img.setImageBitmap(bmp);

                    imageToSave=photoURI.toString();
                }

            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(getApplicationContext(),
                        "image cancelled", Toast.LENGTH_SHORT)
                        .show();
            } else {

                Toast.makeText(getApplicationContext(),
                        "failed", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        else if(requestCode == PICK_IMAGE_REQUEST)
        {
            if(resultCode==RESULT_OK)
            {
                //This variable gives the photo that is selected in the gallery
                selectedImage = data.getData();
                this.getContentResolver().takePersistableUriPermission(selectedImage, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                try {


                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    imgPreview.setImageBitmap(bitmap);

                    imageToSave=selectedImage.toString();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
            else if(requestCode==RESULT_CANCELED)
            {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image selection", Toast.LENGTH_SHORT)
                        .show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),
                        "Failure to select a picture!", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),
                    "Please Try again!", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                btnGallary.setEnabled(true);
            }
        }

    }

    public void onLocationChanged (Location location) {

        txtLat = (TextView) findViewById(R.id.test);

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        List<Address> addresses = null;

        String address="",city="",state="",postalCode="";

        String fullAddress="";

        Geocoder gc = new Geocoder(this, Locale.getDefault());

       try {
            addresses = gc.getFromLocation(latitude, longitude, 1);

            address = addresses.get(0).getAddressLine(0);

            city = addresses.get(0).getLocality();

            state = addresses.get(0).getAdminArea();

            postalCode = addresses.get(0).getPostalCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fullAddress=address+","+city+","+state+","+postalCode;

        txtLat.setText(fullAddress);

        place=fullAddress;
    }

    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }


    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }


    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    private void speak(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }


    private void listen(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());


        try {
            startActivityForResult(i, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(MainActivity.this, "Your device doesn't support Speech Recognition", Toast.LENGTH_SHORT).show();
        }



    }}