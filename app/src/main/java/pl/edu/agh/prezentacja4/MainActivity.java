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

//https://developer.android.com/guide/topics/providers/content-provider-basics
//ewentualnie https://developer.android.com/guide/components/loaders ale raczej starczy czytac todo

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity { //todo implements LoaderManager.LoaderCallbacks<Cursor> {

    private String[] mColumnProjection = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            //todo dodac kolumnę HAS_PHONE_NUMBER
            };

    String mSelectionClause = ""; //todo nazwa kolumny (enum wyżej) + zapytanie LIKE sql
    String[] mSelectionArgs = {""}; //todo argument dla LIKE
    String mSortOrder = ""; //todo nazwa kolumny i sortowanie ASC/DESC

    private boolean firstTimeLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loadDataButton = findViewById(R.id.loadDataButton);
        loadDataButton.setOnClickListener(this::loadData);

        //todo odkomentować to - jest to runtimowe zapytanie o uprawnienia od marshmallowa. Dodatkowo uncomment w xmlu
        //requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
    }

    private void loadData(View view) {
        if(firstTimeLoaded){
            //todo init loadera
            firstTimeLoaded = true;
        }else{
            //todo restart loadera
        }
    }

    @NonNull
    //@Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        //todo zwrocic instancje cursorLoadera z przekazanymi polami
        return null;
    }

    //@Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        TableLayout contactsTableLayout = findViewById(R.id.contactsTableLayout);
        contactsTableLayout.removeAllViews();

        if (cursor != null && cursor.getCount() > 0) {
            //todo przesunac kursor do poczatku

            do {
                addTwoColumnRow(/*pobrac informacje z 1 kolumny*/"", /*i z drugiej*/"", contactsTableLayout);
            } while (false/*todo przejsc do kolejnego elementu kursora*/);
        } else {
            TableRow tableRow = addTableRow(contactsTableLayout);
            addTextView("ADD CONTACTS IN YOUR EMULATOR TO VIEW", tableRow);
        }
    }

    //@Override
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
