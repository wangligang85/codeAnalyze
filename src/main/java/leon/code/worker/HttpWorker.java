package leon.code.worker;

import leon.code.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangligang85@163.com on 2018-12-11 11:38
 */
public class HttpWorker {
    private final static Logger logger = LoggerFactory.getLogger(StoreWorker.class);
    private ExecutorService executor = Executors.newFixedThreadPool(Constants.HTTP_WORKER_THREAD_NUM);

    private HttpWorker(){}

    public static final HttpWorker getInstance() {
        return HttpWorkerHolder.INSTANCE;
    }

    /**
     * 并行做curl请求
     */
    public void curl(String keeWord){
        executor.submit(new HttpJobTask(keeWord));
    }

    class HttpJobTask implements Runnable, Serializable {
        String projectName;
        HttpJobTask(String projectName) {
            this.projectName = projectName;
        }
        public void run() {
            try {
                String jsonResponse = getJsonResponse(projectName);
                if (StringUtils.isNotBlank(jsonResponse)) {
                    //请求结果交由另外的线程池处理
                    StoreWorker.getInstance().store(jsonResponse);
                }
            }catch (Exception e){
                logger.error(e.getMessage(), e);
            }
        }
    }
    static String getJsonResponse(String projectName){
        logger.info("do get request : {}", projectName);
        String url = Constants.HTTP_URL + "&componentKey=" + projectName;
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader(Constants.HTTP_HEADER_KEY_COOKIE, Constants.HTTP_HEADER_VALUE_COOKIE);
            httpGet.setHeader(Constants.HTTP_HEADER_KEY_ACCEPT, Constants.HTTP_HEAD_VALUE_ACCEPT);

            // 执行请求
            response = httpclient.execute(httpGet);

            // 处理结果
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), Constants.HTTP_ENCODING);
            } else {
                logger.warn(projectName  + "__" + response.getStatusLine().getStatusCode() + "___" + response.getStatusLine().getReasonPhrase());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return resultString;
    }

    private static class HttpWorkerHolder {
        private static final HttpWorker INSTANCE = new HttpWorker();
    }
}


