import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Utils {
    public static void setEnvVariable(String key, String value) throws ConfigurationException {
        PropertiesConfiguration config=new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key, value);
        config.save();
    }
    public static int generateRandom(int min, int max){
        double rand= Math.random()*(max-min)+min;
        return (int) rand;
    }

    public static void main(String[] args) {
        int rand= generateRandom(100000,999999);
        System.out.println(rand);
    }
}
