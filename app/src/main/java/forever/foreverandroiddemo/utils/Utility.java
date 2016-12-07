package forever.foreverandroiddemo.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Utility {

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		if (listAdapter.getCount() < 6) {
			for (int i = 0; i < listAdapter.getCount(); i++) {
				View listItem = listAdapter.getView(i, null, listView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}
		} else {
			for (int i = 0; i < 6; i++) {
				View listItem = listAdapter.getView(i, null, listView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight;
		listView.setLayoutParams(params);
	}

	public static void setGridViewHeightBasedOnChildren(GridView gridView) {
		ListAdapter listAdapter = gridView.getAdapter();
		int numColumns = gridView.getNumColumns();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		if (listAdapter.getCount() < 12) {
			for (int i = 0; i < listAdapter.getCount() / numColumns + 0.5; i++) {
				View listItem = listAdapter.getView(i, null, gridView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}
		} else {
			for (int i = 0; i < 6; i++) {
				View listItem = listAdapter.getView(i, null, gridView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight;
		gridView.setLayoutParams(params);
	}

	public static void setWindowBackground(Activity activity, Float alpha) {
		WindowManager.LayoutParams lp2 = activity.getWindow().getAttributes();
		if (alpha == 0.5f) {
			alpha = 0.3f;
		}
		lp2.alpha = alpha;
		activity.getWindow().setAttributes(lp2);
	}
}
