package com.example.memeistan


import android.content.Context
import com.example.dagger_android.model.RetrofitInterface
import com.example.memeistan.business.model.LoginBusinessModel
import com.example.memeistan.data.model.realm.mapper.RealmLoginMapper
import com.example.memeistan.data.provider.RealmLocalProvider
import com.example.memeistan.presentation.ui.login.LogInPresenter
import com.example.memeistan.presentation.ui.login.LoginViewInterface
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Spy

class LoginTest {
    @Mock
    lateinit var context: Context

    @Mock
    lateinit var retrofitInterface: RetrofitInterface

    // Just an important info // if I use spy, then I have to take care of the primary args
    // of realm local provider so use mock to make it easy
    @Mock
    lateinit var realmLocalProvider: RealmLocalProvider

    @Mock
    lateinit var realmLoginMapper: RealmLoginMapper

    @Mock
    lateinit var loginViewInterface: LoginViewInterface

    private lateinit var logInPresenter: LogInPresenter

    @BeforeEach
    fun beforeTest() {   // For annotations
        MockitoAnnotations.initMocks(this)
        // Presenter Initialisations and attaching View
        logInPresenter =
            LogInPresenter(context, retrofitInterface, realmLocalProvider, realmLoginMapper)
        logInPresenter.attachView(loginViewInterface)
    }

    @AfterEach
    fun afterTest() {
        logInPresenter.detachView()
    }

    @Test
    fun `Login Validation Tests`() {

        assertAll(Executable {
            assertEquals(
                "Username is empty",
                logInPresenter.loginValidation("", "shami")
            )
        }, Executable {
            assertEquals(
                "Password is empty",
                logInPresenter.loginValidation("shami", "")
            )
        },
            Executable {
                assertEquals(
                    "Validation Passed",
                    logInPresenter.loginValidation("shami", "shami")
                )
            })
    }

    @Test
    fun testSubscribers() {
        `when`(realmLocalProvider.readLoggedUserData()).thenAnswer {
            val callback = it.arguments[0] as LogInPresenter.LoginState
            callback.onNext(t = LoginBusinessModel("shami", "shami", true))
            callback.onComplete()
        }

    }


}