package vibrationspotter.vibrationspotterapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleyClass {
    private static VolleyClass volleyInstance;
    private RequestQueue vRequestQueue;
    private ImageLoader vImageLoader;
    private static Context vContext;

    private VolleyClass(Context context){
        vContext = context;
        vRequestQueue = getRequestQueue();
        vImageLoader = new ImageLoader(vRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String,Bitmap> cache = new LruCache<>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);
            }
        });
    }

    public static synchronized VolleyClass getInstance(Context context){
        if(volleyInstance == null) volleyInstance = new VolleyClass(context);
        return volleyInstance;
    }

    public RequestQueue getRequestQueue(){
        if(vRequestQueue == null) vRequestQueue = Volley.newRequestQueue(vContext.getApplicationContext());
        return vRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag){
        DefaultRetryPolicy policy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS/2,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return vImageLoader;
    }

    public void cancelPendingRequests(Object tag){
        if(vRequestQueue != null) vRequestQueue.cancelAll(tag);
    }

}
