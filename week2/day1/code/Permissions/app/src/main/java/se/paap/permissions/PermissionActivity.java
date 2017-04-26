package se.paap.permissions;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PermissionActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_READ_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.read_contacts_rationale_title))
                        .setMessage(getString(R.string.read_contacts_rationale_message))
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(PermissionActivity.this, new String[] {Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
                            }
                        })
                        .create()
                        .show();

            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case REQUEST_CODE_READ_CONTACTS:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // YAY, lets read contacts!
                } else if(grantResults.length > 0) {
                    // Disable functionality
                } else {
                    // User dismissed the dialog
                }
        }
    }
}
