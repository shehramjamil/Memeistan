package com.example.memeistan.presentation.ui.signup

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.memeistan.data.model.json.SignUpResponse
import com.example.dagger_android.model.RetrofitInterface
import com.example.memeistan.REQUEST_CODE
import com.example.memeistan.business.usecases.SignUpUseCase
import com.example.memeistan.presentation.base.BasePresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class SignUpPresenter @Inject constructor(
    private val activity: Activity,
    private val signUpUseCase: SignUpUseCase
) : BasePresenter<SignupViewInterface>(), SignUpPresenterInterface {

    private var bitmap: Bitmap? = null

    override fun signUpValidation(
        userName: String,
        password: String,
        repeatPassword: String,
        gender: String,
        imageString: String
    ): String {
        return when {
            userName == "" -> "Please Enter Your Username"
            userName.length > 10 -> "Username Length Must Not Exceeds 10"
            password == "" -> "Please Enter Your Password"
            repeatPassword == "" -> "Please Repeat Your Password"
            password != repeatPassword -> "Password Does Not Match"
            gender == "" -> "Please Specify Your Gender"
            imageString == "" -> "Please Upload an Image"
            else -> {
                "Validation Passed"
            }
        }
    }

    override fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(activity, intent, REQUEST_CODE, null)
        // Result will be retrieved in OnActivityResult in Activity

    }

    override fun compressAndConvertImageToString(uri: Uri?): String {
        bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(activity.contentResolver, uri);
        } else {
            val source = ImageDecoder.createSource(activity.contentResolver, uri!!)
            ImageDecoder.decodeBitmap(source)
        }
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val imageBytes: ByteArray = outputStream.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    override fun trySignUpOnServer(
        userName: String,
        password: String,
        gender: String,
        imageString: String
    ) {
        signUpUseCase
            .execute(userName, password, gender, imageString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer())
    }

    private fun observer(): Observer<SignUpResponse> {
        return object : Observer<SignUpResponse> {

            override fun onComplete() {
            }

            override fun onSubscribe(p0: Disposable) {
                //p0.dispose()
            }

            override fun onNext(msg: SignUpResponse) {
                if (msg.response == "User Registered Successfully") {
                    view?.signUpServerStatus(msg.response)
                    activity.finish()
                } else {
                    view?.signUpServerStatus(msg.response)
                }
            }

            override fun onError(p0: Throwable) {
                view?.signUpServerStatus(p0.toString())
            }
        }
    }
}

