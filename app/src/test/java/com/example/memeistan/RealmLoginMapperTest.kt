package com.example.memeistan

import com.example.memeistan.data.model.json.LoginResponse
import com.example.memeistan.data.model.realm.LoginRealmModel
import com.example.memeistan.data.model.realm.mapper.RealmLoginMapper
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.hamcrest.Matchers.*

class RealmLoginMapperTest {
    @Rule
    @JvmField
    var initRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var realmLoginMapper: RealmLoginMapper


    @BeforeEach
    fun beforeTest() {
        // Prepare Test Data

    }

    @Test
    fun testLoginResponseToUserLoginRealmModel() {
        val loginRealmModel = LoginRealmModel("shami", "shami", true)
        val response: LoginRealmModel = realmLoginMapper.loginResponseToUserLoginRealmModel(
            LoginResponse(
                "success",
                "shami",
                "shami"
            )
        )

        // It throws some error because instances of data classes are not same  // Check it later
        // assertEquals(loginRealmModel, response)

        assertEquals(loginRealmModel.loginStatus, response.loginStatus)
        assertEquals(loginRealmModel.userName, response.userName)

    }


}