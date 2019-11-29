package pl.edu.agh.currencytrack.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class ImageHelper {

    public static Bitmap ImageViaAssets(String fileName, Context contex) {
        InputStream is = null;
        
        try {
            if (!fileName.contains("png")) {
                fileName = fileName + ".png";
            }

            is = contex.getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return BitmapFactory.decodeStream(is);
    }
}
