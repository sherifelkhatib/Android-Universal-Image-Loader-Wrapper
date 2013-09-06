/*******************************************************************************
 * Copyright 2011-2013 Sherif elKhatib
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package mobi.sherif.example.universalimageloaderwrapper;

import mobi.sherif.util.ui.ImageLoadingView;
import android.os.Bundle;

import com.nostra13.example.universalimageloader.BaseActivity;
import com.nostra13.example.universalimageloader.Constants.Extra;
import com.nostra13.example.universalimageloader.R;

/**
 * @author Sherif elKhatib (sherif.elkhatib[at]gmail[dot]com)
 */
public class ImageLoadingViewActivity extends BaseActivity {

	String imageUrl;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_image_loading_view);

		Bundle bundle = getIntent().getExtras();
		imageUrl = bundle.getString(Extra.IMAGES);

		((ImageLoadingView) findViewById(R.id.image)).setImage(imageUrl);
	}
}