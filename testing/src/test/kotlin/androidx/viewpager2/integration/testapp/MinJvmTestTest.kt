package androidx.viewpager2.integration.testapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MinJvmTestTest {

  @org.junit.Test
  fun test123() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    assertThat(context.packageName).isEqualTo("androidx.viewpager2.integration.testapp.test.test")
  }
}