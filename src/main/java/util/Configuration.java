package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Configuration {

    private Properties prop = new Properties();
    private String configPropPath;


    public Configuration() throws IOException {
        String configPath = readEnvironment();
        loadConfigProperties(configPath);
    }

    private void loadConfigProperties(String path) throws IOException {
        InputStream in = new FileInputStream(path);
        prop.load(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    public String readEnvironment() {
        //istenirse test ortamı canlı ortam şeklinde parametrik yapılabilir.
        return configPropPath = System.getProperty("user.dir") + "\\src\\main\\resources\\environment\\prod_env.properties";
    }


    public String getBaseUrl() {
        return prop.getProperty("base.url");
    }

    public String getTesData() {
        return prop.getProperty("test.data");
    }
    public String getProductInfoFile() {
        return prop.getProperty("product.info");
    }
}
