/*
 * The Bestory Project
 */

package com.thebestory.android.fragment.main.stories;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.thebestory.android.R;
import com.thebestory.android.TheBestoryApplication;
import com.thebestory.android.adapter.main.StoriesAdapter;
import com.thebestory.android.api.urlCollection.TypeOfCollection;
import com.thebestory.android.apollo.StoriesByTopicsQuery;
import com.thebestory.android.apollo.type.StoryListingSection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public final class StoriesTabFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View view;
    private final StoriesTabFragment thisFragment = this;

    private RecyclerView rv;
    private TextView errorTextView;
    private ProgressBar progressView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean isLoadingNow = true;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nullable
    private StoriesAdapter adapter;

    private StoryListingSection section;


    public StoriesTabFragment() {
        super();
        this.section = StoryListingSection.LATEST;
    }


    public void setSection(StoryListingSection section) {
        this.section = section;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StoriesTabFragment.
     */
    public static StoriesTabFragment newInstance(StoryListingSection type) {
        StoriesTabFragment temp = new StoriesTabFragment();
        temp.setSection(type);
        return temp;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //String currentId = ((TheBestoryApplication) getActivity().getApplication()).currentIdTopic;
        //loadedStories = BankStoriesLocation.getInstance().getStoriesArray(section, currentId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new StoriesAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_stories_tab, container, false);

        progressView = (ProgressBar) view.findViewById(R.id.progress);
        errorTextView = (TextView) view.findViewById(R.id.error_text);

        rv = (RecyclerView) view.findViewById(R.id.rv_stories_tab);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(
                Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

        if (adapter != null) {
            if (adapter.getItemCount() == 0) {
                getStories(TypeOfCollection.NONE);
            } else {
                displayNonEmptyData();
                isLoadingNow = false;
            }
        }

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (isLoadingNow) {
                    return;
                }

                Log.d("StoriesTabFragment", "FOUND");

                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (adapter != null
                        && llm.findLastVisibleItemPosition() + 4 >= adapter.getItemCount()) {
                    isLoadingNow = true;
                    if (adapter.getItemCount() == 0) {
                        getStories(TypeOfCollection.NONE);
                    } else {
                        Log.d("StoriesTabFragment", "AFTER" + ", Count stories in adapter: " + adapter.getItemCount());
                        getStories(TypeOfCollection.AFTER);
                    }
                }
            }
        });

        return view;
    }

    private void getStories(TypeOfCollection t) {

        Object storyId = null;
        ApolloCall<StoriesByTopicsQuery.Data> storiesQuery = null;

        if (adapter != null) {

            List<Object> ids = new ArrayList<>();

            if (!((TheBestoryApplication) getActivity().
                    getApplication()).currentIdTopic.isEmpty()) {
                for (String e : ((TheBestoryApplication) getActivity().
                        getApplication()).currentIdTopic) {
                    ids.add((Object) e);
                }
            }

            switch (t) {
                case BEFORE: {
                    storyId = adapter.getStoryAt(0).id();
                    storiesQuery = TheBestoryApplication.getApolloClient()
                            .query(new StoriesByTopicsQuery(ids, section, storyId, null, 8));
                    break;
                }
                case NONE: {
                    storiesQuery = TheBestoryApplication.getApolloClient()
                            .query(new StoriesByTopicsQuery(ids, section, null, null, 8));
                    break;
                }
                case AFTER:
                    storyId = adapter.getStoryAt(adapter.getItemCount() - 1).id();
                    Log.d("StoriesTabFragment", storyId.toString());
                    storiesQuery = TheBestoryApplication.getApolloClient()
                            .query(new StoriesByTopicsQuery(ids, section, null, storyId, 8));
                    break;
            }
        }

        DisposableObserver<Response<StoriesByTopicsQuery.Data>> disObsForStoriesByQuery =
                getDisObsForStoriesByQuery(t);

        if (storiesQuery != null) {
            Rx2Apollo.from(storiesQuery)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(disObsForStoriesByQuery);
        }

        compositeDisposable.add(disObsForStoriesByQuery);
    }


    private DisposableObserver<Response<StoriesByTopicsQuery.Data>> getDisObsForStoriesByQuery(final TypeOfCollection t) {
        return new DisposableObserver<Response<StoriesByTopicsQuery.Data>>() {
            @Override
            public void onNext(@NonNull Response<StoriesByTopicsQuery.Data> dataResponse) {
                List<StoriesByTopicsQuery.Story> storyList = dataResponse.data().stories();

                if (adapter != null) {
                    if (storyList != null) {
                        if (storyList.isEmpty()) {
                            if (adapter.getItemCount() > 0) {
                                displayNonEmptyData();
                            } else {
                                displayEmptyData();
                            }
                        } else {
                            switch (t) {
                                case BEFORE: {
                                    adapter.addFirstStories(storyList.size(), storyList);
                                    break;
                                }
                                case NONE: {
                                    adapter.addLastStories(storyList.size(), storyList);
                                    break;
                                }
                                case AFTER: {
                                    adapter.addLastStories(storyList.size(), storyList);
                                    break;
                                }
                            }
                        }
                    }
                }

                displayNonEmptyData();
                Log.d("StoriesTabFragment", "End: " + t.toString() + " , Count stories in adapter: " + adapter.getItemCount());
                isLoadingNow = false;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("StoriesTabFragment", e.getMessage(), e);
            }

            @Override
            public void onComplete() {
                //TODO
            }
        };
    }

    @Override
    public void onRefresh() {
        if (adapter != null) {
            if (adapter.getItemCount() > 0) {
                getStories(TypeOfCollection.BEFORE);
                Log.d("StoriesTabFragment", "BEFORE" + ", Count stories in adapter: " + adapter.getItemCount());
            } else {
                getStories(TypeOfCollection.NONE);
            }
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void displayEmptyData() {
        progressView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(R.string.stories_not_found);
    }

    private void displayNonEmptyData() {
        progressView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
    }

//    private void displayError(LoaderStatus resultType) {
//        if ((adapter != null ? adapter.getItemCount() : 0) == 0) {
//            progressView.setVisibility(View.GONE);
//            rv.setVisibility(View.GONE);
//            errorTextView.setVisibility(View.VISIBLE);
//            final int messageResId;
//            if (resultType == LoaderStatus.ERROR) {
//                messageResId = R.string.no_internet;
//            } else {
//                messageResId = R.string.error;
//            }
//            errorTextView.setText(messageResId);
//        } else {
//            Snackbar.make(
//                    getActivity().findViewById(R.id.main_stories_layout),
//                    R.string.no_internet,
//                    Snackbar.LENGTH_LONG
//            ).show();
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
