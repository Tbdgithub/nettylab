import com.google.gson.Gson;
import com.saturn.sort.merge.Config;
import org.junit.Test;

public class Config_test {


    @Test
    public void test1() {

        Gson gson = new Gson();

        Config config = new Config();
        config.setInputDir("/sz/MergeSorter/inputDir");
        config.setOutputDir("/sz/MergeSorter/outputDir");
        config.setTempDir("/sz/MergeSorter/tempDir");
        config.setMaxLinePerFile(1000);
        config.setAntiDuplicate(true);

        String str = gson.toJson(config);
        System.out.println(str);
    }
}
