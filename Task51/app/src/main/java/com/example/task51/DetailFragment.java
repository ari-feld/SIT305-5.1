package com.example.task51;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task51.database.AppDatabase;
import com.example.task51.database.BookmarkEntity;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {

    ImageView image;
    TextView title, desc;
    Button bookmarkButton;
    RecyclerView relatedRecycler;

    boolean isBookmarked = false;

    AppDatabase db;

    String currentTitle;
    String currentDesc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        image = view.findViewById(R.id.detailImage);
        title = view.findViewById(R.id.detailTitle);
        desc = view.findViewById(R.id.detailDesc);
        bookmarkButton = view.findViewById(R.id.bookmarkButton);
        relatedRecycler = view.findViewById(R.id.relatedRecycler);

        db = AppDatabase.getInstance(getContext());

        // Get data from bundle
        Bundle bundle = getArguments();

        if (bundle != null) {
            currentTitle = bundle.getString("title");
            currentDesc = bundle.getString("desc");

            title.setText(currentTitle);
            desc.setText(currentDesc);
            image.setImageResource(bundle.getInt("image"));
        }

        // CHECK if already bookmarked
        checkBookmarkState();

        // TOGGLE BOOKMARK BUTTON
        bookmarkButton.setOnClickListener(v -> toggleBookmark());

        // RELATED STORIES
        relatedRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        List<NewsItem> relatedList = getDummyRelated();

        NewsAdapter adapter = new NewsAdapter(relatedList, item -> {

            Bundle newbundle = new Bundle();
            newbundle.putString("title", item.title);
            newbundle.putString("desc", item.description);
            newbundle.putInt("image", item.imageResId);

            Navigation.findNavController(requireView())
                    .navigate(R.id.detailFragment, newbundle);
        });

        relatedRecycler.setAdapter(adapter);

        return view;
    }

    // -------------------------------
    // CHECK IF BOOKMARK EXISTS
    // -------------------------------
    private void checkBookmarkState() {

        new Thread(() -> {

            BookmarkEntity existing = db.bookmarkDao()
                    .findBookmark(currentTitle, currentDesc);

            requireActivity().runOnUiThread(() -> {

                isBookmarked = (existing != null);
                updateButtonText();
            });

        }).start();
    }

    // -------------------------------
    // TOGGLE BOOKMARK
    // -------------------------------
    private void toggleBookmark() {

        new Thread(() -> {

            if (!isBookmarked) {

                BookmarkEntity item = new BookmarkEntity(
                        currentTitle,
                        currentDesc,
                        R.drawable.ic_launcher_foreground,
                        "General"
                );

                db.bookmarkDao().insert(item);
                isBookmarked = true;

                requireActivity().runOnUiThread(() -> {
                    updateButtonText();
                    Toast.makeText(getContext(), "Bookmarked!", Toast.LENGTH_SHORT).show();
                });

            } else {

                db.bookmarkDao().deleteByContent(currentTitle, currentDesc);
                isBookmarked = false;

                requireActivity().runOnUiThread(() -> {
                    updateButtonText();
                    Toast.makeText(getContext(), "Removed Bookmark", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    // -------------------------------
    // UPDATE BUTTON UI
    // -------------------------------
    private void updateButtonText() {
        bookmarkButton.setText(isBookmarked ? "Remove Bookmark" : "Bookmark");
    }

    // -------------------------------
    // RELATED STORIES
    // -------------------------------
    private List<NewsItem> getDummyRelated() {
        List<NewsItem> list = new ArrayList<>();

        list.add(new NewsItem("Related 1",
                "More details here",
                android.R.drawable.ic_menu_gallery,
                "Football"));

        list.add(new NewsItem("Related 2",
                "Another story",
                android.R.drawable.ic_menu_gallery,
                "Basketball"));

        return list;
    }
}