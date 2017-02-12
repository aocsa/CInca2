package mono;

import java.io.*;
import java.lang.String;
import java.util.Locale;
import java.util.HashSet;
import java.util.zip.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.util.Log;
import mono.android.Runtime;

public class MonoPackageManager {

	static Object lock = new Object ();
	static boolean initialized;

	static android.content.Context Context;

	public static void LoadApplication (Context context, ApplicationInfo runtimePackage, String[] apks)
	{
		synchronized (lock) {
			if (context instanceof android.app.Application) {
				Context = context;
			}
			if (!initialized) {
				android.content.IntentFilter timezoneChangedFilter  = new android.content.IntentFilter (
						android.content.Intent.ACTION_TIMEZONE_CHANGED
				);
				context.registerReceiver (new mono.android.app.NotifyTimeZoneChanges (), timezoneChangedFilter);
				
				System.loadLibrary("monodroid");
				Locale locale       = Locale.getDefault ();
				String language     = locale.getLanguage () + "-" + locale.getCountry ();
				String filesDir     = context.getFilesDir ().getAbsolutePath ();
				String cacheDir     = context.getCacheDir ().getAbsolutePath ();
				String dataDir      = getNativeLibraryPath (context);
				ClassLoader loader  = context.getClassLoader ();

				Runtime.init (
						language,
						apks,
						getNativeLibraryPath (runtimePackage),
						new String[]{
							filesDir,
							cacheDir,
							dataDir,
						},
						loader,
						new java.io.File (
							android.os.Environment.getExternalStorageDirectory (),
							"Android/data/" + context.getPackageName () + "/files/.__override__").getAbsolutePath (),
						MonoPackageManager_Resources.Assemblies,
						context.getPackageName ());
				
				mono.android.app.ApplicationRegistration.registerApplications ();
				
				initialized = true;
			}
		}
	}

	public static void setContext (Context context)
	{
		// Ignore; vestigial
	}

	static String getNativeLibraryPath (Context context)
	{
	    return getNativeLibraryPath (context.getApplicationInfo ());
	}

	static String getNativeLibraryPath (ApplicationInfo ainfo)
	{
		if (android.os.Build.VERSION.SDK_INT >= 9)
			return ainfo.nativeLibraryDir;
		return ainfo.dataDir + "/lib";
	}

	public static String[] getAssemblies ()
	{
		return MonoPackageManager_Resources.Assemblies;
	}

	public static String[] getDependencies ()
	{
		return MonoPackageManager_Resources.Dependencies;
	}

	public static String getApiPackageName ()
	{
		return MonoPackageManager_Resources.ApiPackageName;
	}
}

class MonoPackageManager_Resources {
	public static final String[] Assemblies = new String[]{
		/* We need to ensure that "Camping Peru.dll" comes first in this list. */
		"Camping Peru.dll",
		"ByteSmith.WindowsAzure.Messaging.Android.dll",
		"Cirrious.MvvmCross.Community.Plugins.Sqlite.Droid.dll",
		"Cirrious.MvvmCross.Plugins.File.Droid.dll",
		"Cirrious.MvvmCross.Plugins.PictureChooser.dll",
		"Cirrious.MvvmCross.Plugins.PictureChooser.Droid.dll",
		"GCM.Client.dll",
		"Microsoft.WindowsAzure.Storage.dll",
		"Cirrious.MvvmCross.Community.Plugins.Sqlite.dll",
		"Cirrious.MvvmCross.Plugins.File.dll",
		"Microsoft.WindowsAzure.Mobile.dll",
		"Microsoft.WindowsAzure.Mobile.Ext.dll",
		"Newtonsoft.Json.dll",
		"System.Net.Http.Extensions.dll",
		"System.Net.Http.Primitives.dll",
		"Xamarin.Android.Support.v4.dll",
		"Xamarin.Android.Support.v7.RecyclerView.dll",
		"Cirrious.CrossCore.dll",
		"Cirrious.CrossCore.Droid.dll",
		"Cirrious.MvvmCross.Binding.dll",
		"Cirrious.MvvmCross.Binding.Droid.dll",
		"Cirrious.MvvmCross.Localization.dll",
		"Cirrious.MvvmCross.dll",
		"Cirrious.MvvmCross.Droid.dll",
		"Cirrious.MvvmCross.Droid.FullFragging.dll",
		"Cirrious.MvvmCross.Droid.Fragging.dll",
		"Xamarin.Android.Support.v7.AppCompat.dll",
		"Square.OkIO.dll",
		"Square.OkHttp.dll",
		"Square.Picasso.dll",
		"UrlImageViewHelper.dll",
		"System.Runtime.dll",
		"MLearning.Core.dll",
		"System.IO.dll",
		"System.Threading.Tasks.dll",
		"System.Runtime.Extensions.dll",
		"System.ObjectModel.dll",
		"System.Collections.dll",
		"System.Text.RegularExpressions.dll",
		"System.Linq.Expressions.dll",
		"System.Resources.ResourceManager.dll",
		"System.Globalization.dll",
		"System.Reflection.dll",
		"System.Net.Primitives.dll",
		"System.Threading.dll",
		"System.Diagnostics.Debug.dll",
		"System.Linq.dll",
		"System.Reflection.Extensions.dll",
		"System.Text.Encoding.dll",
		"System.Diagnostics.Tools.dll",
		"System.Linq.Queryable.dll",
		"System.Runtime.Serialization.Primitives.dll",
		"System.Runtime.InteropServices.dll",
		"System.Collections.Concurrent.dll",
		"System.Net.Requests.dll",
	};
	public static final String[] Dependencies = new String[]{
	};
	public static final String ApiPackageName = "Mono.Android.Platform.ApiLevel_18";
}
