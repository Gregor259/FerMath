package com.example.linalsolve;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View p = (View) view.getParentForAccessibility();
                EditText ur = (EditText) p.findViewById(R.id.ur);
                String s = ur.getText().toString();
                Solver solver = new Solver(s);
                solver.Parse();
                Uravn res = solver.Solve();
                //NavHostFragment.findNavController(FirstFragment.this)
                //        .navigate(R.id.action_FirstFragment_to_SecondFragment);
                TextView out = (TextView) p.findViewById(R.id.resul);
                if (!res.IsValid) out.setText("Решений нет!");
                else {
                    int i = 0;
                    String t = "";
                    while (i < res.roots) {
                        double x = res.x[i];
                        t += Double.toString(x) + "\n";
                        i += 1;
                    }
                    out.setText(t);
                }
            }
        });
    }
}