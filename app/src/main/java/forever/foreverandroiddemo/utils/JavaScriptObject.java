package forever.foreverandroiddemo.utils;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;


//sdk17版本以上加上注解


public class JavaScriptObject {
	Context mContxt;

	public JavaScriptObject(Context mContxt) {
		this.mContxt = mContxt;
	}

	@JavascriptInterface //sdk17版本以上加上注解
	public void toTeacherInfo(String name) {
//		Intent intent=new Intent();
//		intent.setClass(mContxt, TeacherInfoActivity.class);
//		intent.putExtra("uid",name);
//		mContxt.startActivity(intent);
	}

	public void fun2(String name) {
		Toast.makeText(mContxt, "调用fun2:" + name, Toast.LENGTH_SHORT).show();
	}
}
