package com.peacecorps.pcsa.policies_glossary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.peacecorps.pcsa.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Glossary
 * @author rohan
 * @since 2016-07-29
 */
public class GlossaryFragment extends Fragment {

    public static final String TAG = GlossaryFragment.class.getSimpleName();
    List<String> listDataHeader = new ArrayList<>();
    EditText searchBox;
    HashMap<String, List<String>> listDataChild = new HashMap<>();
    GlossaryAdapter listAdapter;
    int currentExpanded = -1;

    /**
     * Create the view for this fragment, using the arguments given to it.
     *
     * @param inflater inflate any views in the fragment
     * @param container if non-null, this is the parent view that the fragment's UI should be attached to. .
     * @param savedInstanceState if non-null, this fragment is being re-constructed from a previous saved state
     * @return the properly constructed view object
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_glossary,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.policies_glossary);
        //Get the listview
        final ExpandableListView expListView = (ExpandableListView) rootView.findViewById(R.id.list_words);
        //Prepare list data
        GlossaryAdapter.prepareListData(getActivity(),listDataHeader,listDataChild);
        listAdapter = new GlossaryAdapter(getActivity(), listDataHeader, listDataChild,expListView);
        //Setting list adapter
        expListView.setAdapter(listAdapter);
        searchBox = (EditText)rootView.findViewById(R.id.inputSearch);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            //Instantiated when user changes the text present in edit text.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("INPUT",s.toString());
                listAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //Callback method to be invoked when a group in this expandable list been expanded
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if( currentExpanded >= 0 && currentExpanded != groupPosition)
                    expListView.collapseGroup(currentExpanded);
                currentExpanded = groupPosition;
            }
        });
        return rootView;
    }
}
