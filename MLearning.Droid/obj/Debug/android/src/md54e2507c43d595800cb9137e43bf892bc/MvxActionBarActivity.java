package md54e2507c43d595800cb9137e43bf892bc;


public class MvxActionBarActivity
	extends md54e2507c43d595800cb9137e43bf892bc.MvxActionBarEventSourceActivity
	implements
		mono.android.IGCUserPeer
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_setContentView:(I)V:GetSetContentView_IHandler\n" +
			"";
		mono.android.Runtime.register ("MLearning.Droid.MvxActionBarActivity, Camping Peru, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null", MvxActionBarActivity.class, __md_methods);
	}


	public MvxActionBarActivity () throws java.lang.Throwable
	{
		super ();
		if (getClass () == MvxActionBarActivity.class)
			mono.android.TypeManager.Activate ("MLearning.Droid.MvxActionBarActivity, Camping Peru, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null", "", this, new java.lang.Object[] {  });
	}


	public void setContentView (int p0)
	{
		n_setContentView (p0);
	}

	private native void n_setContentView (int p0);

	private java.util.ArrayList refList;
	public void monodroidAddReference (java.lang.Object obj)
	{
		if (refList == null)
			refList = new java.util.ArrayList ();
		refList.add (obj);
	}

	public void monodroidClearReferences ()
	{
		if (refList != null)
			refList.clear ();
	}
}
