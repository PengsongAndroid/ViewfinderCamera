package com.peng.camera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.peng.camera.view.CameraHelper;
import com.peng.camera.view.MaskSurfaceView;
import com.peng.camera.view.OnCaptureCallback;

/**
 * Created by PS on 2016/8/25.
 */
public class CameraActivity extends Activity implements OnCaptureCallback, View.OnClickListener {

	private CameraActivity instance;

	private MaskSurfaceView surfaceview;

	private ImageView bnToggleCamera;
	private ImageView bnCapture;
	private View focuseView;

	private int bncaptureWidth = 0;

	private int bnToggleWidth = 0;

	private static final int SHOWPIC_CODE = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.camera_layout);

		bnToggleCamera = (ImageView) findViewById(R.id.bnToggleCamera);
		bnCapture = (ImageView) findViewById(R.id.bnCapture);
		focuseView = findViewById(R.id.viewFocuse);
		surfaceview = (MaskSurfaceView) findViewById(R.id.surface_view);

		setListener();
		initView();
		CameraHelper.getInstance().setFlashlight(CameraHelper.Flashlight.OFF);
	}

	private void initView() {
		Display display = getWindowManager().getDefaultDisplay();
		int w = display.getWidth();
		int h = display.getHeight();
		bnToggleCamera.measure(w, h);
		bnCapture.measure(w, h);
		bncaptureWidth = bnCapture.getMeasuredWidth();
		bnToggleWidth = bnToggleCamera.getMeasuredWidth();
		// 设置矩形区域大小
		surfaceview.setMaskSize(2 * (h - 5 * bncaptureWidth / 2) / 3, h - 5 * bncaptureWidth / 2, w, h);
	}

	private void setListener() {
		bnToggleCamera.setOnClickListener(this);
		bnCapture.setOnClickListener(this);
		focuseView.setOnClickListener(this);
	}

	@Override
	public void onCapture(boolean success, String filePath) {
		if (success) {
            Intent it = new Intent();
            it.putExtra("filePath", filePath);
			setResult(RESULT_OK, it);
			finish();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SHOWPIC_CODE && resultCode == RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bnToggleCamera:
			switchCamera();
			break;
		case R.id.bnCapture:
			bnCaptureClicked();
			break;
		case R.id.viewFocuse:
			focuseClick();
			break;
		}
	}

	private void bnCaptureClicked() {
		bnCapture.setEnabled(false);
		CameraHelper.getInstance().tackPicture(this);
	}

	private void switchCamera() {
		CameraHelper.getInstance().switchCamera();
	}

	private void focuseClick() {
		CameraHelper.getInstance().needFocuse(focuseView);
	}

	@Override
	protected void onResume() {
		super.onResume();
		bnCapture.setEnabled(true);
	}
}
