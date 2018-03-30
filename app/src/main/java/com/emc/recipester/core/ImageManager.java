package com.emc.recipester.core;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by Munsif on 3/30/2018.
 */

public class ImageManager {

    private HashMap<String, Bitmap> imageMap = new HashMap<>();
    private File cacheDirectory;
    private Thread imageLoaderThread = new Thread(new ImageQueueManager());
    private ImageQueue imageQueue = new ImageQueue();
    private String folderPath = "data/recipester";

    public ImageManager(Context context) {
        // Make background thread low priority, to avoid affecting UI performance
        imageLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);
        // Find the dir to save cached images
        String sdState = android.os.Environment.getExternalStorageState();
        if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
            File sdDir = android.os.Environment.getExternalStorageDirectory();
            cacheDirectory = new File(sdDir, folderPath);
        } else {
            cacheDirectory = context.getCacheDir();
        }

        if (!cacheDirectory.exists()) {
            cacheDirectory.mkdirs();
        }
    }

    private void queueImage(String url, ImageView imageView) {
        // This ImageView might have been used for other images, so we clear
        // the queue of old tasks before starting.
        imageQueue.Clean(imageView);
        ImageRef imageRef = new ImageRef(url, imageView);
        synchronized (imageQueue.imageRefStack) {
            imageQueue.imageRefStack.push(imageRef);
            imageQueue.imageRefStack.notifyAll();
        }
        // Start thread if it's not started yet
        if (imageLoaderThread.getState() == Thread.State.NEW) {
            imageLoaderThread.start();
        }
    }

    private Bitmap getBitmap(String url) {
        String filename = String.valueOf(url.hashCode());
        File imageFileReference = new File(cacheDirectory, filename);
        // Is the bitmap in our cache?
        Bitmap bitmap = BitmapFactory.decodeFile(imageFileReference.getPath());
        if (bitmap != null) {
            return bitmap;
        }
        // Nope, have to download it
        try {
            bitmap = BitmapFactory.decodeStream(new URL(url).openConnection().getInputStream());
            // save bitmap to cache for later
            writeFile(bitmap, imageFileReference);
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void writeFile(Bitmap bmp, File file) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    public void displayImage(String url, ImageView imageView) {
        if (imageMap.containsKey(url)) {
            imageView.setImageBitmap(imageMap.get(url));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            queueImage(url, imageView);
            imageView.setImageResource(android.R.drawable.gallery_thumb);
        }
    }


    private class ImageRef {
        public String url;
        public ImageView imageView;

        public ImageRef(String url, ImageView imgRecipe) {
            this.url = url;
            this.imageView = imgRecipe;
        }
    }

    //stores list of images to download
    private class ImageQueue {
        private Stack<ImageRef> imageRefStack = new Stack<>();

        //removes all instances of this ImageView
        public void Clean(ImageView view) {
            for (int i = 0; i < imageRefStack.size(); ) {
                if (imageRefStack.get(i).imageView == view) {
                    imageRefStack.remove(i);
                } else {
                    ++i;
                }
            }
        }
    }

    private class ImageQueueManager implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    // Thread waits until there are images in the
                    // queue to be retrieved
                    if (imageQueue.imageRefStack.size() == 0) {
                        Log.d("xxxxx", "imageRefStack.size() == 0");
                        synchronized (imageQueue.imageRefStack) {
                            imageQueue.imageRefStack.wait();
                        }
                    }
                    // When we have images to be loaded
                    if (imageQueue.imageRefStack.size() != 0) {
                        Log.d("xxxxx", "imageRefStack.size() != 0");
                        ImageRef imageToLoad;
                        synchronized (imageQueue.imageRefStack) {
                            imageToLoad = imageQueue.imageRefStack.pop();
                        }
                        Bitmap bmp = getBitmap(imageToLoad.url);
                        imageMap.put(imageToLoad.url, bmp);
                        Object tag = imageToLoad.imageView.getTag();
                        // Make sure we have the right view - thread safety defender
                        if (tag != null && ((String) tag).equals(imageToLoad.url)) {
                            Log.d("xxxxx", "tag != null");
                            BitmapDisplayer bmpDisplayer = new BitmapDisplayer(bmp, imageToLoad.imageView);
                            Activity context = (Activity) imageToLoad.imageView.getContext();
                            context.runOnUiThread(bmpDisplayer);
                        } else {
                            BitmapDisplayer bmpDisplayer = new BitmapDisplayer(bmp, imageToLoad.imageView);
                            Activity context = (Activity) imageToLoad.imageView.getContext();
                            context.runOnUiThread(bmpDisplayer);
                        }
                    }
                    if (Thread.interrupted()) {
                        Log.d("xxxxx", "Thread.interrupted()");
                        break;
                    }
                }
            } catch (InterruptedException e) {
                Log.d("xxxxx", "InterruptedException");

            }
        }
    }

    //Used to display bitmap in the UI thread
    private class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        ImageView imageView;

        public BitmapDisplayer(Bitmap bmp, ImageView recipeImg) {
            bitmap = bmp;
            imageView = recipeImg;
        }

        public void run() {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView.setImageResource(android.R.drawable.gallery_thumb);
            }
        }
    }
}
