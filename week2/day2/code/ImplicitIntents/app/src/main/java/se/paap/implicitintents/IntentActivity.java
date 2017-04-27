package se.paap.implicitintents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

// link to implicit intents reading
// https://developer.android.com/guide/components/intents-common.html

public class IntentActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final String TAG = IntentActivity.class.getSimpleName();

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        Button shareBtn = (Button) findViewById(R.id.btn_share);
        Button mapBtn = (Button) findViewById(R.id.btn_maps);
        Button cameraBtn = (Button) findViewById(R.id.btn_camera);

        // TODO: Share text
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "My message to send");
                intent.setType("text/plain");

                if(intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(intent, "Choose app to share my text!"));
                }
            }
        });

        // TODO: Open MAP
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("geo:0,0")
                        .buildUpon()
                        .appendQueryParameter("q", "Farmarstigen 11, Tyresö")
                        .build();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);

                if(intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });


        // Take PHOTO
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                }
            }
        });

        imageView = (ImageView) findViewById(R.id.image_view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA) {
            if(resultCode == RESULT_OK) {
                Bitmap image = (Bitmap) data.getExtras().get("data");

                imageView.setImageBitmap(image);
                imageView.setVisibility(View.VISIBLE);

                // Save image
            }
        }
    }
}
