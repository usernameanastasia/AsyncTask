package asynctask.lab6.onpu.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
// ЭТО КОММЕНТАРИЙ ДЛЯ ЛАБЫ


// А ЭТО КОММЕНТАРИЙ ДЛЯ КОНФЛИКТА
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_download).setOnClickListener(v -> {
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute("http://kotsobaka.com/wp-content/uploads/2018/06/kot-pokazyvaet-yazyk.jpg");
        });
    }

    private class DownloadTask extends AsyncTask<String, Bitmap, Boolean> {

        @Override
        protected Boolean doInBackground(String... urls) {

            for (String src: urls) {
                try {
                    Bitmap myBitmap = null;
                    for (int i = 0; i < 1; i++) {
                        URL url = new URL(src);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        myBitmap = BitmapFactory.decodeStream(input);
                    }

                    this.publishProgress(myBitmap);
                } catch (IOException e) {
                    // Log exception
                    return null;
                }


            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Bitmap... values) {
            super.onProgressUpdate(values);

            ImageView imgview = findViewById(R.id.image);
            imgview.setImageBitmap(values[0]);
        }
    }
}
