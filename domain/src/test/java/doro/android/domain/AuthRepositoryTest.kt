package doro.android.domain

import dagger.hilt.android.testing.HiltAndroidTest
import doro.android.domain.repository.AuthRepository
import io.mockk.mockk
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
class AuthRepositoryTest {

    @Test
    fun `login success`() {
        GlobalScope.launch {
            val authRepository = mockk<AuthRepository>()
            val userResponse = authRepository.signIn("goddoro", "gusgh0705!")
            assertEquals(userResponse.id, 2)
        }
    }
}