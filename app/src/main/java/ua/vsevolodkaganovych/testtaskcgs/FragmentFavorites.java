package ua.vsevolodkaganovych.testtaskcgs;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.vsevolodkaganovych.testtaskcgs.provider.image.ImageColumns;


public class FragmentFavorites extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private CustomCursorAdapter mAdapter;

    public static final int URL_LOADER = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_favorites_view, container, false);

        getLoaderManager().initLoader(URL_LOADER, null, this);

        String[] projection = {ImageColumns._ID, ImageColumns.IMAGE};

        Cursor cursor = getActivity().getContentResolver().query(ImageColumns.CONTENT_URI, projection, null, null, null);

        cursor.setNotificationUri(getActivity().getContentResolver(), ImageColumns.CONTENT_URI);
        getActivity().getContentResolver().notifyChange(ImageColumns.CONTENT_URI, null);

        mAdapter = new CustomCursorAdapter(getActivity(), cursor, 0);
        setListAdapter(mAdapter);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {ImageColumns._ID, ImageColumns.IMAGE};
        CursorLoader cursorLoader = new CursorLoader(getActivity().getApplicationContext(), ImageColumns.CONTENT_URI, projection,
                null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.changeCursor(null);
    }
}
