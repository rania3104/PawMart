package com.example.pawmart

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CartActivity : AppCompatActivity() {
    private lateinit var showPopUpInstruct: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val cartItemsTextView = findViewById<TextView>(R.id.cartItemsTextView)
        val totalAmountTextView = findViewById<TextView>(R.id.totalAmountTextView)

        //retrieve cart items and prices from intent extra
        val cartItems = intent.getStringArrayListExtra("cartItems")
        val cartPrices = intent.getStringArrayListExtra("cartPrices")

        //checkout popup
        showPopUpInstruct = findViewById(R.id.checkoutButton)
        showPopUpInstruct.setOnClickListener {
            if (cartItems.isNullOrEmpty() || cartPrices.isNullOrEmpty()) {
                //toast message if cart is empty and user presses checkout
                Toast.makeText(this, "Please add items to your cart", Toast.LENGTH_SHORT).show()
            } else {
                showPopUpI(cartItems, cartPrices, cartItemsTextView, totalAmountTextView)
            }
        }

        //to update the cart items and total price
        if (cartItems.isNullOrEmpty() || cartPrices.isNullOrEmpty()) {
            cartItemsTextView.text = "0 items in your cart"
            totalAmountTextView.text = "Total: AED 0.00"
        } else {
            cartItemsTextView.text = cartItems.joinToString("\n\n")
            val totalAmount = cartPrices.map { it.toDouble() }.sum()
            totalAmountTextView.text = "Total: AED %.2f".format(totalAmount)
        }

        //back button (finishes activity)
        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        //clear button
        val clearButton = findViewById<Button>(R.id.clearButton)
        clearButton.setOnClickListener {
            cartItemsTextView.text = "0 items in your cart"
            totalAmountTextView.text = "Total: AED 0.00"
            //clear the lists
            if (cartItems != null) {
                cartItems.clear()
            }
            if (cartPrices != null) {
                cartPrices.clear()
            }
            //update cart count in MainActivity
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            mainActivityIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(mainActivityIntent)
        }
    }

    //checkout popup
    private fun showPopUpI(cartItems: ArrayList<String>?, cartPrices: ArrayList<String>?, cartItemsTextView: TextView, totalAmountTextView: TextView) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.activity_check_out_pop_up, null)

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT

        val instructWindow = PopupWindow(popupView, width, height, true)
        instructWindow.showAtLocation(popupView, Gravity.CENTER, 20, 20)

        val successMessageTextView = popupView.findViewById<TextView>(R.id.successMessageTextView)
        successMessageTextView.text = "Checkout Successful!"

        //clear the cart and update UI
        cartItemsTextView.text = "0 items in your cart"
        totalAmountTextView.text = "Total: AED 0.00"
        cartItems?.clear()
        cartPrices?.clear()

        //use Handler to introduce a delay before transitioning back to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            instructWindow.dismiss()
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            mainActivityIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(mainActivityIntent)
        }, 1000) // 1000 milliseconds = 1 second
    }
}
