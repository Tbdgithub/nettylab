package com.saturn.lab.jcip.TaskExecution;

import java.util.List;
import java.util.concurrent.*;

public abstract class Renderer {

    private final ExecutorService executor;

    Renderer(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) {
        final List<ImageInfo> info = scanForImageInfo(source);
        //CompletionService<ImageData> completionService =
        //      new ExecutorCompletionService<ImageData>(executor);

        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executor);

        for (final ImageInfo imageInfo : info)

//            completionService.submit(new Callable<ImageData>() {
//                public ImageData call() {
//                    return imageInfo.downloadImage();
//                }
//            });


            completionService.submit(new Callable<ImageData>() {
                @Override
                public ImageData call() throws Exception {
                    //return null;
                    return imageInfo.downloadImage();
                }
            });


        renderText(source);

        try {
            for (int t = 0, n = info.size(); t < n; t++) {
                //Future<ImageData> f = completionService.take();
                Future<ImageData> f = completionService.take();

                //ImageData imageData = f.get();
                ImageData imageData = f.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {

            //Thread.currentThread().interrupt();
            Thread.currentThread().interrupt();

        } catch (ExecutionException e) {
            //throw launderThrowable(e.getCause());
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