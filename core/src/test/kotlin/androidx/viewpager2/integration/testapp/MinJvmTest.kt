package androidx.viewpager2.integration.testapp

import com.google.common.truth.Truth

class MinJvmTest {

  @org.junit.Test
  fun test123() {
    Truth.assertThat(1).isNotEqualTo(2)
  }
}