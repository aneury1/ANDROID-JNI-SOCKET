package info.aneury.androidtuto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

///https://drive.google.com/drive/folders/0B9-wBXSAHyOSfnhWUU96eG14SEpwSU9yWnpfcEJ3WDJIM194bWlPTjJlZDh1UGRMczZJelE
class MainActivity : AppCompatActivity() {


    external fun getHelloWorld() :String

    external fun getRequestByUrl(url:String):String

    external fun getRequestByUrlAndEndPoint(url:String, endpoint:String):String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textContent = findViewById<TextView>(R.id.text)

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
                    var text     = url.text.toString();
                    var url      = _getUrlValid(text)
                    var endpoint = _getEndpoint(text)

                    var request: String?= getRequestByUrlAndEndPoint(url, endpoint)

                    uiThread{
                       textContent.text = request
                   }
                }
            }
        })
    }

    val  TAG ="MainActivity"

    fun _getUrlValid(url :String):String{

        if(url.indexOf('/')>0){
            return url.substring(0, url.indexOf('/') - 1);
        }
        return url;
    }


    fun _getEndpoint(buffer: String):String{

        if(buffer.contains('/'))
        {
            val ret = buffer.substring(buffer.indexOf('/'), buffer.length);
            Log.d(TAG,"substr  ${ret}");
            return ret;
        }

        return "/index.html"
    }


    init{
        System.loadLibrary("hworld")
    }

}