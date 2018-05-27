package com.saturn.lab.jcip.TaskExecution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


class FutureRendererImp extends FutureRenderer
{

    @Override
   public void renderPage(CharSequence source) {
        super.renderPage(source);
        System.out.println("renderPage");
    }

    @Override
    void renderText(CharSequence s) {
        System.out.println("renderText");
    }

    @Override
    List<ImageInfo> scanForImageInfo(CharSequence s) {

        List<ImageInfo> list=new ArrayList<>();

        ImageInfo item=new ImageInfo() {
            @Override
            public ImageData downloadImage() {

                ImageData data=new ImageData() {
                };

                return data;
            }
        };

        list.add(item);

        return list;
    }

    @Override
    void renderImage(ImageData i) {
        System.out.println("renderImage");
    }

    public static void main(String [] args)
    {
        FutureRenderer  worker =new FutureRendererImp();

        worker.renderPage("hello");

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * todo
 */
public abstract class FutureRenderer {
    private final ExecutorService executor = Executors.newCachedThreadPool();

   public void renderPage(CharSequence source) {
        final List<ImageInfo> imageInfos = scanForImageInfo(source);
        Callable<List<ImageData>> task =
                new Callable<List<ImageData>>() {
                    public List<ImageData> call() {
                        List<ImageData> result = new ArrayList<ImageData>();
                        for (ImageInfo imageInfo : imageInfos)
                            result.add(imageInfo.downloadImage());
                        return result;
                    }
                };

        //Future<List<ImageData>> future = executor.submit(task);
        Future<List<ImageData>> future = executor.submit(task);

        renderText(source);

        try {
            //List<ImageData> imageData = future.get();
            List<ImageData> imageData = future.get();

            for (ImageData data : imageData) {
                renderImage(data);
            }

        } catch (InterruptedException e) {
            // Re-assert the thread's interrupted status
            Thread.currentThread().interrupt();
            // We don't need the result, so cancel the task too
            future.cancel(true);
        } catch (ExecutionException e) {
            // throw launderThrowable(e.getCause());
        }
    }

    interface ImageData {
    }

    interface ImageInfo {
        ImageData downloadImage();
    }

    abstract void renderText(CharSequence s);

    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    abstract void renderImage(ImageData i);


}