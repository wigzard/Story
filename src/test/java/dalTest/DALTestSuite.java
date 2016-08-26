package dalTest;

import dalTest.loadDataFromDBTest.LoadDataActorsTest;
import dalTest.loadDataFromDBTest.LoadDataMapsTest;
import dalTest.loadSettings.SettingsLoaderTest;
import org.junit.runner.RunWith;

/**
 * Created by alex on 30.03.16.
 */
@RunWith(org.junit.runners.Suite.class)
@org.junit.runners.Suite.SuiteClasses({
        SettingsLoaderTest.class,
        LoadDataMapsTest.class,
        LoadDataActorsTest.class,
})
public class DALTestSuite {
}
