package com.example.kotlin_retfit_sample_weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin_retfit_sample_weather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // bindingは、activity_main.xmlというレイアウトリソースと直接通信するための変数です。
    // これにより、レイアウト内の各ビュー（ボタン、テキストビューなど）にアクセスできます。
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ActivityMainBindingのinflateメソッドを使用して、activity_main.xmlレイアウトを変数'binding'に関連付けます。
        binding = ActivityMainBinding.inflate(layoutInflater)
        // setContentViewメソッドで、'binding.root'を指定することで、activity_main.xmlがこのアクティビティのレイアウトとしてセットされます。
        setContentView(binding.root)
        // setSupportActionBarメソッドを使用して、ツールバーをこのアクティビティのアクションバーとして設定します。
        setSupportActionBar(binding.toolbar)

    }
}