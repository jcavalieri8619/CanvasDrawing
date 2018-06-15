package com.bignerdranch.android.draganddraw;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrawAndDrawFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrawAndDrawFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BoxDrawingView mBoxDrawingView;


    public DrawAndDrawFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrawAndDrawFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrawAndDrawFragment newInstance(String param1, String param2) {
        DrawAndDrawFragment fragment = new DrawAndDrawFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_drag_and_draw, menu);


    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_circle:
                mBoxDrawingView.setDrawType(BoxDrawingView.DrawType.CIRCLE);

                break;
            case R.id.menu_boxes:
                mBoxDrawingView.setDrawType(BoxDrawingView.DrawType.BOX);

                break;
            case R.id.menu_point:
                mBoxDrawingView.setDrawType(BoxDrawingView.DrawType.POINT);

                break;
            case R.id.menu_clear:
                mBoxDrawingView.clearCanvas();
                Toast.makeText(getContext(), "Clearing Canvas", Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu_line:
                mBoxDrawingView.setDrawType(BoxDrawingView.DrawType.LINE);

                break;

            default:

                return super.onOptionsItemSelected(item);


        }
        return true;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drag_and_draw, container, false);

        mBoxDrawingView = v.findViewById(R.id.boxdrawing_view);

        return v;
    }

}
