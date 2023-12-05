package com.example.criminalintentchapter14

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.fragment.NavHostFragment
import com.example.criminalintentchapter14.databinding.ActivityMainBinding
import com.example.criminalintentchapter14.ui.theme.CriminalIntentChapter14Theme

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val navHostFragment = NavHostFragment.create(R.navigation.nav_graph)
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, navHostFragment)
//            .setPrimaryNavigationFragment(navHostFragment)
//            .commit()

//        setContent {
//            CriminalIntentChapter14Theme {
//                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    Greeting("Android")
//                }
//            }
//        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//            text = "Hello $name!",
//            modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CriminalIntentChapter14Theme {
//        Greeting("Android")
//    }
//}