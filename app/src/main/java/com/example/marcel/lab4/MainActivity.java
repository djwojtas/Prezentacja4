
package com.example.marcel.lab4;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        Button button = findViewById(R.id.btn_run);
        //java 8 - lambda and effective final
        final AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncTaskRunner.execute(5000);
            }
        });
    }

    private static class AsyncTaskRunner extends AsyncTask<Integer, Integer, String> {

        private final WeakReference<MainActivity> mainActivity;

        private AsyncTaskRunner(MainActivity mainActivity) {
            this.mainActivity = new WeakReference<>(mainActivity);
        }

        @Override
        protected String doInBackground(Integer... params) {
            publishProgress(0);
            Integer time = params[0];
            try {
                Thread.sleep(time/5);
                return "Slept for " + time + " seconds";
            } catch (Exception e) {
                e.printStackTrace();
                return e.getLocalizedMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            mainActivity.get().progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPreExecute() {
            mainActivity.get().progressBar.setVisibility(View.VISIBLE);
        }
    }
}
