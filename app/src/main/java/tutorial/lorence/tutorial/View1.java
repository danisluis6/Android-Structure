package tutorial.lorence.tutorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

import java.util.Stack;

import tutorial.lorence.tutorial.fragment.FragmentA;
import tutorial.lorence.tutorial.fragment.FragmentB;
import tutorial.lorence.tutorial.fragment.FragmentC;
import tutorial.lorence.tutorial.fragment.FragmentD;
import tutorial.lorence.tutorial.utilitize.FragmentStack;
import tutorial.lorence.tutorial.utilitize.FragmentUtils;

public class View1 extends AppCompatActivity implements MainView {

    private Stack<FragmentStack> mCurrentFrgStack;
    private FragmentUtils mFragmentUtils;

    private FragmentA mFragmentA;
    private FragmentB mFragmentB;
    private FragmentC mFragmentC;
    private FragmentD mFragmentD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_1);
        this.getSupportActionBar().setTitle(this.getString(R.string.title));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze);

        initAttributes();
        initViews();
    }

    @Override
    public void initAttributes() {
        mCurrentFrgStack = new Stack<>();
        mFragmentUtils = new FragmentUtils(this, mCurrentFrgStack, R.id.main_activity_container);
    }

    @Override
    public void initViews() {
        mFragmentA = new FragmentA();
        pushFragment(mFragmentA, getString(R.string.TAG_FRAGMENT_A));
    }

    public void pushFragment(Fragment fragment, String tag) {
        mFragmentUtils.pushFragment(FragmentUtils.PushFrgType.REPLACE, fragment, tag, true);
        defineToolbar(tag);
    }

    private void defineToolbar(String tag) {
        // TODO
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
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.ic_a:
                                mFragmentA = new FragmentA();
                                pushFragment(mFragmentA, getString(R.string.TAG_FRAGMENT_A));
                                return true;
                            case R.id.ic_b:
                                mFragmentB = new FragmentB();
                                pushFragment(mFragmentB, getString(R.string.TAG_FRAGMENT_B));
                                return true;
                            case R.id.ic_c:
                                mFragmentC = new FragmentC();
                                pushFragment(mFragmentC, getString(R.string.TAG_FRAGMENT_C));
                                return true;
                            case R.id.ic_d:
                                mFragmentD = new FragmentD();
                                pushFragment(mFragmentD, getString(R.string.TAG_FRAGMENT_D));
                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (mCurrentFrgStack.size() > 1) {
            mFragmentUtils.popFragment();
            defineToolbar(mCurrentFrgStack.peek().getTag());
        } else {
            finish();
        }
    }
}
