package taskninja.android.test.models;

import taskninja.android.models.NinjaList;
import taskninja.android.ormlite.DatabaseHelper;
import android.test.AndroidTestCase;

public class NinjaListTest extends AndroidTestCase {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		DatabaseHelper.setContext(getContext());
	}
	
	public void testCreation() {
		String title = "title";
		NinjaList list = new NinjaList(title);
		assertEquals(title, list.getTitle());
	}

}
