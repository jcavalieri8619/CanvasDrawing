package com.bignerdranch.android.draganddraw;

import android.support.v4.app.Fragment;

public class DragAndDrawActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        String param1 = "";
        String param2 = "";
        return DrawAndDrawFragment.newInstance(param1, param2);
    }


}
