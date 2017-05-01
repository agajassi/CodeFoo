package com.ign.agajan.agajancodefoo;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://www.ign.com/articles/";
    private static String JsonArticleUrl = "http://ign-apis.herokuapp.com/articles?startIndex=30&count=13";
    private static String JsonVideoUrl = "http://ign-apis.herokuapp.com/videos?startIndex=9&count=9";
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private ListView alistView; // listView for articles

    ArrayList<ArticleModel> arrayOfArticles;
    ArrayList<VideoModel> arrayOfVideos;
    ArticleAdapter articleAdapter;
    Typeface custom_font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        // Construct the data source
        arrayOfArticles = new ArrayList<ArticleModel>();
        arrayOfVideos = new ArrayList<VideoModel>();

        alistView = (ListView) findViewById(R.id.articleListview);
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/din_alt.ttf");
        new GetStuffFromAPI().execute();
    }

    private class GetStuffFromAPI extends AsyncTask<Void, Void, Void> {
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
            String aJsonString = httpHandler.makeServiceCall(JsonArticleUrl);
            String vJsonString = httpHandler.makeServiceCall(JsonVideoUrl);

            Log.e(TAG, "Article response from url: " + aJsonString);
            Log.e(TAG, "Video response from url: " + vJsonString);
            if ((aJsonString != null) && (vJsonString != null)) {
                try {
                    processArticlesJson(aJsonString);
                    processVideosJson(vJsonString);
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

        /**
         * Processes jsonString by parsing individual fields and
         * populates arrayOfArticles with ArticleModel.
         * @param jsonString
         * @throws JSONException
         */
        protected void processArticlesJson(String jsonString) throws JSONException {
            JSONObject jsonObject = new JSONObject(jsonString);
            // Getting JSON Array node
            JSONArray articles = jsonObject.getJSONArray("data");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String headline = article.getJSONObject("metadata").getString("headline");
                String publishDate = article.getJSONObject("metadata").getString("publishDate");
                String slug = article.getJSONObject("metadata").getString("slug");

                JSONArray thumbnails = article.getJSONArray("thumbnails");
                String posterUrl = thumbnails.getJSONObject(2).getString("url");

                ArticleModel aModel = new ArticleModel();
                aModel.setHeadline(headline);
                aModel.setPublishedSince(publishDate);
                aModel.setPosterUrl(posterUrl);
                aModel.setArticleUrl(BASE_URL + slug);

                arrayOfArticles.add(aModel);
            }
        }

        /**
         * Processes jsonString by parsing individual fields and
         * populates arrayOfVideos with VideoModel.
         * @param jsonString
         * @throws JSONException
         */
        protected void processVideosJson(String jsonString) throws JSONException {
            JSONObject jsonObject = new JSONObject(jsonString);
            // Getting JSON Array node
            JSONArray videos = jsonObject.getJSONArray("data");

            for (int i = 0; i < videos.length(); i++) {
                JSONObject video = videos.getJSONObject(i);
                String headline = video.getJSONObject("metadata").getString("name");

                JSONArray thumbnails = video.getJSONArray("thumbnails");
                String posterUrl = thumbnails.getJSONObject(0).getString("url");

                VideoModel vModel = new VideoModel();
                vModel.setHeadline(headline);
                vModel.setPosterUrl(posterUrl);

                arrayOfVideos.add(vModel);
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (progressDialog.isShowing())
                progressDialog.dismiss();

            // Create the adapter to convert the array to views
            articleAdapter = new ArticleAdapter(MainActivity.this, arrayOfArticles, custom_font, arrayOfVideos);

            // Attach the adapter to a ListView
            alistView.setAdapter(articleAdapter);

            alistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String urlToLoad = articleAdapter.getItem(position).getArticleUrl();
                    WebViewActivity wbActivity = new WebViewActivity();
                    Intent intent = new Intent(MainActivity.this, wbActivity.getClass());
                    intent.putExtra("URL", urlToLoad);
                    startActivity(intent);
                }
            });
            Log.e(TAG, "Video array size: " + arrayOfVideos.size());
        }
    }
}
