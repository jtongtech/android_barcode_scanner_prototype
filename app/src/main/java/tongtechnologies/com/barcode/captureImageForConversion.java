package tongtechnologies.com.barcode;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class captureImageForConversion extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView userImageView;


    public static String URL = "Paste your URL here";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image_for_conversion);

        Button takePicBtn = (Button) findViewById(R.id.takePicBtn);
        userImageView = (ImageView) findViewById(R.id.userImageView);

        // Disable Button if user has no camera
        if (!hasCamera())
            takePicBtn.setEnabled(false);
    }

    //Check if the user has a camera
    private boolean hasCamera(){
        return  getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //Launching the camera
    public void launchCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Take a picture and pass results along to onActivityResult
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

    }

    public static String encodeTobase64(Bitmap bitmap) {
        Bitmap immagex=bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }

    //If you want to return image taken
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Get the photo
//            Log.e("data is", data.getExtras().toString());
//            Bundle extras = data.getExtras();
//            Bitmap photo = (Bitmap) extras.get("data");
            Bitmap userImage = (Bitmap)data.getExtras().get("data");
//            Log.e("bitmap is", bitmap.toString());
            userImageView.setImageBitmap(userImage);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            userImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            byte[] byteArray = stream.toByteArray();
//            String decoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
//            Log.e("encoded is", decoded);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            userImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
            String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            Log.e("imageEncoded is", imageEncoded);

//            userImageView.setImageResource(R.drawable.hp_bot64);
//            String photoInfo = photo.getNinePatchChunk().toString();
//            Log.e("photo is", photoInfo);

//            Log.e("photodata is", photo);
//            userImageView.setImageBitmap(photo);
//            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.id.userImageView);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] byteArrayImage = baos.toByteArray();
//            String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
//            Log.e("encodedImage is", encodedImage);
        }

    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(this.requestCode == requestCode && resultCode == RESULT_OK){
//            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
//            imageHolder.setImageBitmap(bitmap);
//        }
//    }
}