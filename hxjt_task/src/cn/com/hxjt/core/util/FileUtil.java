package cn.com.hxjt.core.util;

import java.io.File;

import android.os.Environment;
import cn.com.hxjt.core.cons.SystemConst;

public class FileUtil {
	public static String getDownloadFileDir() {
		File p = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		String parent = p.getParent();
		String pth = parent + "/" + SystemConst.task_doc_dir;
		return pth;
	}
}
