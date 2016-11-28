package com.mashell.one.module.home.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.mashell.one.R;
import com.mashell.one.base.BaseFragment;
import com.mashell.one.module.home.contract.HomeContract;
import com.mashell.one.module.home.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;

/**
 * Created by mashell on 16/11/14.
 * Email: mashell624@163.com
 * Github: https://github.com/mashell
 */

public class HomeFragment extends BaseFragment<HomeContract.IHomePresenter> implements HomeContract.IHomeView {
    @BindView(R.id.fragmentViewPager)
    ViewPager fragmentViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initListFragment(List<String> idList) {
        LeftMostFragment leftMostFragment = new LeftMostFragment();
        RightMostFragment rightMostFragment = new RightMostFragment();

        final List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(leftMostFragment);

        for (int i = 0; i < idList.size(); i++) {
            fragmentList.add(InnerHomeFragment.getInstance(idList.get(i)));
        }
        fragmentList.add(rightMostFragment);

        MyFragmentAdapter adapter = new MyFragmentAdapter(getChildFragmentManager(), fragmentList);
        fragmentViewPager.setAdapter(adapter);
        fragmentViewPager.setCurrentItem(1);
        fragmentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    fragmentViewPager.setCurrentItem(position + 1);
                    mvpPresenter.getIdList();
                    Toast.makeText(getActivity(),"正在刷新",Toast.LENGTH_SHORT).show();
                } else if (position == fragmentList.size() - 1) {
                    fragmentViewPager.setCurrentItem(position - 1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initView() {
        mvpPresenter.getIdList();
    }

    @Override
    public <V> Observable.Transformer<V, V> bind() {
        return this.bindToLifecycle();
    }

    @Override
    public HomeContract.IHomePresenter createMvpPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_home;
    }


    class MyFragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}