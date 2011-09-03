package com.rocksolidmobility.taskninja.android.test;

import java.util.Stack;

import org.json.JSONObject;

import android.content.Context;
import android.os.SystemClock;
import android.test.AndroidTestCase;
import android.util.Log;

import com.rocksolidmobility.android.rsmodel.RSController;
import com.rocksolidmobility.android.rsmodel.RSListener;
import com.rocksolidmobility.android.rsmodel.RSModel;

public class RSModelTest extends AndroidTestCase {
	
	private static String TAG = "DbModelTest";
	
	@Override
	public void setUp(){
		RSModel.setContext(getContext());
	}
	
	@Override
	public void tearDown(){
		assertTrue(mContext.getSharedPreferences(TestModel.class.getSimpleName(), 4).edit().clear().commit());		
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
		TestBool key  = TestBool.BOOL1;
		
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
		
		RSController<TestModel, TestInteger, TestLong, TestString, TestBool> controller 
		= new TestModel.LocalController(TestModel.class, mContext);
		
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
		
		RSController<TestModel, TestInteger, TestLong, TestString, TestBool> controller 
		= new TestModel.LocalController(TestModel.class, mContext);
		
		assertEquals(null, controller.get(model.getId()));
		
	}
	
	public void testListener(){
		final Stack<Enum> enums = new Stack();
		TestModel model = new TestModel();
		
		model.addListener(new RSListener() {
			@Override
			public void onChange(Enum key) {
			enums.push(key);
			}
		});
		
		model.put(TestBool.BOOL1, true);
		model.put(TestBool.BOOL1, false);
		model.put(TestInteger.INT1, 1);
		
		assertEquals(TestInteger.INT1, enums.pop());
		assertEquals(TestBool.BOOL1, enums.pop());
		assertEquals(TestBool.BOOL1, enums.pop());
	}
	
	private static class TestModel extends RSModel<TestModel, TestInteger, TestLong, TestString,TestBool> {

		public TestModel(){
			super();
		}
		
		// ----------------------------------------------------------------------------------------------------
		//  RSModel Interface Configuration
		// ----------------------------------------------------------------------------------------------------
		private static RSController<TestModel, TestInteger, TestLong, TestString,TestBool> mController;
		
		@Override
		protected RSController<TestModel, TestInteger, TestLong, TestString,TestBool> instanceGetController() {
			return staticGetController();
		}
		
		private static RSController<TestModel, TestInteger, TestLong, TestString,TestBool> staticGetController(){
			if (mController == null){
				try {
					mController = new LocalController(TestModel.class, mContext);
				}catch (Exception e2){
					e2.printStackTrace();
					return null;
				}
			}
			return mController;
		}
		
		private TestModel(JSONObject JSONObject){
			super(JSONObject);
		}
		
		private static class LocalController extends RSController<TestModel, TestInteger, TestLong, TestString,TestBool> {
				
			protected LocalController(Class<TestModel> dbModel, Context context) {
				super(dbModel, context);
			}

			@Override
			protected TestModel getInstance(JSONObject json) {
				return new TestModel(json);
			}	
		}
		// ----------------------------------------------------------------------------------------------------
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
		BOOL1,
		BOOL2
	}
	

}
