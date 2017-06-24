import java.util.concurrent.Callable;

import de.appsfactory.mvp.preference.annotations.Preferences;
import de.appsfactory.mvp.preferences.Preference;

/**
 * Created by Collider on 24.06.2017.
 */

@Preferences
public interface TestPreferences2 {


    Preference<Integer> appVersion2();
}
