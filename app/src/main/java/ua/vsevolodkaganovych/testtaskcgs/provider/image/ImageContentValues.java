package ua.vsevolodkaganovych.testtaskcgs.provider.image;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import ua.vsevolodkaganovych.testtaskcgs.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code image} table.
 */
public class ImageContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ImageColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, ImageSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public ImageContentValues putImage(String value) {
        mContentValues.put(ImageColumns.IMAGE, value);
        return this;
    }

    public ImageContentValues putImageNull() {
        mContentValues.putNull(ImageColumns.IMAGE);
        return this;
    }

}
