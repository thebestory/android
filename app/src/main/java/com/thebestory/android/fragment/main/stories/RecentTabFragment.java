package com.thebestory.android.fragment.main.stories;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thebestory.android.R;
import com.thebestory.android.Story;
import com.thebestory.android.adapter.main.StoriesFragmentForStoryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecentTabFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecentTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecentTabFragment extends Fragment {
    private View view;
    private List<Story> stories;
    private RecyclerView rv;

    private OnFragmentInteractionListener mListener;

    public RecentTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecentTabFragment.
     */
    public static RecentTabFragment newInstance() {
        return new RecentTabFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_stories_recent_tab, container, false);

        rv = (RecyclerView) view.findViewById(R.id.rv);

        //rv.setHasFixedSize(true); if rv don't change

        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(llm);

        StoriesFragmentForStoryAdapter adapter = new StoriesFragmentForStoryAdapter(stories);
        rv.setAdapter(adapter);

        initializeData();
        initializeAdapter();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void initializeData() {
        stories = new ArrayList<>();
        stories.add(new Story("Funny", 56,
                "Самое классное для меня в путешествиях - это снять квартиру вне туристического центра, обедать не в дорогих ресторанах, а в маленьких закусочных, которые держат какие-нибудь милые пенсионеры, гулять не по центральным улицам, а по неизведанным дворам, где кипит настоящая местная жизнь.\n" +
                        "Памятники и музеи - это прекрасно, но так жаль, что многие туристы и не догадываются, сколько еще интересного скрывают чужие города.", 120, "4 hours ago"));
        stories.add(new Story("Funny", 56,
                "Самое классное для меня в путешествиях - это снять квартиру вне туристического центра, обедать не в дорогих ресторанах, а в маленьких закусочных, которые держат какие-нибудь милые пенсионеры, гулять не по центральным улицам, а по неизведанным дворам, где кипит настоящая местная жизнь.\n" +
                        "Памятники и музеи - это прекрасно, но так жаль, что многие туристы и не догадываются, сколько еще интересного скрывают чужие города.", 120, "4 hours ago"));

    }

    private void initializeAdapter(){
        StoriesFragmentForStoryAdapter adapter = new StoriesFragmentForStoryAdapter(stories);
        rv.setAdapter(adapter);
    }
}
