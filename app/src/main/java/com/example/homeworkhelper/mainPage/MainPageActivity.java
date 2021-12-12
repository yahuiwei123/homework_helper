package com.example.homeworkhelper.mainPage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.homeworkhelper.R;
import com.example.homeworkhelper.history.HistoryDisplayActivity;
import com.example.homeworkhelper.history.bean.RecordData;
import com.example.homeworkhelper.result.ResultDisplayActivity;
import com.example.homeworkhelper.utils.APIUtils;
import com.example.homeworkhelper.utils.TransferUtils;

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
    private Button btnOpenHistory;          //跳转历史记录界面按钮
    private ImageView ivShowPicture;        //图片显示imageview
    //以下为区分Activity回传的区分静态变量
    private static final int REQUEST_CAMERA_1 = 1;
    private static final int REQUEST_ALBUM_2 = 2;
    public static final int REQUEST_Crop_3 = 3;
    private static int  REVOLVE_DEGREE = 90;    //旋转最小角度变量
    private static Uri Turi;        //获取图片的URI
    private final int REQUEST_GPS = 1;          //动态权限获取的识别码
    private static String base64 = null;
    private Uri photoUri = null;
    private Uri photoOutputUri = null; // 图片最终的输出文件的 Uri
    private static Handler resultHandler;

    public static void setHandler(Handler handler) {
        resultHandler = handler;
    }

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
        btnOpenHistory = (Button) findViewById(R.id.btnOpenHistory);
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
        btnOpenHistory.setOnClickListener(this);
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
                cropPhoto(photoUri);
                break;
            case R.id.btnO_back:
                // 返回拍照页
                backPic();
                break;
            case R.id.btnO_confirm:
                confirmPic();
                break;
            case R.id.btnOpenHistory:
                skiptoHistory();
                break;
            default:
                break;
        }
    }

    private void skiptoHistory() {
        Intent intent = new Intent(MainPageActivity.this, HistoryDisplayActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void confirmPic() {
        Bundle bundle = new Bundle();
        getImgBase64(ivShowPicture);
        System.out.println(base64);
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                String result = (String) msg.obj;
                System.out.println(result);
                bundle.putString("APIresult", result);    //递交给历史记录页面api的返回结果
                resultHandler.sendMessage(new Message());
            }
        };
        APIUtils.setHandler(handler);
        APIUtils.call_api(base64);
        bundle.putString("Bitmap",base64);
        Intent intenttrans = new Intent();
        intenttrans.setClass(MainPageActivity.this, ResultDisplayActivity.class);
                                 //将用户自己拍到的图片递交给历史记录页面
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
        File file = new File(getExternalCacheDir(), "image.jpg");
        try {
            if(file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 因 Android 7.0 开始，不能使用 file:// 类型的 Uri 访问跨应用文件，否则报异常，
         * 因此我们这里需要使用内容提供器，FileProvider 是 ContentProvider 的一个子类，
         * 我们可以轻松的使用 FileProvider 来在不同程序之间分享数据(相对于 ContentProvider 来说)
         */
        if(Build.VERSION.SDK_INT >= 24) {
            photoUri = FileProvider.getUriForFile(this, "com.example.homeworkhelper.fileprovider", file);
        } else {
            photoUri = Uri.fromFile(file); // Android 7.0 以前使用原来的方法来获取文件的 Uri
        }
        // 打开系统相机的 Action，等同于："android.media.action.IMAGE_CAPTURE"
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 设置拍照所得照片的输出目录
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(takePhotoIntent,REQUEST_CAMERA_1);
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
//        startActivityForResult(intent, REQUEST_CAMERA_1);
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

    //保存图片
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
            System.out.println(uri);
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

    private void cropPhoto(Uri inputUri) {
        // 调用系统裁剪图片的 Action
        Intent cropPhotoIntent = new Intent("com.android.camera.action.CROP");
        // 设置数据Uri 和类型
        cropPhotoIntent.setDataAndType(inputUri, "image/*");
        // 授权应用读取 Uri，这一步要有，不然裁剪程序会崩溃
        cropPhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置图片的最终输出目录
        cropPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                photoOutputUri = Uri.parse("file:////sdcard/image_output.jpg"));
        startActivityForResult(cropPhotoIntent, REQUEST_Crop_3);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回数据
            if (requestCode == REQUEST_CAMERA_1) { // 判断请求码是否为REQUEST_CAMERA,如果是代表是这个页面传过去的，需要进行获取
                cropPhoto(photoUri);
            }
            else if(requestCode == REQUEST_ALBUM_2){
                photoUri = data.getData();           //获得路径
                cropPhoto(photoUri);                   //直接进行裁剪处理
            }
            else if(requestCode == REQUEST_Crop_3){
                File file = new File(photoOutputUri.getPath());
                Bitmap bitmap = BitmapFactory.decodeFile(photoOutputUri.getPath());
                file.delete();
                ivShowPicture.setImageBitmap(bitmap);
                StartView.setVisibility(View.GONE);
                OperateView.setVisibility(View.VISIBLE);
            }
        }
    }
}
