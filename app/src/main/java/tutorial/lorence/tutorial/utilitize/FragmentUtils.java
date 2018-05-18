package tutorial.lorence.tutorial.utilitize;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.Stack;

import tutorial.lorence.tutorial.MainActivity;
import tutorial.lorence.tutorial.R;

public class FragmentUtils {
    private Context mContext;
    private Stack<FragmentStack> mCurrentFrgStack;
    private int mContainerId;

    public FragmentUtils(Context context, Stack<FragmentStack> currentFrgStack, @IdRes int containerId) {
        this.mContext = context;
        this.mCurrentFrgStack = currentFrgStack;
        this.mContainerId = containerId;
    }

    public void peekFragment() {
        try {
            FragmentStack fragment = mCurrentFrgStack.peek();
            FragmentManager manager = ((MainActivity) mContext).getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            ft.replace(mContainerId, fragment.getFragment(), fragment.getTag());
            ft.commitAllowingStateLoss();
        } catch (IllegalStateException | ArrayIndexOutOfBoundsException e) {
        }
    }

    public void popFragment() {
        mCurrentFrgStack.pop();
        peekFragment();
    }

    public void pushFragment(PushFrgType type, Fragment fragment, String tag, boolean shouldAdd) {
        try {
            FragmentManager manager = ((MainActivity) mContext).getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            if (type == PushFrgType.REPLACE) {
                ft.replace(mContainerId, fragment, tag);
                ft.addToBackStack(tag);
                ft.commitAllowingStateLoss();
            } else if (type == PushFrgType.ADD) {
                ft.add(mContainerId, fragment, tag);
                ft.disallowAddToBackStack();
                ft.commit();
            }
            manager.executePendingTransactions();
        } catch (IllegalStateException e) {
        }
        if (shouldAdd) {
            mCurrentFrgStack.add(new FragmentStack(fragment, tag));
        }
    }

    public enum PushFrgType {
        REPLACE, ADD
    }
}
