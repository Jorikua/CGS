package ua.vsevolodkaganovych.testtaskcgs.provider.image;

import java.util.Date;

import android.database.Cursor;

import ua.vsevolodkaganovych.testtaskcgs.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code image} table.
 */
public class ImageCursor extends AbstractCursor {
    public ImageCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code image} value.
     * Can be {@code null}.
     */
    public String getImage() {
        Integer index = getCachedColumnIndexOrThrow(ImageColumns.IMAGE);
        return getString(index);
    }
}
