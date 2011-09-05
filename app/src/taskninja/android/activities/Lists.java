package taskninja.android.activities;

import java.sql.SQLException;

import taskninja.android.R;
import taskninja.android.ormlite.DatabaseHelper;
import android.app.Activity;
import android.os.Bundle;

public class Lists extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        DatabaseHelper.getInstance();
        try {
			DatabaseHelper.getListDao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}