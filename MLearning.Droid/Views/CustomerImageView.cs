
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;
using Android.Graphics;
using Android.Graphics.Drawables;
using Square.Picasso;
using Android.Text;
using System.Threading.Tasks;
using System.Net.Http;
using Android.Text.Util;

namespace MLearning.Droid
{
	public class CustomerImageView : RelativeLayout
	{
		Context context;
		LinearLayout image;
		LinearLayout background;
		TextView txtDescription;
		TextView txtTitle;
		ImageView imBack;
		public LinearLayout mainLinearLayout;

		LinearLayout relTemp;

		public CustomerImageView (Context context) :
		base (context)
		{
			this.context = context;
			Initialize ();
		}


		void Initialize ()
		{
			this.LayoutParameters = new RelativeLayout.LayoutParams(-1,-1);
			this.SetGravity(GravityFlags.CenterHorizontal);

			mainLinearLayout = new LinearLayout (context);
			mainLinearLayout.LayoutParameters = new LinearLayout.LayoutParams (-1, -1);
			mainLinearLayout.Orientation = Orientation.Vertical;

			imBack = new ImageView (context);
			txtDescription = new TextView (context);
			txtTitle = new TextView (context);

			txtTitle.SetTextSize (ComplexUnitType.Fraction, Configuration.getHeight(38));
			txtDescription.SetTextSize (ComplexUnitType.Fraction, Configuration.getHeight(32));
			txtTitle.Typeface =  Typeface.CreateFromAsset(context.Assets, "fonts/ArcherMediumPro.otf");
			txtDescription.Typeface =  Typeface.CreateFromAsset(context.Assets, "fonts/ArcherMediumPro.otf");


			int padW = Configuration.getWidth(30);
			int padH = Configuration.getHeight (0);
			this.SetPadding (padW,padH,padW,padH);
	
			mainLinearLayout.AddView (txtTitle);
			mainLinearLayout.AddView (imBack);
			mainLinearLayout.AddView (txtDescription);

			this.AddView (mainLinearLayout);
			//this.AddView (background);


		}

		private String _title;
		public String Title{
			get{ return _title;}
			set{ _title = value;
				if (_title == null) {
					mainLinearLayout.RemoveView (txtTitle);
				}
				txtTitle.Text = _title;
				Linkify.AddLinks (txtTitle, MatchOptions.All);//HUILLCA
			}

		}

		private String _description;
		public String Description{
			get{ return _description;}
			set{ _description = value;

				if (_description == null) {
					mainLinearLayout.RemoveView (txtDescription);
				}

				txtDescription.TextFormatted = Html.FromHtml (_description);
				Linkify.AddLinks (txtDescription, MatchOptions.All);//HUILLCA

				ViewTreeObserver vto = txtDescription.ViewTreeObserver;
				int H = 0;
				vto.GlobalLayout += (sender, args) => {     
					H = txtDescription.Height;
					txtDescription.LayoutParameters.Height = H - Configuration.getHeight (50);

				};  
				//txtDescription.Text = _description;
			}

		}

		private String _imagen;
		public String Imagen{
			get{ return _imagen;}
			set{ _imagen = value;

				Picasso.With (context).Load (_imagen).Resize(Configuration.getWidth(640),Configuration.getHeight(640)).CenterInside().Into (imBack);

			}


		}

		public async void  DownloadImage(){

			Bitmap  bm = await GetImageBitmapFromUrlAsync (_imagen);
			imBack.SetImageBitmap (Bitmap.CreateScaledBitmap (bm, Configuration.getWidth (582), Configuration.getHeight (394), true));
			bm = null;
		}

		private string _color;
		public string ColorTexto{
			get{return _color; }
			set{_color = value;
				txtTitle.SetTextColor(Color.ParseColor(_color));
			}

		}


		private Bitmap _imageBitmap;
		public Bitmap ImageBitmap{
			get{ return _imageBitmap;}
			set{ _imageBitmap = value;			

				Drawable dr = new BitmapDrawable (Bitmap.CreateScaledBitmap (_imageBitmap, Configuration.getWidth (582), Configuration.getHeight (394), true));
				image.SetBackgroundDrawable (dr);
			}

		}

		public Bitmap getBitmapFromAsset( String filePath) {
			System.IO.Stream s =context.Assets.Open (filePath);
			Bitmap bitmap = BitmapFactory.DecodeStream (s);

			return bitmap;
		}


		public async Task<Bitmap> GetImageBitmapFromUrlAsync(string url)
		{
			Bitmap imageBitmap = null;

			using (var httpClient = new HttpClient())
			{
				var imageBytes = await httpClient.GetByteArrayAsync(url);
				if (imageBytes != null && imageBytes.Length > 0)
				{
					imageBitmap = BitmapFactory.DecodeByteArray(imageBytes, 0, imageBytes.Length);
				}
			}

			return imageBitmap;
		}


	}
}

