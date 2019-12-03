package com.example.closet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.opencv.core.*;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import static org.opencv.core.CvType.CV_64FC1;
import static org.opencv.core.CvType.CV_8U;
import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.core.CvType.CV_8UC3;
import static org.opencv.imgproc.Imgproc.COLOR_RGBA2BGRA;
import static org.opencv.imgproc.Imgproc.CV_RGBA2mRGBA;
import static org.opencv.imgproc.Imgproc.GC_BGD;


public class openCV_test extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{
    static {
        System.loadLibrary("native-lib");
        System.loadLibrary("opencv_java3");
    }

    //public native int seg();

    private static final String TAG = "AndroidOpenCv";
    private static final int REQ_CODE_SELECT_IMAGE = 100;
    private Bitmap mInputImage;
    private Bitmap mOriginalImage;
    private ImageView mImageView;
    private ImageView mEdgeImageView;
    private boolean mIsOpenCVReady = false;

    //public native int grabcut(long inputImage, long outputImage);
    //public native void detectEdgeJNI(long inputImage, long outputImage, int th1, int th2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_cv_test);
        mInputImage = BitmapFactory.decodeResource(getResources(),R.drawable.t_shirt);
        mImageView = findViewById(R.id.origin_iv);
        mEdgeImageView = findViewById(R.id.edge_iv);
        //detectEdgeUsingJNI();
        //detectEdge();
        grabCut();
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
        //detectEdgeJNI(src.getNativeObjAddr(),edge.getNativeObjAddr(),50,150);
        Utils.matToBitmap(edge,mInputImage);
        mEdgeImageView.setImageBitmap(mInputImage);
    }

    public void detectEdge() {
        //위와 같은 기능
        Mat src = new Mat();
        Utils.bitmapToMat(mInputImage, src);
        Mat edge = new Mat();
        Imgproc.Canny(src, edge, 50, 150);
        Utils.matToBitmap(edge, mInputImage);
        src.release();
        edge.release();
        mEdgeImageView.setImageBitmap(mInputImage);
    }

    private void getBinMask(Mat comMask, Mat binMask){
        if( comMask.empty() || comMask.type()!= CV_8UC1 )
            Log.d("TYPE ERROR", "comMask is empty or has incorrect type (not CV_8UC1)" );
        if( binMask.empty() || binMask.rows() !=comMask.rows() || binMask.cols() != comMask.cols() )
            binMask.create( comMask.size(), CV_8UC3 );
        Core.bitwise_and(comMask, binMask, binMask);
    }

    public void grabCut(){
        Mat src = new Mat();
        Mat result = new Mat();
        Mat mask = new Mat();
        Mat bgdModel = new Mat();
        Mat fgdModel = new Mat();

        Rect rect = new Rect(20, 20, src.cols() - 20, src.rows() - 20);
        int iterCount = 5;

        src.create(src.size(), CV_8UC3);
        mask.create(src.size(), CV_8UC3);
        result.create(src.size(), CV_8UC3);
        fgdModel.create(src.size(), CV_64FC1);
        bgdModel.create(src.size(), CV_64FC1);

        Utils.bitmapToMat(mInputImage, src);
        Imgproc.cvtColor(src, src, COLOR_RGBA2BGRA);

        mask.setTo(Scalar.all(GC_BGD));
        Mat binMask = new Mat(); getBinMask(mask, binMask); //둘이 크기 맞춰줘야 하는듯?
        src.copyTo(result, binMask);

        //Mat source = new Mat(1, 1, CV_8U, new Scalar(0));

        Imgproc.grabCut(src, mask, rect, bgdModel, fgdModel, iterCount, Imgproc.GC_INIT_WITH_MASK);

        Utils.matToBitmap(mask, mInputImage); //??
        src.release();
        mask.release();
        mEdgeImageView.setImageBitmap(mInputImage);
    }

    public void onButtonClicked(View view) {
        //Intent intent = new Intent(Intent.ACTION_PICK);
        //intent.setType(android.provider.MediaSto`re.Images.Media.CONTENT_TYPE);
        //intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
        grabCut();
        Log.d("Log_d Button","ButtonClicked");
    }
}
