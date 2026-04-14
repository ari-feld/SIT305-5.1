package com.example.task51;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.task51.database.AppDatabase;
import com.example.task51.database.BookmarkEntity;

import java.util.List;

public class BookmarkFragment extends Fragment {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);

        recyclerView = view.findViewById(R.id.bookmarkRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadBookmarks(view);

        return view;
    }

    private void loadBookmarks(View view) {

        AppDatabase db = AppDatabase.getInstance(getContext());

        new Thread(() -> {

            List<BookmarkEntity> list = db.bookmarkDao().getAll();

            requireActivity().runOnUiThread(() -> {

                BookmarkAdapter adapter = new BookmarkAdapter(list, item -> {

                    Bundle bundle = new Bundle();
                    bundle.putString("title", item.title);
                    bundle.putString("desc", item.description);
                    bundle.putInt("image", item.imageRes);

                    Navigation.findNavController(view)
                            .navigate(R.id.detailFragment, bundle);
                });

                recyclerView.setAdapter(adapter);
            });

        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBookmarks(requireView());
    }
}