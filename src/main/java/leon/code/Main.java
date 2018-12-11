package leon.code;

import leon.code.worker.HttpWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author wangligang85@163.com on 2018-12-10 18:02
 */
public class Main {
    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String INPUT_FILE_PATH = "/Users/leon/code_scan/mmm_info_source_sonar_project.txt";

    public static void main(String[] args) {
        dealDataInFile();
    }

    /**
     * 读取文件中的内容并处理
     */
    static void dealDataInFile(){
        try (Stream<String> stream = Files.lines(Paths.get(INPUT_FILE_PATH))) {
            stream.forEach(HttpWorker.getInstance()::curl);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
    }
}
