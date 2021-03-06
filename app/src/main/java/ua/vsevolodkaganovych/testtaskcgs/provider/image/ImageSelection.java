package ua.vsevolodkaganovych.testtaskcgs.provider.image;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import ua.vsevolodkaganovych.testtaskcgs.provider.base.AbstractSelection;

/**
 * Selection for the {@code image} table.
 */
public class ImageSelection extends AbstractSelection<ImageSelection> {
    @Override
    public Uri uri() {
        return ImageColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code ImageCursor} object, which is positioned before the first entry, or null.
     */
    public ImageCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new ImageCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public ImageCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public ImageCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public ImageSelection id(long... value) {
        addEquals(ImageColumns._ID, toObjectArray(value));
        return this;
    }


    public ImageSelection image(String... value) {
        addEquals(ImageColumns.IMAGE, value);
        return this;
    }

    public ImageSelection imageNot(String... value) {
        addNotEquals(ImageColumns.IMAGE, value);
        return this;
    }

    public ImageSelection imageLike(String... value) {
        addLike(ImageColumns.IMAGE, value);
        return this;
    }
}
