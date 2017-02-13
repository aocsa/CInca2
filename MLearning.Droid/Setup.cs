using Android.Content;
using Cirrious.CrossCore;
using Cirrious.CrossCore.Platform;
using Cirrious.MvvmCross.Droid.Platform;
using Cirrious.MvvmCross.ViewModels;
using CincaMLearning.Core.File;
using CincaMLearning.Droid.AsyncStorage;
using CincaMLearning.Core;

namespace CincaMLearning.Droid
{
    public class Setup : MvxAndroidSetup
    {


        public Setup(Context applicationContext) : base(applicationContext)
        {

        }


        protected override IMvxApplication CreateApp()
        {
			Mvx.RegisterSingleton<IUserInfo>(new UserInfo(this.ApplicationContext));

            return new CincaMLearning.Core.App();
        }
		
        protected override IMvxTrace CreateDebugTrace()
        {
            return new DebugTrace();
        }

        protected override void InitializeLastChance()
        {
            Mvx.RegisterSingleton<IAsyncStorageService>(new DroidAsyncStorageService());

            base.InitializeLastChance();
        }
    }
}