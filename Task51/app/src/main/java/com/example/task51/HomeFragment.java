package com.example.task51;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView featuredRecycler, newsRecycler;
    EditText searchBar;

    NewsAdapter featuredAdapter;
    NewsAdapter newsAdapter;

    List<NewsItem> fullList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        featuredRecycler = view.findViewById(R.id.featuredRecycler);
        newsRecycler = view.findViewById(R.id.newsRecycler);
        searchBar = view.findViewById(R.id.searchBar);

        // Layout managers
        featuredRecycler.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        newsRecycler.setLayoutManager(
                new LinearLayoutManager(getContext())
        );

        // DATA
        fullList = getDummyData();

        // FEATURED ADAPTER
        featuredAdapter = new NewsAdapter(fullList, item -> {

            Bundle bundle = new Bundle();
            bundle.putString("title", item.title);
            bundle.putString("desc", item.description);
            bundle.putInt("image", item.imageResId);

            Navigation.findNavController(view)
                    .navigate(R.id.detailFragment, bundle);
        });

        // LATEST ADAPTER
        newsAdapter = new NewsAdapter(fullList, item -> {

            Bundle bundle = new Bundle();
            bundle.putString("title", item.title);
            bundle.putString("desc", item.description);
            bundle.putInt("image", item.imageResId);

            Navigation.findNavController(view)
                    .navigate(R.id.detailFragment, bundle);
        });

        featuredRecycler.setAdapter(featuredAdapter);
        newsRecycler.setAdapter(newsAdapter);

        // SEARCH FUNCTION
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString().toLowerCase();

                List<NewsItem> filtered = new ArrayList<>();

                for (NewsItem item : fullList) {
                    if (item.title.toLowerCase().contains(text)
                            || item.category.toLowerCase().contains(text)) {
                        filtered.add(item);
                    }
                }

                featuredAdapter.updateList(filtered);
                newsAdapter.updateList(filtered);
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private List<NewsItem> getDummyData() {
        List<NewsItem> list = new ArrayList<>();

        list.add(new NewsItem("Football Final",
                "Exciting match ends in draw",
                android.R.drawable.ic_menu_gallery,
                "Football"));

        list.add(new NewsItem("Basketball Win",
                "Team secures victory",
                android.R.drawable.ic_menu_gallery,
                "Basketball"));

        list.add(new NewsItem("Cricket Update",
                "New record achieved",
                android.R.drawable.ic_menu_gallery,
                "Cricket"));

        return list;
    }
}