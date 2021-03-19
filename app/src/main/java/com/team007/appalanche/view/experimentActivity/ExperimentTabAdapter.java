package com.team007.appalanche.view.experimentActivity;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.team007.appalanche.R;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ExperimentTabAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.overview_text,
            R.string.trials_Text, R.string.question_Text};
    private final Context mContext;

    public ExperimentTabAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a SubscribedFragment (defined as a static inner class below).
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = TrialsFragment.newInstance(0);
                break;
            case 2:
                fragment = QuestionFragment.newInstance(0);
                break;
            default:
                fragment = OverviewFragment.newInstance(0);
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}