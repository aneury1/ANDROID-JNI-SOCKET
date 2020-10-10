package info.aneury.androidtuto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

///https://drive.google.com/drive/folders/0B9-wBXSAHyOSfnhWUU96eG14SEpwSU9yWnpfcEJ3WDJIM194bWlPTjJlZDh1UGRMczZJelE
class MainActivity : AppCompatActivity() {


    external fun getHelloWorld() :String

    external fun getRequestByUrl(url:String):String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
     ///   text.text = getHelloWorld()

       //doAsync{
        //    var request: String?= getHelloWorld();
        //   uiThread{
         //      text.text = request
          // }
      // }
        button.setOnClickListener({
            if(url.text.length>0)
            {
                doAsync{
                    var request: String?= getRequestByUrl(url.text.toString())
                   uiThread{
                      text.text = request
                 }
                }
            }
        })



    }

    init{
        System.loadLibrary("hworld")
    }

}