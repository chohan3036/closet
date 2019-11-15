package com.example.closet;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.closet.History.History;
import com.example.closet.Match.Match;
import com.example.closet.Recommend.Recommend;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(androidx.fragment.app.FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        switch(position)
        {
            case 0:
            return new Home();
            case 1:
            return new Match();
            case 2:
            return new History();
            case 3:
            return new Recommend();
            default:
            return null;
        }
    }

    @Override
    public int getCount()
    {
        return 4;
    }
}
