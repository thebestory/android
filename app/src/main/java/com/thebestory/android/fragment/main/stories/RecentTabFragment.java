package com.thebestory.android.fragment.main.stories;

import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thebestory.android.R;
import com.thebestory.android.adapter.main.StoriesFragmentForStoryAdapter;
import com.thebestory.android.loader.main.LoadResult;
import com.thebestory.android.loader.main.ResultType;
import com.thebestory.android.loader.main.StoriesData;
import com.thebestory.android.loader.main.StoriesLoader;
import com.thebestory.android.models.Story;

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
public class RecentTabFragment extends Fragment implements LoaderManager.LoaderCallbacks<LoadResult<List<Story>>> {

    private View view;
    final RecentTabFragment thisFragment = this;

    private List<Story> stories;
    private RecyclerView rv;
    private TextView errorTextView;
    private ProgressBar progressView;

    private boolean flagForLoader;
    private int currentIdStories;

    @Nullable
    private StoriesFragmentForStoryAdapter adapter;
    private StoriesData storiesData;

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

        FragmentManager fm = getFragmentManager();

        progressView = (ProgressBar) view.findViewById(R.id.progress);
        errorTextView = (TextView) view.findViewById(R.id.error_text);

        adapter = new StoriesFragmentForStoryAdapter(getActivity());
        storiesData = (StoriesData) fm.findFragmentByTag(StoriesData.TAG);


        rv = (RecyclerView) view.findViewById(R.id.rv_stories_recent_tab);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //rv.addItemDecoration(new RecylcerDividersDecorator(R.color.colorPrimaryDark));
        rv.setAdapter(adapter);

        if (storiesData == null) {
            storiesData = new StoriesData();
            fm.beginTransaction().add(storiesData, StoriesData.TAG).commit();
        }

        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);

        if (savedInstanceState != null) {
            currentIdStories = savedInstanceState.getInt("currentIdStories");
            displayNonEmptyData(storiesData.getCurrentStories());
        } else {
            currentIdStories = 0;
            flagForLoader = true;
            getLoaderManager().initLoader(currentIdStories, null, this);
        }

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (flagForLoader) {
                    return;
                }
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (llm.findLastVisibleItemPosition() + 3 >= adapter.getItemCount()) {
                    flagForLoader = true;
                    getLoaderManager().initLoader(++currentIdStories, null, thisFragment);
                }
            }
        });

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


    private void debugInitializeData() {
        stories = new ArrayList<>();
        stories.add(new Story(1, 56, 5,
                "Самое классное для меня в путешествиях - это снять квартиру вне туристического центра, обедать не в дорогих ресторанах, а в маленьких закусочных, которые держат какие-нибудь милые пенсионеры, гулять не по центральным улицам, а по неизведанным дворам, где кипит настоящая местная жизнь.\n" +
                        "Памятники и музеи - это прекрасно, но так жаль, что многие туристы и не догадываются, сколько еще интересного скрывают чужие города."));
        stories.add(new Story(1, 56, 5,
                "Самое классное для меня в путешествиях - это снять квартиру вне туристического центра, обедать не в дорогих ресторанах, а в маленьких закусочных, которые держат какие-нибудь милые пенсионеры, гулять не по центральным улицам, а по неизведанным дворам, где кипит настоящая местная жизнь.\n" +
                        "Памятники и музеи - это прекрасно, но так жаль, что многие туристы и не догадываются, сколько еще интересного скрывают чужие города."));
        stories.add(new Story(1, 56, 5,
                "Самое классное для меня в путешествиях - это снять квартиру вне туристического центра, обедать не в дорогих ресторанах, а в маленьких закусочных, которые держат какие-нибудь милые пенсионеры, гулять не по центральным улицам, а по неизведанным дворам, где кипит настоящая местная жизнь.\n" +
                        "Памятники и музеи - это прекрасно, но так жаль, что многие туристы и не догадываются, сколько еще интересного скрывают чужие города."));
        stories.add(new Story(1, 56, 5,
                "Самое классное для меня в путешествиях - это снять квартиру вне туристического центра, обедать не в дорогих ресторанах, а в маленьких закусочных, которые держат какие-нибудь милые пенсионеры, гулять не по центральным улицам, а по неизведанным дворам, где кипит настоящая местная жизнь.\n" +
                        "Памятники и музеи - это прекрасно, но так жаль, что многие туристы и не догадываются, сколько еще интересного скрывают чужие города."));
        /*stories.add(new Story("Funny", 56,
                "Самое классное для меня в путешествиях - это снять квартиру вне туристического центра, обедать не в дорогих ресторанах, а в маленьких закусочных, которые держат какие-нибудь милые пенсионеры, гулять не по центральным улицам, а по неизведанным дворам, где кипит настоящая местная жизнь.\n" +
                        "Памятники и музеи - это прекрасно, но так жаль, что многие туристы и не догадываются, сколько еще интересного скрывают чужие города.", 120, "4 hours ago"));
*/
    }


    @Override
    public Loader<LoadResult<List<Story>>> onCreateLoader(int id, Bundle args) {
        return new StoriesLoader(getActivity(), this.currentIdStories);
    }

    @Override
    public void onLoadFinished(Loader<LoadResult<List<Story>>> loader, LoadResult<List<Story>> result) {
        flagForLoader = false;
        if (result.resultType == ResultType.OK) {
            if (result.data != null && !result.data.isEmpty()) {
                displayNonEmptyData(result.data);
                storiesData.getCurrentStories().addAll(result.data);
            } else {
                displayEmptyData();
            }
        } else {
            displayError(result.resultType);
        }
    }

    @Override
    public void onLoaderReset(Loader<LoadResult<List<Story>>> loader) {
        displayEmptyData();
    }


    private void displayEmptyData() {
        progressView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(R.string.stories_not_found);
    }

    private void displayNonEmptyData(List<Story> stories) {
        if (adapter != null) {
            adapter.addStories(stories);
        }
        progressView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
    }

    private void displayError(ResultType resultType) {
        progressView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        final int messageResId;
        if (resultType == ResultType.NO_INTERNET) {
            messageResId = R.string.no_internet;
        } else {
            messageResId = R.string.error;
        }
        errorTextView.setText(messageResId);
    }

    private void initializeAdapter() {
        //StoriesFragmentForStoryAdapter adapter = new StoriesFragmentForStoryAdapter(stories);
        //rv.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentIdStories", currentIdStories);
    }

}
