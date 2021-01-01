package com.example.memeistan

import android.app.Activity
import com.example.dagger_android.model.RetrofitInterface
import com.example.memeistan.business.usecases.SignUpUseCase
import com.example.memeistan.presentation.ui.signup.SignUpPresenter
import com.example.memeistan.presentation.ui.signup.SignupViewInterface
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SignUpTest {
    @Mock
    lateinit var signUpViewInterface: SignupViewInterface

    @Mock
    lateinit var activity: Activity

    @Mock
    lateinit var signUpUseCase: SignUpUseCase

    @Mock
    lateinit var retrofitInterface: RetrofitInterface



    private lateinit var signUpPresenter: SignUpPresenter
    private lateinit var mockWebServer: MockWebServer


    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this);
        // Setting up Presenter for Testing
        signUpPresenter = SignUpPresenter(activity, signUpUseCase)
        signUpPresenter.attachView(signUpViewInterface)


        //  Creating the mock web server
        mockWebServer = MockWebServer()

        // Creating mock responses
        mockWebServer.enqueue(MockResponse().setBody("{\"response\":\"User Already Exists\"}"))
        mockWebServer.enqueue(MockResponse().setBody("{\"response\":\"User Registered Successfully\"}"))
        mockWebServer.start()


        // Creating the retrofit API for pinging the mock web server
        retrofitInterface = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(RetrofitInterface::class.java)


    }

    @Test
    fun signUpValidationTest() {
        assertEquals(
            "Please Enter Your Username",
            signUpPresenter.signUpValidation(
                "", "shami",
                "shami", "shami", "anyByte64ImageString"
            )
        )

        assertEquals(
            "Please Enter Your Password",
            signUpPresenter.signUpValidation(
                "shami", "",
                "shami", "shami", "anyByte64ImageString"
            )
        )
        assertEquals(
            "Please Repeat Your Password",
            signUpPresenter.signUpValidation(
                "shami", "shami",
                "", "shami", "anyByte64ImageString"
            )
        )
        assertEquals(
            "Password Does Not Match",
            signUpPresenter.signUpValidation(
                "shami", "shami",
                "shami123", "shami", "anyByte64ImageString"
            )
        )
        assertEquals(
            "Please Specify Your Gender",
            signUpPresenter.signUpValidation(
                "shami", "shami",
                "shami", "", "anyByte64ImageString"
            )
        )
        assertEquals(
            "Please Upload an Image",
            signUpPresenter.signUpValidation(
                "shami", "shami",
                "shami", "male", ""
            )
        )
        assertEquals(
            "Validation Passed",
            signUpPresenter.signUpValidation(
                "shami", "shami",
                "shami", "male", "anyByte64ImageString"
            ), "Validation Passed"
        )

    }

    @Test
    fun trySignUpOnServerTest() {
        val res1 =
            retrofitInterface.getSignUpResponse("shami", "shami", "shami", "shami").blockingFirst()
        assertEquals("User Already Exists", res1.response)

        val res2 =
            retrofitInterface.getSignUpResponse("shami", "shami", "shami", "shami").blockingFirst()
        assertEquals("User Registered Successfully", res2.response)

    }

    @After
    fun afterTest() {
        mockWebServer.shutdown()
        signUpPresenter.detachView()
    }
}


// For Testing that appropriate method is called
//verify(signupViewInterface).signUpValidationStatus("Please Enter Your Password")
//verify(signUpPresenter).trySignUpOnServer("shami","shami","shami","shami")


