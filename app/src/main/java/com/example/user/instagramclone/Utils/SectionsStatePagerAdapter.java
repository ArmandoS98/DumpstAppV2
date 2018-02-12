package com.example.user.instagramclone.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 19/01/2018.
 */

public class SectionsStatePagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final HashMap<Fragment, Integer> mFragments = new HashMap<>();
    private final HashMap<String, Integer> mFragmentsNumbers = new HashMap<>();
    private final HashMap<Integer, String> mFragmentsName = new HashMap<>();

    public SectionsStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String fragmentName){
        mFragmentList.add(fragment);
        mFragments.put(fragment, mFragmentList.size()-1);
        mFragmentsNumbers.put(fragmentName,mFragmentList.size()-1);
        mFragmentsName.put(mFragmentList.size()-1, fragmentName);
    }

    /**
     * returns the fragments with the name @param
     * @param fragmentName
     * @return
     */
    public Integer getFragmentNumber(String fragmentName){
        if (mFragmentsNumbers.containsKey(fragmentName)){
            return mFragmentsNumbers.get(fragmentName);
        }else{
            return null;
        }
    }

    /**
     * returns the fragments with the name @param
     * @param fragment
     * @return
     */
    public Integer getFragmentNumber(Fragment fragment){
        if (mFragmentsNumbers.containsKey(fragment)){
            return mFragmentsNumbers.get(fragment);
        }else{
            return null;
        }
    }

    /**
     * returns the fragments with the name @param
     * @param fragmentNumber
     * @return
     */
    public String getFragmentNumber(Integer fragmentNumber){
        if (mFragmentsName.containsKey(fragmentNumber)){
            return mFragmentsName.get(fragmentNumber);
        }else{
            return null;
        }
    }
}
