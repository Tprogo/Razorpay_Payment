package com.example.razorpayapplication

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        * To ensure faster loading of the Checkout form,
//        * call this method as early as possible in your checkout flow
//        * */
        Checkout.preload(applicationContext)
        val co = Checkout()
        // apart from setting it in AndroidManifest.xml, keyId can also be set
        // programmatically during runtime
        co.setKeyID("rzp_test_ucYeAR4GSUeQ9g")


        val buyNowBtn1 = findViewById<Button>(R.id.buyBtn)
        val buyNowBtn2 = findViewById<Button>(R.id.buyBtn2)


        Checkout.sdkCheckIntegration(this)

//        There are two ways to pass the checkout parameters.
//        You can either use payloadhelper or the JSONObject options.
//        We recommend using payloadhelper to ensure that the right data types are used for the parameter values.


        buyNowBtn1.setOnClickListener {
            val name1 = "Titan Mechanical Black Dial Automatic Watch"
            val description1 = "MechanicaL Automatic watch from the TATA's watch making Brand Titan"
            val price1 = "1519500"
            startPayment(name = "$name1", description = "$description1", price = price1)
        }

        buyNowBtn2.setOnClickListener {
            val name2 = "Diamond Heart Bracelet"
            val description2 = "A beautiful with diamond heart and infinity"
            val price2 = "1119500"
            startPayment(name = "$name2", description = "$description2", price = "$price2")
        }


    }

    private fun startPayment(name: String, description: String?, price: String) {
        /*
        *  You need to pass the current activity to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","$name")
            options.put("description","$description")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQy0AATM5RlTjKtZsAv98aM2nicoX9Ewhtw4Q24DQ8d-Q&s")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
//            options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount","$price")//pass amount in currency subunits( in Paisa 1Rs = 100 Paisa)

//            val retryObj =JSONObject();
//            retryObj.put("enabled", true);
//            retryObj.put("max_count", 4);
//            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email","")
            prefill.put("contact","")

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Successful ", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed ", Toast.LENGTH_SHORT).show()
    }
}