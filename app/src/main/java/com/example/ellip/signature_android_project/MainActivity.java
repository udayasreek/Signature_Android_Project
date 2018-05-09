package com.example.ellip.signature_android_project;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kyanogen.signatureview.SignatureView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Bitmap bitmap = null;
    private SignatureView signatureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signatureView = findViewById(R.id.signature_view);
        int colorPrimary = ContextCompat.getColor(this, R.color.colorAccent);
        signatureView.setPenColor(colorPrimary);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_signature, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_clear:
                signatureView.clearCanvas();
                Toast.makeText(getApplicationContext(), "Signature is cleared", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_download:
                bitmap = signatureView.getSignatureBitmap();
                SaveImage(bitmap);

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void SaveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt();
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);

        if (file.exists()) file.delete();
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        if (!file.exists())
            try {
                file.createNewFile();
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
                Toast.makeText(this, "signature is downloaded" +
                        "\n  you can view signature in filemanager -> local -> internalstorage -> saved_images", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Permission denied" +
                        "\n Please enable permission for storage in settings  > apps >Signature_Android_Project > Storage(enable)", Toast.LENGTH_LONG).show();
            }
    }

}