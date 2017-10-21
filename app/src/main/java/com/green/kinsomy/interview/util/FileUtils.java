package com.green.kinsomy.interview.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kinsomy on 2017/10/21.
 */

public class FileUtils {

    public static boolean findFile(Context context,String fileName){
        File dir = context.getApplicationContext().getExternalFilesDir("Images");
        File[] images = dir.listFiles(new MyFileFilter(fileName));
        return images.length > 0;

    }

    public static void saveImage(Context context, String fileName,Bitmap bmp) {
        // 首先保存图片
        File file = context.getApplicationContext().getExternalFilesDir("Images").getAbsoluteFile();
        File image = new File(file, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(image);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


        // 保存图片到手机指定目录
    public static void savaBitmap(Context context,String imgName, byte[] bytes) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = null;
            FileOutputStream fos = null;
            try {
                filePath = context.getApplicationContext().getExternalFilesDir("Images").getAbsolutePath();
                File imgDir = new File(filePath);
                if (!imgDir.exists()) {
                    imgDir.mkdirs();
                }
                imgName = filePath + "/" + imgName;
                fos = new FileOutputStream(imgName);
                fos.write(bytes);
//                Toast.makeText(context, "图片已保存到" + filePath, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(context, "请检查SD卡是否可用", Toast.LENGTH_SHORT).show();
        }
    }
}
