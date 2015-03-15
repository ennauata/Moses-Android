package com.moses.Moses;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.moses.Adapters.AddGroupListAdapter;
import com.moses.Adapters.NavBarAdapter;
import com.moses.DAO.GroupDAO;
import com.moses.DAO.UserDAO;
import com.moses.Utils.BaseListElement;
import com.moses.Utils.Callback;

public class CreateGroupActivity extends Activity {
	private String TAG = "ERROR";
	private static final int PICK_IMAGE = 1;
	private ListView listView;
	private List<BaseListElement> listElements;
	private Context mContext;
	private List<GraphUser> selectedUsers;
	private static final String FRIENDS_KEY = "friends";
	private UiLifecycleHelper uiHelper;
	private EditText inputGroupNameView;
	private List<GraphUser> usersToAdd;
	private int[] mThumbIds = { R.drawable.icons_home_gray,
			R.drawable.icons_group_blue, R.drawable.icons_bill_gray,
			R.drawable.icons_configuration_gray };
	private ImageButton inputPictureButton;
	private ImageView groupPic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_list);
		mContext = getApplicationContext();
		inputPictureButton = (ImageButton) findViewById(R.id.inputPictureButton);
		inputGroupNameView = (EditText) findViewById(R.id.inputGroupName);

		// Setup NavBar Gridview
		GridView navBar = (GridView) findViewById(R.id.navBar);

		navBar.setAdapter(new NavBarAdapter(this, mThumbIds));
		navBar.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter,
					android.view.View v, int position, long id) {
				Intent intent;
				switch (position) {
				// Home
				case 0:
					intent = new Intent(mContext, MainActivity.class);
					startActivity(intent);
					finish();
					break;

				// New Group
				case 1:

					intent = new Intent(mContext, CreateGroupActivity.class);
					startActivity(intent);
					finish();
					break;

				// New Bill
				case 2:
					intent = new Intent(mContext, CreateBillActivity.class);
					startActivity(intent);
					finish();
					break;

				// Configuration
				case 3:
					intent = new Intent(mContext, ConfigurationActivity.class);
					startActivity(intent);
					finish();
					break;

				default:
					break;
				}
			}
		});

		// Setup Upload Image Button
		inputPictureButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Setup gallery fragment
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"),
						PICK_IMAGE);

			}
		});

		// Setup Done Button
		Button doneB = (Button) findViewById(R.id.done);
		doneB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					UserDAO.getInstance().setUsersToAdd(
							MosesApplication.getSelectedUsers());
					
					GroupDAO.getInstance().setGroupDaoCallback(new Callback() {
						
						@Override
						public void callbackCall() {
							MainActivity.updateList();
						}
					});
					
					GroupDAO.getInstance().createGroup(
							inputGroupNameView.getText().toString());

					// Switch Screen to Main Activity
					startMainActivity();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// Find the list view
		listView = (ListView) findViewById(R.id.selection_list);

		// Set up the list view items, based on a list of
		// BaseListElement items
		listElements = new ArrayList<BaseListElement>();

		// Set the list view adapter
		listView.setAdapter(new AddGroupListAdapter(this, R.id.selection_list,
				listElements));

		// Check for an open session
		uiHelper = new UiLifecycleHelper(this, null);

		// Restore Elements
		if (savedInstanceState != null) {
			// Restore the state for each list element
			for (BaseListElement listElement : listElements) {
				listElement.restoreState(savedInstanceState);
			}
		}

		// Set up group picture
		groupPic = (ImageView) findViewById(R.id.groupPic);
	}

	public void add_member(View v) {
		startPickerActivity(PickerActivity.FRIEND_PICKER, 0);
	}

	private void startPickerActivity(Uri data, int requestCode) {
		Intent intent = new Intent();
		intent.setData(data);
		intent.setClass(mContext, PickerActivity.class);
		startActivityForResult(intent, requestCode);
	}

	@Override
	public void onBackPressed() {
		startMainActivity();
	}

	public void startMainActivity() {
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		for (BaseListElement listElement : listElements) {
			listElement.onSaveInstanceState(bundle);
		}
		uiHelper.onSaveInstanceState(bundle);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PICK_IMAGE && data != null && data.getData() != null) {
			Uri selectedImageUri = data.getData();
			Matrix matrix = new Matrix();
			float rotation = rotationForImage(mContext, selectedImageUri);
			if (rotation != 0f) {
				matrix.preRotate(rotation);
			}

			// User had pick an image.
			Cursor cursor = getContentResolver()
					.query(selectedImageUri,
							new String[] { android.provider.MediaStore.Images.ImageColumns.DATA },
							null, null, null);
			cursor.moveToFirst();

			// Link to the image
			final String imageFilePath = cursor.getString(0);
			cursor.close();

			Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);

			Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
					bitmap.getWidth(), bitmap.getHeight(), matrix, true);

			groupPic.setImageBitmap(resizedBitmap);

		} else if (resultCode == Activity.RESULT_OK && requestCode >= 0
				&& requestCode <= listElements.size()) {
			selectedUsers = MosesApplication.getSelectedUsers();
			Boolean exists = false;
			for (int i = 0; i < selectedUsers.size(); i++) {
				for (int j = 0; j < listElements.size(); j++) {
					if (listElements.get(j).getFbId()
							.equals(selectedUsers.get(i).getId())) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					PeopleListElement newUser = new PeopleListElement(0,
							selectedUsers.get(i).getName(), Activity.RESULT_OK,
							selectedUsers.get(i).getId());
					listElements.add(newUser);
				}
			}
			listView.setAdapter(new AddGroupListAdapter(this,
					R.id.selection_list, listElements));
		}
	}

	public static float rotationForImage(Context context, Uri uri) {
		if (uri.getScheme().equals("content")) {
			String[] projection = { Images.ImageColumns.ORIENTATION };
			Cursor c = context.getContentResolver().query(uri, projection,
					null, null, null);
			if (c.moveToFirst()) {
				return c.getInt(0);
			}
		} else if (uri.getScheme().equals("file")) {
			try {
				ExifInterface exif = new ExifInterface(uri.getPath());
				int rotation = (int) exifOrientationToDegrees(exif
						.getAttributeInt(ExifInterface.TAG_ORIENTATION,
								ExifInterface.ORIENTATION_NORMAL));
				return rotation;
			} catch (IOException e) {
				// Log.e(TAG, "Error checking exif", e);
			}
		}
		return 0f;
	}

	private static float exifOrientationToDegrees(int exifOrientation) {
		if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
			return 90;
		} else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
			return 180;
		} else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
			return 270;
		}
		return 0;
	}

	public List<GraphUser> getUsersToAdd() {
		return usersToAdd;
	}

	public void setUsersToAdd(List<GraphUser> usersToAdd) {
		this.usersToAdd = usersToAdd;
	}

	// Private Class PeopleListElement
	private class PeopleListElement extends BaseListElement {

		public PeopleListElement(int icLauncher, String text, int requestCode,
				String fbId) {
			super(icLauncher, text, requestCode, fbId);
		}

		private byte[] getByteArray(List<GraphUser> users) {
			// convert the list of GraphUsers to a list of String
			// where each element is the JSON representation of the
			// GraphUser so it can be stored in a Bundle
			List<String> usersAsString = new ArrayList<String>(users.size());
			for (GraphUser user : users) {
				usersAsString.add(user.getInnerJSONObject().toString());
			}
			try {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				new ObjectOutputStream(outputStream).writeObject(usersAsString);
				return outputStream.toByteArray();
			} catch (IOException e) {
				Log.e(TAG, "Unable to serialize users.", e);
			}
			return null;
		}

		private List<GraphUser> restoreByteArray(byte[] bytes) {
			try {
				@SuppressWarnings("unchecked")
				List<String> usersAsString = (List<String>) (new ObjectInputStream(
						new ByteArrayInputStream(bytes))).readObject();

				if (usersAsString != null) {
					List<GraphUser> users = new ArrayList<GraphUser>(
							usersAsString.size());
					for (String user : usersAsString) {
						GraphUser graphUser = GraphObject.Factory.create(
								new JSONObject(user), GraphUser.class);
						users.add(graphUser);
					}
					return users;
				}
			} catch (ClassNotFoundException e) {
				Log.e(TAG, "Unable to deserialize users.", e);
			} catch (IOException e) {
				Log.e(TAG, "Unable to deserialize users.", e);
			} catch (JSONException e) {
				Log.e(TAG, "Unable to deserialize users.", e);
			}
			return null;
		}

		@Override
		public boolean restoreState(Bundle savedState) {
			byte[] bytes = savedState.getByteArray(FRIENDS_KEY);
			if (bytes != null) {
				selectedUsers = restoreByteArray(bytes);
				return true;
			}
			return false;
		}

		@Override
		public void onSaveInstanceState(Bundle bundle) {
			if (selectedUsers != null) {
				bundle.putByteArray(FRIENDS_KEY, getByteArray(selectedUsers));
			}
		}

		@Override
		public View.OnClickListener getOnClickListener() {
			return new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					// Do nothing for now
				}
			};
		}
	}

}
