package com.example.closet;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

public class openCV_test extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{
    static {
        System.loadLibrary("native-lib");
        System.loadLibrary("opencv_java3");
    }
    private static final String TAG = "AndroidOpenCv";
    private static final int REQ_CODE_SELECT_IMAGE = 100;
    private Bitmap mInputImage;
    private Bitmap mOriginalImage;
    private ImageView mImageView;
    private ImageView mEdgeImageView;
    private boolean mIsOpenCVReady = false;

    public native void detectEdgeJNI(long inputImage, long outputImage, int th1, int th2);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_cv_test);
        mInputImage = BitmapFactory.decodeResource(getResources(),R.drawable.t_shirt);
        mImageView = findViewById(R.id.origin_iv);
        mEdgeImageView = findViewById(R.id.edge_iv);

        //context.getResources() 로 해야하나?
        detectEdgeUsingJNI();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return null;
    }

    public void detectEdgeUsingJNI(){
        //if(!mlsOpenCVReady)

        Mat src = new Mat();
        Utils.bitmapToMat(mInputImage,src);
        mImageView.setImageBitmap(mOriginalImage);
        Mat edge = new Mat();
        detectEdgeJNI(src.getNativeObjAddr(),edge.getNativeObjAddr(),50,150);
        Utils.matToBitmap(edge,mInputImage);
        mEdgeImageView.setImageBitmap(mInputImage);
    }
}
