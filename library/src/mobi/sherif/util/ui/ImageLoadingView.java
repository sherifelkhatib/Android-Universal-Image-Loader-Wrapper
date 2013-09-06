package mobi.sherif.util.ui;

import mobi.sherif.imageloadingview.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class ImageLoadingView extends ImageView implements ImageLoadingListener {
	/**
	 * {@linkplain DisplayImageOptions Display image options} for image displaying.
	 */
	private DisplayImageOptions mDisplayImageOptions = null;
	/**
	 * {@linkplain ImageLoadingListener Listener} for image loading process.
	 */
	private ImageLoadingListener mListener;
	private String mUrl;

	private static final Bitmap.Config BITMAP_CONFIG[] = new Bitmap.Config[] {Bitmap.Config.ALPHA_8, Bitmap.Config.ARGB_4444, Bitmap.Config.ARGB_8888, Bitmap.Config.RGB_565}; 
	public ImageLoadingView(Context context) {
		super(context);
		init(context, null);
	}
	public ImageLoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}
	public ImageLoadingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}
	private void init(Context context, AttributeSet attrs) {
		if(attrs == null) return;
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ImageLoadingView, 0, 0);
		try {
			if(a.hasValue(R.styleable.ImageLoadingView_bitmapConfig)) {
				Bitmap.Config _bitmapConfig = null;
				int bitmapConfig = a.getInteger(R.styleable.ImageLoadingView_bitmapConfig, -1);
				if(bitmapConfig == -1) {
					String bitmapConfigValue = a.getString(R.styleable.ImageLoadingView_bitmapConfig);
					if(bitmapConfigValue == null) {
						try {
							_bitmapConfig = Bitmap.Config.valueOf(bitmapConfigValue);
						} catch(Exception ex) {
							ex.printStackTrace();
						}
					}
				}
				else {
					try {
						_bitmapConfig = BITMAP_CONFIG[bitmapConfig];
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
				if(_bitmapConfig != null) {
					builder.bitmapConfig(_bitmapConfig);
				}
			}
			if(a.hasValue(R.styleable.ImageLoadingView_cacheInMemory)) {
				builder.cacheInMemory(a.getBoolean(R.styleable.ImageLoadingView_cacheInMemory, false));
			}
			if(a.hasValue(R.styleable.ImageLoadingView_cacheOnDisc)) {
				builder.cacheInMemory(a.getBoolean(R.styleable.ImageLoadingView_cacheOnDisc, false));
			}
			if(a.hasValue(R.styleable.ImageLoadingView_delayBeforeLoading)) {
				builder.delayBeforeLoading(a.getInteger(R.styleable.ImageLoadingView_delayBeforeLoading, 0));
			}
			if(a.hasValue(R.styleable.ImageLoadingView_resetViewBeforeLoading)) {
				builder.resetViewBeforeLoading(a.getBoolean(R.styleable.ImageLoadingView_resetViewBeforeLoading, false));
			}
			if(a.hasValue(R.styleable.ImageLoadingView_imageForEmptyUri)) {
				builder.showImageForEmptyUri(a.getResourceId(R.styleable.ImageLoadingView_imageForEmptyUri, 0));
			}
			if(a.hasValue(R.styleable.ImageLoadingView_imageOnFail)) {
				builder.showImageOnFail(a.getResourceId(R.styleable.ImageLoadingView_imageOnFail, 0));
			}
			if(a.hasValue(R.styleable.ImageLoadingView_imageOnLoading)) {
				builder.showStubImage(a.getResourceId(R.styleable.ImageLoadingView_imageOnLoading, 0));
			}
			if(a.hasValue(R.styleable.ImageLoadingView_imageUrl)) {
				mUrl = a.getString(R.styleable.ImageLoadingView_imageUrl);
			}
			mDisplayImageOptions = builder.build();
		} finally {
			a.recycle();
		}
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if(mUrl != null) {
			setImage(mUrl);
		}
	}

	/**
	 * Use to pass the Display options for this ImageLoadingView:<br />
	 * Usage(example):<br />
	 * <code>
		DisplayImageOptions options = <br />
		new DisplayImageOptions.Builder()<br />
	    .showStubImage(R.drawable.stub_image)<br />
	    .showImageForEmptyUri(mNullDrawable)<br />
	    .cacheInMemory()<br />
	    .cacheOnDisc()<br />
	    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)<br />
	    .build()<br />
	 * </code>
	 * @param options   {@linkplain DisplayImageOptions Display image options} for image displaying. If <b>null</b> -
	 *                  default display image options
	 *                  {@linkplain ImageLoaderConfiguration.Builder#defaultDisplayImageOptions(DisplayImageOptions) from
	 *                  configuration} will be used.
	 * @see DisplayImageOptions
	 */
	public void setDisplayOptions(DisplayImageOptions options) {
		this.mDisplayImageOptions = options;
	}

	/**
	 * Loads the specified uri using this {@link ImageLoadingView}'s {@link DisplayImageOptions} set using {@link #setDisplayOptions(DisplayImageOptions)} or null if not set
	 * @param uri       Image URI (i.e. "http://site.com/image.png", "file:///mnt/sdcard/image.png")
	 * @throws IllegalStateException    if {@link #init(ImageLoaderConfiguration)} method wasn't called before
	 * @throws IllegalArgumentException if passed <b>imageView</b> is null
	 */
	public void setImage(String uri) {
		ImageLoader.getInstance().displayImage(uri, this, mDisplayImageOptions, this);
	}

	/**
	 * Loads the specified uri using the passed {@link DisplayImageOptions}
	 * @param uri       Image URI (i.e. "http://site.com/image.png", "file:///mnt/sdcard/image.png")
	 * @param options   {@linkplain DisplayImageOptions Display image options} for image displaying. If <b>null</b> -
	 *                  default display image options
	 *                  {@linkplain ImageLoaderConfiguration.Builder#defaultDisplayImageOptions(DisplayImageOptions) from
	 *                  configuration} will be used.
	 * @throws IllegalStateException    if {@link #init(ImageLoaderConfiguration)} method wasn't called before
	 * @throws IllegalArgumentException if passed <b>imageView</b> is null
	 */
	public void setImage(String uri, DisplayImageOptions options) {
		ImageLoader.getInstance().displayImage(uri, this, options, this);
	}

	/**
	 * Loads the specified uri using the passed {@link DisplayImageOptions}
	 * @param uri       Image URI (i.e. "http://site.com/image.png", "file:///mnt/sdcard/image.png")
	 * @param options   {@linkplain DisplayImageOptions Display image options} for image displaying. If <b>null</b> -
	 *                  default display image options
	 *                  {@linkplain ImageLoaderConfiguration.Builder#defaultDisplayImageOptions(DisplayImageOptions) from
	 *                  configuration} will be used.
	 * @param listener  {@linkplain ImageLoadingListener Listener} for image loading process. Listener fires events on UI
	 *                  thread.
	 * @throws IllegalStateException    if {@link #init(ImageLoaderConfiguration)} method wasn't called before
	 * @throws IllegalArgumentException if passed <b>imageView</b> is null
	 */
	public void setImage(String uri, DisplayImageOptions options, ImageLoadingListener listener) {
		ImageLoader.getInstance().displayImage(uri, this, options, listener);
	}

	public void setListener(ImageLoadingListener listener) {
		mListener = listener;
	}
	@Override
	public void onLoadingStarted(String imageUri, View view) {
		if (mListener != null) {
			mListener.onLoadingStarted(imageUri, view);
		}
	}
	@Override
	public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
		if (mListener != null) {
			mListener.onLoadingFailed(imageUri, view, failReason);
		}
	}
	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		if (mListener != null) {
			mListener.onLoadingComplete(imageUri, view, loadedImage);
		}
	}
	@Override
	public void onLoadingCancelled(String imageUri, View view) {
		if (mListener != null) {
			mListener.onLoadingCancelled(imageUri, view);
		}
	}
}
