### Onboarding slider with a bottom toolbar and slide indicators
A simple library that helps to create Oboarding pages for Android apps
- Uses ViewPager2 Adapter
- and DepthPageTransformer
<img src = "https://user-images.githubusercontent.com/36896406/86584463-393bd700-bf85-11ea-91aa-3eeebd9b2216.gif"/>

### Table of Contents
* [Setup](#setup)
* [Custom look](#custom-look)
* [Code sample](#code-sample)
* [Listeners](#listeneres)

### Setup

- Inherit from the abstract ```OnBoardingActivity``` and implement its members:
   - ```fragments``` represents fragments to use inside the pager
   - ```colors``` represents colors to use as background inside the pager.Colors used according their index, if there are more screens than colors available, the last color is reused.
   - ```skipText```, ```previousText``` and ```finishText``` are optional, pass null if you wish to use default values (keep in mind that those strings shouldn't be too long)

### Custom look
Example of bottom bar with a custom color:

<img src = "https://user-images.githubusercontent.com/36896406/86594973-46ae8c80-bf98-11ea-9d70-0c42d0a4cffb.png" height="500"/>

You can call certain functions in ``` onCreate ``` to customize the look:
* set up colors for the bottom bar:
```
setBarColors(@ColorInt barColorRes: Int?, //pass null if you want to use the same color as page background
        @ColorInt textColor: Int, //color used for buttons' text and ImageButton icon
        @ColorInt accentColor: Int)
```

* set a drawable for a moving image (optional)
``` setMovingImageDrawable(drawable: Drawable)```
* set a drawable for the custome devider that separates the bottom bar and the fragment (optional)
``` setDividerDrawable(drawable: Drawable)```
* set ``` transformer: ViewPager2.PageTransformer? ``` to define a custom animation to use between sliders


### Listeneres
- implement ```OnBoardingActivity.OnPageChangedListener``` to pass listener for page changes
- implement ```OnBoardingActivity.ClickListener``` to set actions for *Finish* and *Skip* buttons

### Code sample
```
class DemoActivity : OnBoardingActivity(), OnBoardingActivity.OnPageChangedListener, OnBoardingActivity.ClickListener {

    override val colors: List<Int>
        get() = arrayListOf(R.color......)
    override val fragments: ArrayList<Fragment>
        get() {
            ....
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setup colors to use inside the bar
        setBarColors(ContextCompat.getColor(this, R.color.primary),
            ContextCompat.getColor(this, R.color.primaryText),
            ContextCompat.getColor(this, R.color.accent))

        getDrawable(R.drawable.ic_plane)?.let { setMovingImageDrawable(it)}
        getDrawable(R.drawable.bg_divider)?.let { setDividerDrawable(it) }

        setClickListener(this)
    }

    override fun onPageChanged(position: Int) {
        ...
    }

    override fun onFinish() {
        ...
    }

    override fun onSkip() {
        ...
    }
}

```

PS: checkout Demo app
