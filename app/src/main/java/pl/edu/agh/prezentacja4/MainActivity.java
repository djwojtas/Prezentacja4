package pl.edu.agh.prezentacja4;

import android.Manifest;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private String[] mColumnProjection = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.HAS_PHONE_NUMBER};

    String mSelectionClause = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?";
    String[] mSelectionArgs = {"%a%"};
    String mSortOrder = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " ASC";

    private boolean firstTimeLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loadDataButton = findViewById(R.id.loadDataButton);
        loadDataButton.setOnClickListener(this::loadData);

        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
    }

    private void loadData(View view) {
        if(firstTimeLoaded){
            getSupportLoaderManager().initLoader(0, null, this);
            firstTimeLoaded = true;
        }else{
            getSupportLoaderManager().restartLoader(0, null, this);
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(MainActivity.this, ContactsContract.Contacts.CONTENT_URI,
                mColumnProjection,
                mSelectionClause,
                mSelectionArgs,
                mSortOrder);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        TableLayout contactsTableLayout = findViewById(R.id.contactsTableLayout);
        contactsTableLayout.removeAllViews();

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                addTwoColumnRow(cursor.getString(0), cursor.getString(1), contactsTableLayout);
            } while (cursor.moveToNext());
        } else {
            TableRow tableRow = addTableRow(contactsTableLayout);
            addTextView("ADD CONTACTS IN YOUR EMULATOR TO VIEW", tableRow);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {}

    private void addTwoColumnRow(String firstColumn, String secondColumn, ViewGroup parent) {
        TableRow tableRow = addTableRow(parent);
        addTextView(firstColumn, tableRow);
        addTextView(secondColumn, tableRow);
    }

    private TableRow addTableRow(ViewGroup parent) {
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT));

        parent.addView(tableRow);
        return tableRow;
    }

    private TextView addTextView(String text, ViewGroup parent) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setText(text);

        parent.addView(textView);
        return textView;
    }
}
