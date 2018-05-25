# Generic-Interface-Android
- We have two options for now:
Notice: aa1b1c1_b_c
=> Currently, we had three fragments A, B and C.
+ Fragment A: Replace => Using as my mind.
+ Fragment B: Override fragment A when you click
+ Fragment C: Override fragment B or A when you click
=> Exist only one fragment in Activity -> Back -> exist activity

- Enhance level 1: Assume, You want to add new fragment A1 on fragment A. How?
```java
FragmentManager manager = ((View1) mContext).getSupportFragmentManager();
    FragmentTransaction ft = manager.beginTransaction();
    ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
    if (type == PushFrgType.REPLACE) {
        ft.replace(mContainerId, fragment, tag);
        ft.addToBackStack(tag);
        ft.commitAllowingStateLoss();
    } else if (type == PushFrgType.ADD) {
        ft.add(mContainerId, fragment, tag);
        ft.disallowAddToBackStack();
        ft.commit();
    }
```
- We use getSupportFragmentManager(). All of fragments will be managed on its activity. => If you add
a new fragment on old fragment. Action back of user will be updated directly on its activity.
- As you add new Fragment => Syntax: <Activity>->push(Fragment, ADD)
+ If we add more fragment and just use keyword "REPLACE" => It doesn't matter
+ If we add more fragment and just use keyword "ADD" => You should create Stack to manage these fragemnt
when you add.
+ If we use Stack. Please notice to make sure that you already avoiding duplicate fragment.
+ Summary: We have all cases here:
## Case 1: Using Stack(true) + Replace
```java
public void pushFragment(PushFrgType type, Fragment fragment, String tag, boolean shouldAdd) {
    try {
        FragmentManager manager = ((View1) mContext).getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
        if (type == PushFrgType.REPLACE) {
            ft.replace(mContainerId, fragment, tag);
            ft.addToBackStack(tag);
            ft.commitAllowingStateLoss();
        } else if (type == PushFrgType.ADD) {
            ft.add(mContainerId, fragment, tag);
            ft.disallowAddToBackStack();
    //                ft.addToBackStack(tag);
            ft.commit();
        }
        manager.executePendingTransactions();
    } catch (IllegalStateException e) {
    }
    if (shouldAdd) {
        mCurrentFrgStack.add(new FragmentStack(fragment, tag));
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
    super.onBackPressed();
}
Enter: A, B, C, D, C => C, D, C, B, A => Check duplicate when replace fragment. By the way, remove old fragment.
```

## Case 2: Using Stack(true) + ADD
We also got the same result. Because, onBackPress is impacted by mCurrentFrgStack
## Case 3: Using Stack(false) + REPLACE
- mCurrentFrgStack is always equal  1.
- Input: A => B(A) => C(B) => D(C)
## Case 4: Using Stack(false) + ADD
- The same with case 3 because mCurrentFrgStack.
## Case 5: Custom method onBackPress() + Stack(true) + REPLACE
```java
//        if (mCurrentFrgStack.size() > 1) {
//            mFragmentUtils.popFragment();
//            defineToolbar(mCurrentFrgStack.peek().getTag());
//        } else {
//            finish();
//        }
        super.onBackPressed();
```
- Input A, B, C, C, D => D, C, C, B, A, Blank
## Case 6: Custom method onBackPress() + Stack(false) + REPLACE
- The same with case 5
## Case 7: Stack(true) + REPLACE + disableowAddToBackStack();
```java
public void pushFragment(PushFrgType type, Fragment fragment, String tag, boolean shouldAdd) {
        try {
            FragmentManager manager = ((View1) mContext).getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
            if (type == PushFrgType.REPLACE) {
                ft.replace(mContainerId, fragment, tag);
//                ft.addToBackStack(tag);
                ft.disallowAddToBackStack();
                ft.commitAllowingStateLoss();
            } else if (type == PushFrgType.ADD) {
                ft.add(mContainerId, fragment, tag);
                ft.disallowAddToBackStack();
//                ft.addToBackStack(tag);
                ft.commit();
            }
            manager.executePendingTransactions();
        } catch (IllegalStateException e) {
        }
        if (shouldAdd) {
            mCurrentFrgStack.add(new FragmentStack(fragment, tag));
        }
    }
```
- Input: A, B, C, => C(No Blank)
## Case 8: Stack(false) + REPLACE + disableowAddToBackStack();
- The same with case 7
## Case 9: We need to distinguish between addToBackStack() and disallowAddToBackStack()
- addToBackStack() is as Stack. We use it to manage fragment. But, we can't handle duplicate fragment between
=> We create variable mCurrentFrgStack to manage it. It's done for now.
- disallowAddToBackStack() => It means when you back => It will go to parent. No need to stay here.


