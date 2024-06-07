package com.example.pawmart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    //lists to store cart items and prices
    private val cartItems = mutableListOf<String>()
    private val cartPrices = mutableListOf<Double>()

    private lateinit var showPopUpInstruct: ImageButton
    private lateinit var showPopUpProfile: ImageButton
    private lateinit var cartItemCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //calling shared preferences
        val sharedPreferences = getSharedPreferences("PawMartPrefs", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("userName", "User")

        //update the greeting message
        val greetingTextView = findViewById<TextView>(R.id.greetingTextView)
        greetingTextView.text = "Hello, $name!"

        //calling cartcount to display when launched
        cartItemCount = findViewById(R.id.cartItemCount)
        updateCartCount()

        //popup buttons references
        showPopUpInstruct = findViewById(R.id.instrucButton)
        showPopUpInstruct.setOnClickListener {
            showPopUpI()
        }

        showPopUpProfile = findViewById(R.id.profileButton)
        showPopUpProfile.setOnClickListener {
            showPopUpP()
        }

        //pet image buttons
        val dogButton = findViewById<ImageButton>(R.id.dogButton)
        val catButton = findViewById<ImageButton>(R.id.catButton)
        val birdButton = findViewById<ImageButton>(R.id.birdButton)
        val fishButton = findViewById<ImageButton>(R.id.fishButton)

        //onClick listener for each individual image button to change activity
        dogButton.setOnClickListener {
            val intent = Intent(this, DogActivity::class.java)
            startActivity(intent)
        }

        catButton.setOnClickListener {
            val intent = Intent(this, CatActivity::class.java)
            startActivity(intent)
        }

        birdButton.setOnClickListener {
            val intent = Intent(this, BirdActivity::class.java)
            startActivity(intent)
        }

        fishButton.setOnClickListener {
            val intent = Intent(this, FishActivity::class.java)
            startActivity(intent)
        }

        //individual add to cart button and values for adding to list
        val addDogFoodButton = findViewById<Button>(R.id.addDogFoodButton)
        addDogFoodButton.setOnClickListener {
            val item = "Dog Food - Suitable for: Dogs - AED 20"
            val price = 20.0
            addToCart(item, price)
        }

        val addCatFoodButton = findViewById<Button>(R.id.addCatFoodButton)
        addCatFoodButton.setOnClickListener {
            val item = "Cat Food - Suitable for: Cat - AED 15"
            val price = 15.0
            addToCart(item, price)
        }

        val addBirdFoodButton = findViewById<Button>(R.id.addBirdFoodButton)
        addBirdFoodButton.setOnClickListener {
            val item = "Bird Food - Suitable for: Birds - AED 10"
            val price = 10.0
            addToCart(item, price)
        }

        val addFishFoodButton = findViewById<Button>(R.id.addFishFoodButton)
        addFishFoodButton.setOnClickListener {
            val item = "Fish Food - Suitable for: Fish- AED - 8"
            val price = 8.0
            addToCart(item, price)
        }

        val addDogAccessoryButton = findViewById<Button>(R.id.addDogAccessoryButton)
        addDogAccessoryButton.setOnClickListener {
            val item = "Leash - Suitable for: Dogs - AED 25"
            val price = 25.0
            addToCart(item, price)
        }

        val addCatAccessoryButton = findViewById<Button>(R.id.addCatAccessoryButton)
        addCatAccessoryButton.setOnClickListener {
            val item = "Scratch Post - Suitable for: Cats - AED 30"
            val price = 30.0
            addToCart(item, price)
        }

        val addBirdAccessoryButton = findViewById<Button>(R.id.addBirdAccessoryButton)
        addBirdAccessoryButton.setOnClickListener {
            val item = "Bird Cage - Suitable for: Birds - AED 40"
            val price = 40.0
            addToCart(item, price)
        }

        val addFishAccessoryButton = findViewById<Button>(R.id.addFishAccessoryButton)
        addFishAccessoryButton.setOnClickListener {
            val item = "Aquarium - Suitable for: Fish - AED 50"
            val price = 50.0
            addToCart(item, price)
        }

        val cartButton = findViewById<ImageButton>(R.id.cartButton)
        cartButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            intent.putStringArrayListExtra("cartItems", ArrayList(cartItems))
            intent.putStringArrayListExtra("cartPrices", cartPrices.map { it.toString() } as ArrayList<String>)
            startActivity(intent)
        }

    }

    //add to cart function for adding items to the list, show toast message and update the counter on top
    private fun addToCart(item: String, price: Double) {
        cartItems.add(item)
        cartPrices.add(price)
        Toast.makeText(this, "1 item added to cart", Toast.LENGTH_SHORT).show()
        updateCartCount()
    }

    //updating cart number function
    private fun updateCartCount() {
        cartItemCount.text = cartItems.size.toString()
    }

    //instruction popup
    private fun showPopUpI() {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.activity_instructions_pop_up, null)

        //set the width and height to match parent
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT

        val instructWindow = PopupWindow(popupView, width, height, true)
        instructWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

        //using a ViewTreeObserver to wait until the view is laid out
        popupView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                //removing the listener to avoid multiple calls
                popupView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                //defining the slide-up animation
                val slideUp = TranslateAnimation(0f, 0f, popupView.height.toFloat(), 0f)
                slideUp.duration = 500

                //applying the animation to the popup view
                popupView.startAnimation(slideUp)
            }
        })

        val closeButton = popupView.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            //defining the slide-down animation in the onClickListener when the close button is pressed
            val slideDown = TranslateAnimation(0f, 0f, 0f, popupView.height.toFloat())
            slideDown.duration = 500 // Setting the duration

            //applying the animation to the popup view
            popupView.startAnimation(slideDown)

            //to close the popup window
            slideDown.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    instructWindow.dismiss()
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }
    }


    //profile popup
    private fun showPopUpP() {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.activity_profile_pop_up, null)

        //set the width and height to match parent
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT

        val profileWindow = PopupWindow(popupView, width, height, true)
        profileWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

        //using a ViewTreeObserver to wait until the view is laid out
        popupView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                //removing the listener to avoid multiple calls
                popupView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                //defining the slide-up animation
                val slideUp = TranslateAnimation(0f, 0f, popupView.height.toFloat(), 0f)
                slideUp.duration = 500

                //applying the animation to the popup view
                popupView.startAnimation(slideUp)
            }
        })

        val sharedPreferences = getSharedPreferences("PawMartPrefs", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("userName", "User")

        val userNameTextView = popupView.findViewById<TextView>(R.id.userNameText)
        userNameTextView.text = name

        val closeButton = popupView.findViewById<Button>(R.id.closeButton2)
        closeButton.setOnClickListener {
            //defining the slide-down animation in the onClickListener when the close button is pressed
            val slideDown = TranslateAnimation(0f, 0f, 0f, popupView.height.toFloat())
            slideDown.duration = 500 // Setting the duration

            //applying the animation to the popup view
            popupView.startAnimation(slideDown)

            //to close the popup window
            slideDown.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    profileWindow.dismiss()
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }

        val logoutButton = popupView.findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            //clear user data and navigate to WelcomeActivity
            with(sharedPreferences.edit()) {
                clear()
                apply()
            }
            val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()  //close MainActivity
        }
    }


}
