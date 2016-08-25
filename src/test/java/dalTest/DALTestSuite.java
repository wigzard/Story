package dalTest;

import dalTest.DataActionsTest.RetrieveMapsActionTest;
import dalTest.loadSettings.SettingsLoaderTest;
import org.junit.runner.RunWith;

/**
 * Created by alex on 30.03.16.
 */
@RunWith(org.junit.runners.Suite.class)
@org.junit.runners.Suite.SuiteClasses({
        SettingsLoaderTest.class,
        RetrieveMapsActionTest.class,
})
public class DALTestSuite {
}
