package tongtechnologies.com.barcode;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static tongtechnologies.com.barcode.SimpleScannerActivity.UPC;

public class WebView extends AppCompatActivity {


    public static boolean isNetworkStatusAvialable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null)
                if (netInfos.isConnected())
                    return true;
        }
        return false;
    }

    private android.webkit.WebView mWebView;
//    private String testing = getIntent().getExtras().getString("keyName");
//    private String testingBreakPoint;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        String UPC = intent.getStringExtra(SimpleScannerActivity.UPC);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (isNetworkStatusAvialable(getApplicationContext())) {
//            Toast.makeText(getApplicationContext(), "Thriva", Toast.LENGTH_SHORT).show();
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("barcodeprototype.herokuapp.com")
                    .appendPath("hidden_page")
                    .appendQueryParameter("upc", UPC);
            String myUrl = builder.build().toString();
            Log.e("string is", myUrl);
            mWebView = new android.webkit.WebView(this);
            mWebView.loadUrl(myUrl);
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                    if (url.startsWith("mailto:")) {
                        // We use `ACTION_SENDTO` instead of `ACTION_SEND` so that only email programs are launched.
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

                        // Parse the url and set it as the data for the `Intent`.
                        emailIntent.setData(Uri.parse(url));

                        // `FLAG_ACTIVITY_NEW_TASK` opens the email program in a new task instead as part of this application.
                        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        // Make it so.
                        startActivity(emailIntent);
                        return true;
//                    } else if(url.contains("https://www.heythrivy.com/privacy-policy-1/")) { //check for internal url
//                        Intent i = new Intent(Intent.ACTION_VIEW);
//                        i.setData(Uri.parse(url));
//                        startActivity(i);
//                        return true;
////                        return super.shouldOverrideUrlLoading(view, url);
                    } else if (url.equals("https://barcodeprototype.herokuapp.com/")) { //check for logout in url
                        Intent intent = new Intent(WebView.this, SimpleScannerActivity.class);
                        startActivity(intent);
                        return true;
//                        return super.shouldOverrideUrlLoading(view, url);
                    } else {  // Load the URL in `webView`.
                        view.loadUrl(url);
                        return true;
                    }
                }
            });
//                    findViewById(R.id.splash).setVisibility(View.GONE);
//                    findViewById(R.id.webview).setVisibility(View.VISIBLE);


            this.setContentView(mWebView);
        } else {
            Toast.makeText(getApplicationContext(), "Internet is currenly unavialable.  Please check your connection.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WebView.this, MainActivity.class));
        }
    }
}

