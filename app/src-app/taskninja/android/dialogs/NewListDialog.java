package taskninja.android.dialogs;

import java.sql.SQLException;

import taskninja.android.ActionListener;
import taskninja.android.models.NinjaList;
import taskninja.android.ormlite.DatabaseHelper;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class NewListDialog extends Dialog implements android.view.View.OnClickListener {
	
	private ActionListener<NinjaList> mListener;
	
	private LinearLayout mLayout;
	private EditText mEditText;
	private Button mSaveButton;
	private Button mCancelButton;

	public NewListDialog(Context context, ActionListener<NinjaList> listener) {
		super(context);
		
		mListener = listener;
		
		setTitle("Create a New List");
		
		mLayout = new LinearLayout(context);
		mLayout.setOrientation(mLayout.VERTICAL);
		
		mEditText = new EditText(context);
		mEditText.setHint("List Title");
		
		mSaveButton = new Button(context);
		mSaveButton.setText("Save");
		mSaveButton.setOnClickListener(this);
		
		mCancelButton = new Button(context);
		mCancelButton.setText("Cancel");
		mCancelButton.setOnClickListener(this);
		
		mLayout.addView(mEditText);
		mLayout.addView(mCancelButton);
		mLayout.addView(mSaveButton);

		setContentView(mLayout);
	}

	@Override
	public void onClick(View v) {
		if (v.equals(mSaveButton)){
			String text = mEditText.getEditableText().toString();
			if (isValid(text)) {
				newList(text);
				this.dismiss();
			} else {
				
			}
			
		} else if (v.equals(mCancelButton)){
			this.dismiss();
		}
		
	}
	
	private void newList(String text) {
		try {
			NinjaList list = new NinjaList(text);
			DatabaseHelper.getListDao().create(list);
			mListener.onAction(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private boolean isValid(String text) {
		return true;
	}


	
	

}
