package leon.code.worker;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hu.netmind.beankeeper.Store;
import leon.code.Constants;
import leon.code.domain.AnalyzeSourceData;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wangligang85@163.com on 2018-12-11 11:38
 */
public class StoreWorker {
    private final static Logger logger = LoggerFactory.getLogger(StoreWorker.class);


    private StoreWorker(){}

    private static AtomicLong storeCounter = new AtomicLong();

    public static final StoreWorker getInstance() {
        return StoreWorkerHolder.INSTANCE;
    }

    private ExecutorService executor = Executors.newFixedThreadPool(Constants.STORE_WORKER_THREAD_NUM);

    private static Store store = new Store(Constants.MYSQL_DRIVER_KEY, Constants.MYSQL_CONNECT_INFO);

    public void store(String jsonResult){
        executor.submit(new StoreJobTask(jsonResult));
    }

    class StoreJobTask implements Runnable, Serializable {
        String jsonResult;
        StoreJobTask(String jsonResult) {
            this.jsonResult = jsonResult;
        }
        public void run() {
            try {
                logger.info("save data ...  {}", storeCounter.incrementAndGet());
                AnalyzeSourceData data = new AnalyzeSourceData();
                Map kvMap = analysisHttpResponse();
                BeanUtils.populate(data, kvMap);
                store.save(data);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        /**
         * 解析http json response to map
         */
        private Map analysisHttpResponse() {
            Map kvMap = new HashMap();
            JSONObject jsonObject = (JSONObject) JSON.parse(jsonResult);
            Object component = jsonObject.get("component");
            if (null != component) {
                Object measures = ((JSONObject) component).get("measures");
                kvMap.put("name", ((JSONObject) component).get("key"));
                kvMap.put("id", ((JSONObject) component).get("id"));
                if (null != measures) {
                    JSONArray objects = (JSONArray) measures;
                    for (Object object : objects) {
                        String key = (String) ((JSONObject) object).get("metric");
                        String value = (String) ((JSONObject) object).get("value");
                        if (null != key && null != value) {
                            kvMap.put(key, value);
                        }
                    }
                }
            }
            return kvMap;
        }
    }

    private static class StoreWorkerHolder {
        private static final StoreWorker INSTANCE = new StoreWorker();
    }
}


