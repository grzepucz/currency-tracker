package pl.edu.agh.currencytrack.ui.convert;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import pl.edu.agh.currencytrack.R;

public class ConvertFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.setHasOptionsMenu(false);

        View rootView = inflater.inflate(R.layout.fragment_convert, container, false);
        Button runConvert = (Button) rootView.findViewById(R.id.btnRunConvertActivity);

        runConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.CONVERT");
                startActivity(intent);
            }
        });

        return rootView;
    }
}