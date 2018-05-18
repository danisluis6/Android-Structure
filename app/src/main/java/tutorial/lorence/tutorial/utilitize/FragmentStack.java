package tutorial.lorence.tutorial.utilitize;

import android.support.v4.app.Fragment;

public class FragmentStack {
    private final Fragment mFragment;
    private final String mTag;

    public FragmentStack(Fragment fragment, String key) {
        this.mFragment = fragment;
        mTag = key;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public String getTag() {
        return mTag;
    }
}
