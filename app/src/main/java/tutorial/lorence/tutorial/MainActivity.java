package tutorial.lorence.tutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().setTitle(this.getString(R.string.title));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_search:
                return true;
            case R.id.ic_more:
                PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.ic_more));
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
                return true;
            case R.id.ic_genericMethodJava:
                return true;
            case R.id.ic_genericInterfaceAndroid:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
