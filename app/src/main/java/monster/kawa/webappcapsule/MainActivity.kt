package monster.kawa.webappcapsule

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.view.MotionEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.webkit.WebViewAssetLoader

import monster.kawa.webappcapsule.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isShiftOn = false

    private val buttonToKeyCode by lazy {
        mapOf(
            binding.buttonUp to KeyEvent.KEYCODE_DPAD_UP,
            binding.buttonDown to KeyEvent.KEYCODE_DPAD_DOWN,
            binding.buttonLeft to KeyEvent.KEYCODE_DPAD_LEFT,
            binding.buttonRight to KeyEvent.KEYCODE_DPAD_RIGHT,
            binding.buttonEnter to KeyEvent.KEYCODE_ENTER,
            binding.buttonEsc to KeyEvent.KEYCODE_ESCAPE,
            binding.buttonF2 to KeyEvent.KEYCODE_F2
        )
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        val assetLoader = WebViewAssetLoader.Builder()
            .setDomain("appassets.androidplatform.net")
            .addPathHandler("/www/", WebViewAssetLoader.AssetsPathHandler(this))
            .build()

        binding.webView.apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                allowFileAccess = false
                allowContentAccess = false
            }
            webViewClient = object : WebViewClient() {
                override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest) =
                    assetLoader.shouldInterceptRequest(request.url)

                override fun onPageFinished(view: WebView, url: String) {
                    view.requestFocus()
                }
            }
            loadUrl("https://appassets.androidplatform.net/www/index.html")
        }

        setupButtonKeyListeners()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupButtonKeyListeners() {
        binding.buttonShift.setOnClickListener {
            isShiftOn = !isShiftOn
            val action = if (isShiftOn) KeyEvent.ACTION_DOWN else KeyEvent.ACTION_UP
            dispatchKeyEventToWebView(KeyEvent.KEYCODE_SHIFT_LEFT, action)
            binding.buttonShift.text = if (isShiftOn) "Shift (on)" else "Shift"
        }

        buttonToKeyCode.forEach { (button, keyCode) ->
            button.setOnTouchListener { _, event ->
                val action = when (event.action) {
                    MotionEvent.ACTION_DOWN -> KeyEvent.ACTION_DOWN
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> KeyEvent.ACTION_UP
                    else -> return@setOnTouchListener false
                }
                dispatchKeyEventToWebView(keyCode, action)
                true
            }
        }
    }

    private fun dispatchKeyEventToWebView(keyCode: Int, action: Int) {
        binding.webView.requestFocus()
        val eventTime = SystemClock.uptimeMillis()
        binding.webView.dispatchKeyEvent(KeyEvent(eventTime, eventTime, action, keyCode, 0))
    }
}