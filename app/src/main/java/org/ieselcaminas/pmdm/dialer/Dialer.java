package org.ieselcaminas.pmdm.dialer;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Dialer extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);

        if (ActivityCompat.checkSelfPermission(Dialer.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(Dialer.this, "You don't have permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(Dialer.this, new String[] {Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);

        } else {
            phone();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    phone();
                } else {

                    Dialog d = new AlertDialog.Builder(Dialer.this).setTitle("Error").
                            setMessage("I need permission to make a call").create();
                    d.show();
                }
                return;
            }

        }
    }

    private void phone() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intentFromSomwhere = getIntent();
            String phoneStr = intentFromSomwhere.getExtras().getString("phone");

            Intent i = new Intent(Intent.ACTION_CALL,
                    Uri.parse("tel:" + phoneStr));
            startActivity(i);
        }
    }
}
