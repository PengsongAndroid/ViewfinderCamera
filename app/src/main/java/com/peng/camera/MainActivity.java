package com.peng.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static int OPEN_CAMRERA_CODE = 0x100;

    private ImageView imageView;

    private Button button;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);
        button = (Button) findViewById(R.id.button);
    }

    public void openCamera(View view){
        startActivityForResult(new Intent(this, CameraActivity.class), OPEN_CAMRERA_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OPEN_CAMRERA_CODE && resultCode == RESULT_OK){
            BitmapFactory.Options options = new BitmapFactory.Options();
            //不对图进行压缩
            options.inSampleSize = 1;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeFile(data.getStringExtra("filePath"), options);
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
    }
}
