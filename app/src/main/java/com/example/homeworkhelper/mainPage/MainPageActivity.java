package com.example.homeworkhelper.mainPage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.example.homeworkhelper.R;
import com.example.homeworkhelper.history.HistoryDisplayActivity;
import com.example.homeworkhelper.result.ResultDisplayActivity;
import com.example.homeworkhelper.utils.APIUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainPageActivity extends Activity implements View.OnClickListener {

    private RelativeLayout StartView;       //拍照搜题页
    private RelativeLayout OperateView;     //图像处理页
    private Button btnOpenCamera;           //调用相机按钮
    private Button btnOpenAlbum;            //调取相册按钮
    private Button btnO_revolve;            //旋转按钮
    private Button btnO_back;               //返回按钮
    private Button btnO_crop;               //裁剪按钮
    private Button btnO_confirm;            //确认按钮
    private ImageView ivShowPicture;        //图片显示imageview
    //以下为区分Activity回传的区分静态变量
    private static final int REQUEST_CAMERA_1 = 1;
    private static final int REQUEST_ALBUM_2 = 2;
    public static final int REQUEST_Crop_3 = 3;
    private static int  REVOLVE_DEGREE = 90;    //旋转最小角度变量
    private static Uri Turi;        //获取图片的URI
    private final int REQUEST_GPS = 1;          //动态权限获取的识别码
    private static String base64 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
// 初始化控件
        init();
// 控件绑定点击事件
        bindClick();
        requestPermissions();
    }
    private void requestPermissions(){
        //REQUEST_GPS为自定义int型静态常量；private final int REQUEST_GPS = 1;
        //申请动态权限，这里的权限申请绑定在了全局控件
        ActivityCompat.requestPermissions(MainPageActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}
                , REQUEST_GPS);
    }

    // 初始化控件和变量
    private void init() {
        //xml文件展示页面层的每一个控件都需要初始化
        ivShowPicture = (ImageView) findViewById(R.id.ivShowPicture);
        btnOpenCamera = (Button) findViewById(R.id.btnOpenCamera);
        btnOpenAlbum = (Button) findViewById(R.id.btnOpenAlbum);
        btnO_revolve = (Button) findViewById(R.id.btnO_revolve);
        btnO_crop = (Button) findViewById(R.id.btnO_crop);
        btnO_back = (Button) findViewById(R.id.btnO_back);
        btnO_confirm = (Button) findViewById(R.id.btnO_confirm);
        StartView = (RelativeLayout) findViewById(R.id.StartView);
        OperateView = (RelativeLayout) findViewById(R.id.OperateView);
    }
    // 控件绑定点击事件
    private void bindClick() {
        btnOpenCamera.setOnClickListener(this);
        btnOpenAlbum.setOnClickListener(this);
        btnO_revolve.setOnClickListener(this);
        btnO_crop.setOnClickListener(this);
        btnO_back.setOnClickListener(this);
        btnO_confirm.setOnClickListener(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOpenCamera:
                // 拍照并显示图片
                openCamera_1();
                break;
            case R.id.btnOpenAlbum:
                // 打开相册并选取图片
                openAlbum_2();
                break;
            case R.id.btnO_revolve:
                // 对图像进行旋转处理
                revolvePic();
                break;
            case R.id.btnO_crop:
                // 对图像进行裁剪处理
                cropPic(Turi);
                break;
            case R.id.btnO_back:
                // 返回拍照页
                backPic();
                break;
            case R.id.btnO_confirm:
                confirmPic();
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void confirmPic() {
        getImgBase64(ivShowPicture);
        Intent intenttrans = new Intent();
        intenttrans.setClass(MainPageActivity.this, ResultDisplayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("APIresult",APIUtils.call_api(base64));    //递交给历史记录页面api的返回结果
//        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxx");
//        System.out.println(APIUtils.call_api(base64));
        bundle.putString("Bitmap",base64);                          //将用户自己拍到的图片递交给历史记录页面
        intenttrans.putExtras(bundle);
        startActivity(intenttrans);
    }

    //返回拍照页面
    private void backPic() {
        ivShowPicture.setImageBitmap(null);
        StartView.setVisibility(View.VISIBLE);
        OperateView.setVisibility(View.GONE);
    }

    // 拍照并显示图片
    private void openCamera_1() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        startActivityForResult(intent, REQUEST_CAMERA_1);
    }

    //调用系统相册显示图片
    private void openAlbum_2(){
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_ALBUM_2);
    }

    //旋转照片方法
    private void revolvePic(){
        ivShowPicture.setPivotX(ivShowPicture.getWidth()/2);
        ivShowPicture.setPivotY(ivShowPicture.getHeight()/2);
        ivShowPicture.setRotation(REVOLVE_DEGREE);
        REVOLVE_DEGREE+=90;
    }

    //裁剪照片方法
    private void cropPic(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        //注意：设备XY设定为1:1的时候展示框为圆形
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        // 返回裁剪后的数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_Crop_3);
    }

    public boolean saveImageToGallery(Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "title";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            //压缩图片需要注意（这里采用的是质量压缩：指代内存不变，压缩转化后的bytes.length减少用于传输，但是PNG并不会影响
            //使用JPEG格式压缩则质量是60，对一张透明图片（PNG）仅仅会失去透明度，对一张非透明图片不会有影响
            //使用PNG格式压缩质量是50，对PNG,JPEG图片都没有影响，但是并不会减少bytes.length，所以这里选择JPEG
            //注意，当质量为100的时候表示不压缩
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));      //系统刷新相册
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getImgBase64(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bb = bos.toByteArray();
        String image = Base64.encodeToString(bb, Base64.NO_WRAP);
        base64 = image;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回数据
            if (requestCode == REQUEST_CAMERA_1) { // 判断请求码是否为REQUEST_CAMERA,如果是代表是这个页面传过去的，需要进行获取
                Bundle bundle = data.getExtras(); // 从data中取出传递回来缩略图的信息，图片质量差，适合传递小图片
                Bitmap bitmap = (Bitmap) bundle.get("data"); // 将data中的信息流解析为Bitmap类型
                System.out.println(saveImageToGallery(bitmap));
                openAlbum_2();
            }
            else if(requestCode == REQUEST_ALBUM_2){
                Turi = data.getData();           //获得路径
                cropPic(Turi);                   //直接进行裁剪处理
            }
            else if(requestCode == REQUEST_Crop_3){
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.getParcelable("data");
                ivShowPicture.setImageBitmap(bitmap);
//                getImgBase64(ivShowPicture);
//                System.out.println(APIUtils.call_api(base64));
                StartView.setVisibility(View.GONE);
                OperateView.setVisibility(View.VISIBLE);
            }
        }
    }
}
