package SingleThreadCrawler;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * Created by dwight12 on 2016/1/2.
 */

public class FileDownloader {



    public String download (String url) {
        String filePath = null;

        HttpClient httpClient = new HttpClient();

        /**
         * 设置http连接超时五秒
         */
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

        GetMethod getMethod = new GetMethod(url);

        /**
         * 设置get请求超时五秒和设置请求重试处理
         */
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler());

        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.out.print("something wrong with Method");
            }

            byte[] response = getMethod.getResponseBody();

            filePath = "download\\" + getFileName(url,getMethod.getResponseHeader("Content-Type").getValue());
            saveToLocal(response, filePath);
        } catch (HttpException e) {
            System.out.println("HttpException");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }

        return filePath;
    }

    public String getFileName(String url, String contentType) {
        /**
         * 正则表达式还有点晕，要好好复习下
         */
        url = url.substring(7);
        if (contentType.indexOf("html") != -1) {
            url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
            return url;
        } else {
            url = url.replaceAll("[\\?/:*|<>\"]", "_")  + "."
                    + contentType.substring(contentType.lastIndexOf("/")+1);
            return url;
        }
    }



    private void saveToLocal(byte[] data, String filePath) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(
                  new FileOutputStream(new File(filePath)));
            for (int i = 0; i < data.length; i++) {
                dataOutputStream.write(data[i]);
            }
            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


