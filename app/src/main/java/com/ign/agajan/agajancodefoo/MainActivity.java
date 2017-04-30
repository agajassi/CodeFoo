package com.ign.agajan.agajancodefoo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog progressDialog;
    private ListView listView;
    // JSON data url
    private static String Jsonurl = "http://ign-apis.herokuapp.com/articles?startIndex=30&count=5";
    ArrayList<ArticleModel> arrayOfArticles;
    ArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        // Construct the data source
        arrayOfArticles = new ArrayList<ArticleModel>();
        listView = (ListView) findViewById(R.id.listview);
        new GetArticles().execute();
    }


    private class GetArticles extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();

            // request to json data url and getting response
            String jsonString = httpHandler.makeServiceCall(Jsonurl);
            Log.e(TAG, "Response from url: " + jsonString);
            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    // Getting JSON Array node
                    JSONArray articles = jsonObject.getJSONArray("data");

                    for (int i = 0; i < articles.length(); i++) {

                        JSONObject article = articles.getJSONObject(i);
                        String headline = article.getJSONObject("metadata").getString("headline");
                        String publishDate = article.getJSONObject("metadata").getString("publishDate");
                        String articleType = article.getJSONObject("metadata").getString("articleType");

                        JSONArray thumbnails = article.getJSONArray("thumbnails");
                        String posterUrl = thumbnails.getJSONObject(2).getString("url");

                        ArticleModel aModel = new ArticleModel();
                        aModel.setHeadline(headline);
                        aModel.setArticleType(articleType);
                        aModel.setPublishDate(publishDate);
                        aModel.setPosterUrl(posterUrl);

                        arrayOfArticles.add(aModel);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Could not get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Could not get json from server.",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (progressDialog.isShowing())
                progressDialog.dismiss();

            // Create the adapter to convert the array to views
            adapter = new ArticleAdapter(MainActivity.this, arrayOfArticles);
            // Attach the adapter to a ListView
            listView.setAdapter(adapter);
        }
    }
}
