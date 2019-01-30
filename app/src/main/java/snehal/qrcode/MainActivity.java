package snehal.qrcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {
    //private static final String TAG = "Certificate_Scanner"
    TextView barcodeResult;
    public String bhai_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barcodeResult = findViewById(R.id.bar_Content);
        isCameraPermissionGranted();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==0){
            if(requestCode== CommonStatusCodes.SUCCESS){
                if(data!=null){
                    Barcode barcode=data.getParcelableExtra("barcode");
                    bhai_value = barcode.displayValue;//object of Barcode class
                    barcodeResult.setText("Barcode value: "+bhai_value);
                    Toast.makeText(this, bhai_value, Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean isCameraPermissionGranted() {
        if(Build.VERSION.SDK_INT>=23){
            if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission is Granted", Toast.LENGTH_SHORT).show();
                return true;
            }else{
                Toast.makeText(this, "Permission is Revoked", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},1);
                return false;
            }

        }else{
            Toast.makeText(this, "Permission is Granted", Toast.LENGTH_SHORT).show();
            return true;
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission "+permissions[0]+"was "+grantResults[0], Toast.LENGTH_SHORT).show();
        }
    }

    public void scanBarcode(View view) {
        Intent in = new Intent(this,ScanBarcodeActivity.class);
        startActivityForResult(in, 0);

    }
}
