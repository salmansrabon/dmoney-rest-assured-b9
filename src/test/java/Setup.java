import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Setup {
    public Properties props;
    @BeforeTest
    public void setup() throws IOException {
        props=new Properties();
        FileInputStream fs=new FileInputStream("./src/test/resources/config.properties");
        props.load(fs);
    }
}
