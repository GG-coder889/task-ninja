package taskninja.core.dbmodel.tests;

import java.util.LinkedList;
import java.util.List;

import taskninja.core.dbmodel.DbController;
import taskninja.core.dbmodel.DbModel;
import android.content.ContentValues;
import android.content.Context;
import android.os.SystemClock;
import android.test.AndroidTestCase;
import android.util.Log;

public class DbModelTest extends AndroidTestCase {
	
	private static String TAG = "DbModelTest";
	
	private static Context mContext;
	
	public void setUp(){
		mContext = getContext();
	}
	
	public void testId() {
		assertNotNull(TestModel.mController);

		TestModel model = new TestModel();
		int id  = model.getId();
		Log.d(TAG, "getId()="+id);
		assertTrue(id != 0);
		
		model.put(TestInteger.INT1, 5);
		assertEquals(Integer.valueOf(5), model.getInteger(TestInteger.INT1));
		
		assertEquals(model, TestModel.mController.get(id));
		
		
	}
	
	public void testLong(){
		TestModel model = new TestModel();
		TestLong key  = TestLong.LONG1;
		
		assertNull(model.getLong(key));
		
		Long value = 10L;
		
		model.put(key, value);
		
		assertEquals(value, model.getLong(key));
	}
	
	public void testString(){
		TestModel model = new TestModel();
		TestString key  = TestString.STRING1;
		
		assertEquals("", model.getString(key));
		
		String value = "hello value";
		
		model.put(key, value);
		
		assertEquals(value, model.getString(key));
	}
	
	public void testInteger(){
		TestModel model = new TestModel();
		TestInteger key  = TestInteger.INT1;
		
		assertNull(model.getInteger(key));
		
		Integer value = 10;
		
		model.put(key, value);
		
		assertEquals(value, model.getInteger(key));
	}
	
	public void testIntegerList(){
		TestModel model = new TestModel();
		TestIntegerList key  = TestIntegerList.INTEGER_LIST1;
		
		assertEquals(0, model.getIntegerList(key).size());
		
		List<Integer> value = new LinkedList<Integer>();
		value.add(1);
		value.add(2);
		value.add(3);
		
		model.put(key, value);
		
		assertEquals(value, model.getIntegerList(key));
	}
	
	public void testBool(){
		TestModel model = new TestModel();
		TestBool key  = TestBool.BOOL;
		
		assertEquals(false, model.getBool(key));
		
		boolean value = true;
		
		model.put(key, value);
		
		assertEquals(value, model.getBool(key));
	}
	
	public void testGetNewId(){
		int id = TestModel.mController.getNewId();
		assertTrue(id != 0);
		
		assertEquals(++id, TestModel.mController.getNewId());
		assertEquals(++id, TestModel.mController.getNewId());
		assertEquals(++id, TestModel.mController.getNewId());
		assertEquals(++id, TestModel.mController.getNewId());
		assertEquals(++id, TestModel.mController.getNewId());
		
	}
	
	public void testDatabaseWrite(){
		DbController<TestModel, TestInteger, TestLong, TestString, TestIntegerList, TestBool> controller 
		= new TestModel.TestController(TestModel.class, mContext, 1);
		
		TestModel model = new TestModel();
		int id = model.getId();
		TestString key = TestString.STRING1;
		String value = "Hello value";
		model.put(key, value);
		
		controller.register(model);
		
		SystemClock.sleep(1000l);
		
		TestModel readModel = controller.get(id);
		
		assertEquals(value, readModel.getString(key));
	}
	
	private static class TestModel extends DbModel<TestModel, TestInteger, TestLong, TestString, TestIntegerList,TestBool> {

		private static final DbController<TestModel, TestInteger, TestLong, TestString, TestIntegerList,TestBool> mController 
			= new TestController(TestModel.class, mContext, 1);
		
		public TestModel(ContentValues values) {
			super(values);
		}
		
		public TestModel() {
			super();
		}

		@Override
		protected DbController<TestModel, TestInteger, TestLong, TestString, TestIntegerList, TestBool> getController() {
			return mController;
		}
		
		private static class TestController
			
			extends DbController<TestModel, TestInteger, TestLong, TestString, TestIntegerList,TestBool> {
			
			protected TestController(Class<TestModel> dbModel, Context context, int version) {
				super(dbModel, context, version);
				assertNotNull(this.mSQLiteHelper);
			}

			@Override
			protected TestInteger[] getIntegerValues() {
				return TestInteger.values();
			}

			@Override
			protected TestString[] getStringValues() {
				return TestString.values();
			}

			@Override
			protected TestLong[] getLongValues() {
				return TestLong.values();
			}

			@Override
			protected TestIntegerList[] getIntegerListValues() {
				return TestIntegerList.values();
			}

			@Override
			protected TestModel getNewInstance(ContentValues values) {
				return new TestModel(values);
			}

			@Override
			protected TestBool[] getBoolValues() {
				return TestBool.values();
			}

		}
	}
	
	
	
	enum TestInteger {
		INT1,
		INT2
	}
	
	enum TestLong{
		LONG1,
		LONG2
		
	}
	
	enum TestString {
		STRING1,
		STRING2
		
	}
	
	enum TestIntegerList {
		INTEGER_LIST1,
		INTEGER_LIST2
	}
	
	enum TestBool {
		BOOL
	}
	

}
