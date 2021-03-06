package com.fratelloinnotech.blooddonors.SQliteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.fratelloinnotech.blooddonors.Beans.ImageHelperBean;

import java.io.ByteArrayOutputStream;

/**
 * Created by admin on 10/17/2017.
 */

public class ImageDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private final String TAG = "DatabaseHelperClass";
    private static final int databaseVersion = 1;
    private static final String databaseName = "dbTest";
    private static final String TABLE_IMAGE = "ImageTable";

    // Image Table Columns names
    private static final String COL_ID = "col_id";
    private static final String IMAGE_ID = "image_id";
    private static final String IMAGE_BITMAP = "image_bitmap";

    public ImageDatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_IMAGE + "("
                + COL_ID + " INTEGER PRIMARY KEY ,"
                + IMAGE_ID + " TEXT,"
                + IMAGE_BITMAP + " TEXT )";
        sqLiteDatabase.execSQL(CREATE_IMAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);
        onCreate(sqLiteDatabase);
    }

    public void insetImage(Drawable dbDrawable, String imageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IMAGE_ID, imageId);
        Bitmap bitmap = ((BitmapDrawable)dbDrawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        values.put(IMAGE_BITMAP, stream.toByteArray());
        db.insert(TABLE_IMAGE, null, values);
        db.close();
    }

    public ImageHelperBean getImage(String imageId) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor2 = db.query(TABLE_IMAGE, new String[] {COL_ID, IMAGE_ID, IMAGE_BITMAP},IMAGE_ID +" LIKE '"+imageId+"%'", null, null, null, null);
        ImageHelperBean imageHelper = new ImageHelperBean();

        if (cursor2.moveToFirst()) {
            while (cursor2.moveToNext()){
                imageHelper.setImageId(cursor2.getString(1));
                imageHelper.setImageByteArray(cursor2.getBlob(2));
            }
        }

        cursor2.close();
        db.close();
        return imageHelper;
    }

}