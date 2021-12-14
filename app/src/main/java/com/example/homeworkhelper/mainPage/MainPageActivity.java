package com.example.homeworkhelper.mainPage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
import com.example.homeworkhelper.utils.JsonUtils;
import com.example.homeworkhelper.utils.TransferUtils;
import com.example.homeworkhelper.utils.common.Config;
import com.example.homeworkhelper.utils.common.Item;
import com.example.homeworkhelper.utils.common.LogRecord;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private static Uri photoOutputUri = null; // 图片最终的输出文件的 Uri
    private static Handler resultHandler;
    private static String items;
    private static int ques_class;
    private static int ans_num;
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

    public static void postSQL() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                File file = new File(photoOutputUri.getPath());
                String filename = photoOutputUri.getPath().substring(photoOutputUri.getPath().lastIndexOf("/") + 1);
                compressBmpFileToTargetSize(file,60000);
                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addPart(Headers.of(
                                "Content-Disposition",
                                "form-data; name=\"getUpTime\""),
                                RequestBody.create(null, "2019-3-29"))
                        .addPart(Headers.of(
                                "Content-Disposition",
                                "form-data; name=\"originalData\"; filename=\"" + filename + "\""), fileBody)
                        .build();
                String url = Config.HttpUrlHead + "/mainPage/solution";
                System.out.println("----------------------------------------------------");
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("text", "failure upload!" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("text", "success upload!");
                        String json = response.body().string();
                        Log.i("success........", "成功" + json);
                    }
                });
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("1111111111111111");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = Config.HttpUrlHead + "/mainPage/items";
                String msg = "{\"items\":[{\"itemId\":\"63655535\",\"content\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/0e9abbfe5b2f159de7f6aff1393fcfda-375.png\\\" width=\\\"335\\\" height=\\\"311\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-311,320-280-338,360-320-311,411-371-284,460-420-284,534-494-257,768-728-257\\\" \\u003e\",\"answer\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/9ad935517d160f3adf6b7c2273e435e0-375.png\\\" width=\\\"335\\\" height=\\\"398\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-398,320-280-392,360-320-392,411-371-398,460-420-365,534-494-338,768-728-338\\\" \\u003e\",\"hint\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/5dcd9c037b7a8d58767e0af0ae6be27b-375.png\\\" width=\\\"335\\\" height=\\\"135\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-135,320-280-135,360-320-135,411-371-135,460-420-108,534-494-81,768-728-81\\\" \\u003e\",\"remark\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/b90a0b7984403ffa312fd81d5ca9dcfd-375.png\\\" width=\\\"335\\\" height=\\\"81\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-81,320-280-81,360-320-81,411-371-54,460-420-54,534-494-54,768-728-27\\\" \\u003e\",\"subject\":2},{\"itemId\":\"669758137\",\"content\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/53c78f44194d8564f2a94ea58906d6e0-375.png\\\" width=\\\"335\\\" height=\\\"232\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-232,320-280-286,360-320-232,411-371-232,460-420-205,534-494-178,768-728-124\\\" \\u003e\",\"answer\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/26213ba6812317b5f8b8a760cdfc8830-375.png\\\" width=\\\"335\\\" height=\\\"27\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-27,320-280-27,360-320-27,411-371-27,460-420-27,534-494-27,768-728-27\\\" \\u003e\",\"hint\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/e299c1930920e946023fb55119c2feda-375.png\\\" width=\\\"335\\\" height=\\\"135\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-135,320-280-135,360-320-135,411-371-135,460-420-108,534-494-81,768-728-81\\\" \\u003e\",\"remark\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/0ad7412106d44cb92faa60eadbcd95da-375.png\\\" width=\\\"335\\\" height=\\\"81\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-81,320-280-81,360-320-81,411-371-54,460-420-54,534-494-54,768-728-27\\\" \\u003e\",\"subject\":2},{\"itemId\":\"4220693\",\"content\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/9e3a21f3ded24126d6f6d1c9535dcf4a-375.png\\\" width=\\\"335\\\" height=\\\"269\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-269,320-280-323,360-320-296,411-371-269,460-420-269,534-494-242,768-728-215\\\" \\u003e\",\"answer\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/5299347fcd22fee176c0f7f2778e190b-375.png\\\" width=\\\"335\\\" height=\\\"153\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-153,320-280-207,360-320-180,411-371-153,460-420-153,534-494-153,768-728-153\\\" \\u003e\",\"hint\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/24b4b1993aff02dca46bfd2aae02c8f5-375.png\\\" width=\\\"335\\\" height=\\\"216\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-216,320-280-216,360-320-216,411-371-216,460-420-162,534-494-162,768-728-135\\\" \\u003e\",\"remark\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/333f6b20d2a336571efd4fe1462f3e57-375.png\\\" width=\\\"335\\\" height=\\\"124\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-124,320-280-180,360-320-124,411-371-96,460-420-96,534-494-96,768-728-64\\\" \\u003e\",\"subject\":2},{\"itemId\":\"37265214\",\"content\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/140690c9bf227a3b9a0d0daaef08d3ef-375.png\\\" width=\\\"335\\\" height=\\\"396\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-396,320-280-423,360-320-396,411-371-396,460-420-369,534-494-369,768-728-342\\\" \\u003e\",\"answer\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/761c05d59177cbf17280da8756d35349-375.png\\\" width=\\\"335\\\" height=\\\"297\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-297,320-280-378,360-320-324,411-371-297,460-420-297,534-494-297,768-728-243\\\" \\u003e\",\"hint\":\"\",\"remark\":\"\",\"subject\":2},{\"itemId\":\"35241890\",\"content\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/31ed07207a268fce4b6a6dfeb0addb28-375.png\\\" width=\\\"335\\\" height=\\\"343\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-343,320-280-371,360-320-343,411-371-315,460-420-315,534-494-315,768-728-287\\\" \\u003e\",\"answer\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/982d819072e60c4375742e185480cc10-375.png\\\" width=\\\"335\\\" height=\\\"289\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-289,320-280-345,360-320-289,411-371-289,460-420-289,534-494-261,768-728-233\\\" \\u003e\",\"hint\":\"\",\"remark\":\"\",\"subject\":2}],\"BaseResp\":{\"error\":{\"code\":0,\"type\":0,\"title\":\"\",\"e_message\":\"\",\"nlp\":\"\",\"schema\":\"\",\"confirm_button\":\"\",\"cancel_button\":\"\",\"error_comment\":\"\"},\"logId\":\"202112041434510102020871482607AD35\",\"StatusMessage\":\"\",\"StatusCode\":0,\"Extra\":{\"logId\":\"202112041434510102020871482607AD35\"}}}";
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()    //构建body
                        .add("items", items)
                        .add("u_id", "1")
                        .add("dev_id", "1")
                        .add("ques_class", String.valueOf(ques_class))
                        .add("ans_num", String.valueOf(ans_num))
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                        Log.d("MainActivityPost", "连接失败" + e.getLocalizedMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println(response.protocol() + "::::" + response.message());
                        System.out.println(response.body().toString());
                    }
                });
            }
        });
        t2.start();
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public static void compressBmpFileToTargetSize(File file, long targetSize) {
        //Log.d(TAG, String.format("compressBmpFileToTargetSize start file.length():%d", file.length()));
        if (file.length() > targetSize) {
            // 每次宽高各缩小一半
            int ratio = 2;
            // 获取图片原始宽高
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            int targetWidth = options.outWidth / ratio;
            int targetHeight = options.outHeight / ratio;

            // 压缩图片到对应尺寸
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int quality = 100;
            Bitmap result = generateScaledBmp(bitmap, targetWidth, targetHeight, baos, quality);

            // 计数保护，防止次数太多太耗时。
            int count = 0;
            while (baos.size() > targetSize && count <= 10) {
                targetWidth /= ratio;
                targetHeight /= ratio;
                count++;

                // 重置，不然会累加
                baos.reset();
                result = generateScaledBmp(result, targetWidth, targetHeight, baos, quality);
            }
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Bitmap generateScaledBmp(Bitmap srcBmp, int targetWidth, int targetHeight, ByteArrayOutputStream baos, int quality) {
        Bitmap result = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, result.getWidth(), result.getHeight());
        canvas.drawBitmap(srcBmp, null, rect, null);
        if (!srcBmp.isRecycled()) {
            srcBmp.recycle();
        }
        result.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        return result;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void confirmPic() {
        Bundle bundle = new Bundle();
        getImgBase64(ivShowPicture);
        System.out.println(base64);
        Handler handler = new Handler(){
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                String result = (String) msg.obj;
                items = result;
                System.out.println(items);
                LogRecord logRecord = JsonUtils.parseJson(items);
                ArrayList<Item> itemArrayList = logRecord.getItemArrayList();
                ans_num = itemArrayList.size();
                if (ans_num != 0) ques_class = itemArrayList.get(0).getSubject();
                else ques_class = 0;
                postSQL();
                System.out.println(result);
                bundle.putString("APIresult", result);    //递交给历史记录页面api的返回结果
                Message result_msg = new Message();
                result_msg.obj = items;
                resultHandler.sendMessage(result_msg);
            }
        };
        APIUtils.setHandler(handler);
        APIUtils.call_api(base64);
        bundle.putString("Bitmap", base64);
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
                Bitmap bitmap = BitmapFactory.decodeFile(photoOutputUri.getPath());
                ivShowPicture.setImageBitmap(bitmap);
                StartView.setVisibility(View.GONE);
                OperateView.setVisibility(View.VISIBLE);
            }
        }
    }
}
