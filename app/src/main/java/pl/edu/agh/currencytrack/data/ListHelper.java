package pl.edu.agh.currencytrack.data;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;

public class ListHelper {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String flatListToStringWithDelimiter(List<?> list, String delimiter) {
        String newList = "";

        for (int i = 0; i < list.size(); i++) {
            newList = newList.concat(list.get(i).toString());

            if (i + 1 < list.size()) {
                newList = newList.concat(delimiter);
            }
        }

        return newList;
    }
}
