package com.nimble

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nimble.di.repository.LocalAppRepository
import com.nimble.di.repository.RemoteAuthRepository
import com.nimble.di.repository.UserPreferencesRepository
import com.nimble.ui.auth.AuthViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.mockito.Mock

/**
 * @file AuthViewModelTest
 * @date 12/1/2023
 * @brief
 * Created by CharithaRatnayake(jachratnayake@gmail.com) on 12/1/2023.
 */

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    // Rule to indicate that LiveData should be executed immediately in tests
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Mocked dependencies
    @Mock
    private lateinit var authRepository: RemoteAuthRepository

    @Mock
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    @Mock
    private lateinit var localAppRepository: LocalAppRepository

    // Class under test
    private lateinit var authViewModel: AuthViewModel

//    // Coroutine dispatcher and scope for testing coroutines
//    private val testDispatcher = TestCoroutineDispatcher()
//    private val testScope = TestCoroutineScope(testDispatcher)
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.initMocks(this)
//
//        // Initialize the ViewModel with mocked dependencies
//        authViewModel = AuthViewModel(authRepository, userPreferencesRepository, localAppRepository)
//
//        // Set the ViewModelScope to use the testDispatcher
//        authViewModel.viewModelScope = ViewModelScope(testScope, testDispatcher)
//    }
//
//    @Test
//    fun `login success`() = testScope.runBlockingTest {
//        // Mock data for a successful login response
//        val successResponse = Resource.Success(LoginResponseDataModel(TokenResponse()))
//
//        // Mock the login response from the repository
//        `when`(authRepository.login(AuthTokenDataModel("test@example.com", "password")))
//            .thenReturn(successResponse)
//
//        // Call the login function
//        authViewModel.login("test@example.com", "password")
//
//        // Ensure the loading state is emitted
//        assert(authViewModel.authLoginResponse.value == Resource.Loading)
//
//        // Advance the coroutine dispatcher to execute all tasks
//        testDispatcher.advanceUntilIdle()
//
//        // Ensure the success response is emitted
//        assert(authViewModel.authLoginResponse.value == successResponse)
//
//        // Ensure the token data is saved (verify or add assertions based on your actual implementation)
//    }
//
//    @Test
//    fun `register success`() = testScope.runBlockingTest {
//        // Mock data for a successful registration response
//        val successResponse = Resource.Success(Unit)
//
//        // Mock the registration response from the repository
//        `when`(
//            authRepository.register(
//                RegisterRequestDataModel(
//                    UserDataModel(
//                        email = "test@example.com",
//                        name = "John",
//                        password = "password",
//                        passwordConfirmation = "password"
//                    )
//                )
//            )
//        ).thenReturn(successResponse)
//
//        // Call the register function
//        authViewModel.register("John", "test@example.com", "password")
//
//        // Ensure the loading state is emitted
//        assert(authViewModel.authLoginResponse.value == Resource.Loading)
//
//        // Advance the coroutine dispatcher to execute all tasks
//        testDispatcher.advanceUntilIdle()
//
//        // Ensure the success response is emitted
//        assert(authViewModel.authLoginResponse.value == successResponse)
//
//        // Add Mockito verification or assertions based on your actual implementation
//    }
}