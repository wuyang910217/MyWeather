package com.app.wuyang.myweather.utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by wuyang on 16-1-22.
 */
public class SetImageUtility {
    private Context mContext;

    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO =2;
    public static final int CHOOSE_PHOTO =3;

    public SetImageUtility(Context mContext) {
        this.mContext = mContext;
    }

    public Bitmap setImage(){
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(
                    mContext.getContentResolver().openInputStream(imageUri()));
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Uri imageUri(){
        String outputImageDir = Environment.
                getExternalStorageDirectory()+ File.separator+"Android"+File.separator
                +"data"+File.separator+mContext.getPackageName();
        String fileName ="temp.jpg";
        File outputImage =new File(outputImageDir);
        if (!outputImage.exists()) {
            outputImage.mkdirs();
        }
        File imageUri=new File(outputImageDir,fileName);
        return Uri.fromFile(imageUri);
    }

    public Intent takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri());
        return intent;
    }

    public Intent chooseFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        return intent;
    }

    public Intent scalePhoto(){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri(), "image/*");
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri());
        return intent;
    }

    public Intent cropPhoto(Intent data){
        //从系统中选取的图片的Uri地址
        Uri getImageUri = data.getData();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageUri,"image/*");
        intent.putExtra("scale", true);
        //输出到imageUri中，如果不改 说明覆盖了选取的图片的地址 经过剪切后 变小了
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri());
        return intent;
    }
}
