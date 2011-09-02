package android.taskninja.test.dbmodel;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.os.SystemClock;
import android.taskninja.dbmodel.Db_Controller;
import android.taskninja.dbmodel.Db_Listener;
import android.taskninja.dbmodel.Db_Model;
import android.test.AndroidTestCase;
import android.util.Log;

public class Db_Model_Test extends AndroidTestCase {
	
	private static String TAG = "DbModelTest";
	
	private static Context mContext;
	
	@Override
	public void setUp(){
		mContext = getContext();
	}
	
	@Override
	public void tearDown(){
		assertTrue(mContext.getSharedPreferences("TestModel", 4).edit().clear().commit());		
	}
	
	public void testId() {
		assertNotNull(TestModel.mController);

		TestModel model = new TestModel();
		String id  = model.getId();
		Log.d(TAG, "getId()="+id);
		assertTrue(id != null);
		
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
		
		assertNull(model.getString(key));
		
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
		
		TestModel model = new TestModel();
		String id = model.getId();
		TestString key = TestString.STRING1;
		String value = "Hello value";
		model.put(key, value);
		
		SystemClock.sleep(1000l);
		
		Db_Controller<TestModel, TestInteger, TestLong, TestString, TestBool> controller 
		= new TestModel.TestController(TestModel.class, mContext);
		
		TestModel readModel = controller.get(id);
		
		assertEquals(value, readModel.getString(key));
	}
	
	public void testDelete(){
		
		TestModel model = new TestModel();
		assertSame(model, TestModel.mController.get(model.getId()));
		
		model.delete();
		assertTrue(model.isDeleted());
		assertEquals(null, TestModel.mController.get(model.getId()));
		
		
		// Test that model.delete() deletes a model
		model = new TestModel();
		assertSame(model, TestModel.mController.get(model.getId()));
		model.delete();
		assertTrue(model.isDeleted());
		assertEquals(null, TestModel.mController.get(model.getId()));
		SystemClock.sleep(1000l);
		
		Db_Controller<TestModel, TestInteger, TestLong, TestString, TestBool> controller 
		= new TestModel.TestController(TestModel.class, mContext);
		
		assertEquals(null, controller.get(model.getId()));
		
	}
	
	public void testListener(){
		final Stack<Enum> enums = new Stack();
		TestModel model = new TestModel();
		
		model.addListener(new Db_Listener() {
			@Override
			public void onChange(Enum key) {
			enums.push(key);
			}
		});
		
		model.put(TestBool.BOOL, true);
		model.put(TestBool.BOOL, false);
		model.put(TestInteger.INT1, 1);
		
		assertEquals(TestInteger.INT1, enums.pop());
		assertEquals(TestBool.BOOL, enums.pop());
		assertEquals(TestBool.BOOL, enums.pop());
		
		
		
		
	}
	
	private static class TestModel extends Db_Model<TestModel, TestInteger, TestLong, TestString,TestBool> {

		private static final Db_Controller<TestModel, TestInteger, TestLong, TestString,TestBool> mController 
			= new TestController(TestModel.class, mContext);
		
		
		public TestModel() {
			super();
		}

		public TestModel(JSONObject json) {
			super(json);
		}

		@Override
		protected Db_Controller<TestModel, TestInteger, TestLong, TestString, TestBool> getController() {
			return mController;
		}
		
		private static class TestController
			
			extends Db_Controller<TestModel, TestInteger, TestLong, TestString,TestBool> {
			
			protected TestController(Class<TestModel> dbModel, Context context) {
				super(dbModel, context);
			}

			@Override
			protected TestModel getInstance(JSONObject json) {
				return new TestModel(json);
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
	
	enum TestBool {
		BOOL
	}
	

}
