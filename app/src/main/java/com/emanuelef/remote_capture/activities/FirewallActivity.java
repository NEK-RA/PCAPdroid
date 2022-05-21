/*
 * This file is part of PCAPdroid.
 *
 * PCAPdroid is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PCAPdroid is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PCAPdroid.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2020-22 - Emanuele Faranda
 */

package com.emanuelef.remote_capture.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.emanuelef.remote_capture.R;
import com.emanuelef.remote_capture.fragments.EditListFragment;
import com.emanuelef.remote_capture.fragments.FirewallStatus;
import com.emanuelef.remote_capture.model.ListInfo;
import com.google.android.material.tabs.TabLayoutMediator;

public class FirewallActivity extends BaseActivity {
    private static final String TAG = "Firewall";
    private ViewPager2 mPager;

    private static final int POS_STATUS = 0;
    private static final int POS_BLOCKLIST = 1;
    private static final int TOTAL_COUNT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.firewall);
        setContentView(R.layout.tabs_activity);

        mPager = findViewById(R.id.pager);
        setupTabs();
    }

    private static class StateAdapter extends FragmentStateAdapter {
        StateAdapter(final FragmentActivity fa) { super(fa); }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Log.d(TAG, "createFragment");

            switch (position) {
                default: // Deliberate fall-through to status tab
                case POS_STATUS:
                    return new FirewallStatus();
                case POS_BLOCKLIST:
                    return EditListFragment.newInstance(ListInfo.Type.BLOCKLIST);
            }
        }

        @Override
        public int getItemCount() {  return TOTAL_COUNT;  }

        public int getPageTitle(final int position) {
            switch (position) {
                default: // Deliberate fall-through to status tab
                case POS_STATUS:
                    return R.string.status;
                case POS_BLOCKLIST:
                    return R.string.blocklist;
            }
        }
    }

    private void setupTabs() {
        final StateAdapter stateAdapter = new StateAdapter(this);
        mPager.setAdapter(stateAdapter);

        new TabLayoutMediator(findViewById(R.id.tablayout), mPager, (tab, position) ->
                tab.setText(getString(stateAdapter.getPageTitle(position)))
        ).attach();

        // TODO fix DPAD navigation on Android TV, see MainActivity.onKeyDown
    }
}
