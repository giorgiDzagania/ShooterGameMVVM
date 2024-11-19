package com.madeit.shooters.presentation

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.madeit.shooters.R
import com.madeit.shooters.common.showToast
import com.madeit.shooters.databinding.ActivityWelcomeBinding
import com.madeit.shooters.presentation.shooters.ShooterGamesActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityWelcomeBinding.inflate(layoutInflater) }
    private val pickImage = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? -> uri?.let { updateProfileImage(it) } }

    companion object {
        private const val REQUEST_CODE_PERMISSION = 100
        const val PLAYER_NAME = "com.madeit.shooters.namePlayer"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        btnUpdateProfileImage()
        btnLogIn()
        setUpPasswordVisibilityOnClick()
    }

    private fun btnUpdateProfileImage() {
        binding.btnUserUpdateImage.setOnClickListener {
            handlePermissions()
        }
    }

    private fun handlePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                pickImage.launch("image/*")
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                    REQUEST_CODE_PERMISSION
                )
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                pickImage.launch("image/*")
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_PERMISSION
                )
            }
        }
    }

    private fun updateProfileImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .circleCrop()
            .into(binding.userProfileImage)
        showToast(R.string.profile_image_updated_successfully)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage.launch("image/*")
            } else
                showToast(R.string.permission_denied_to_read_your_external_storage)
        }
    }

    private fun btnLogIn() {
        binding.btnLogIn.setOnClickListener {
            val playerName = binding.PlayerUsername.text.toString()
            val playerPassword = binding.playerPassword.text.toString()
            if (checkValidationOfInputs(userName = playerName, userPassword = playerPassword)) {
                val intent = Intent(this@WelcomeActivity, ShooterGamesActivity::class.java)
                intent.putExtra(PLAYER_NAME, playerName)
                startActivity(intent)

            } else
                showToast(R.string.not_valid_username_or_password)
        }
    }

    private fun checkValidationOfInputs(userName: String, userPassword: String): Boolean {
        val userNameRegex = "^(?=.*[a-zA-Z])[a-zA-Z0-9 ]*$".toRegex()
        val passwordRegex = "^[a-zA-Z0-9ა-ჰ]+[a-zA-Z0-9ა-ჰ ]*$".toRegex()
        return if (userName.isNotEmpty() && userPassword.isNotEmpty() &&
            passwordRegex.matches(userPassword) && userNameRegex.matches(userName) &&
            userPassword.length >= 8
        ) {
            true
        } else {
            if (userPassword.length < 8) {
                showToast(R.string.password_must_be_at_least_8_char_long)
            } else {
                Toast.makeText(this, R.string.not_valid_username_or_password, Toast.LENGTH_SHORT)
                    .show()
            }
            false
        }
    }

    private fun setUpPasswordVisibilityOnClick() {
        binding.btnPasswordVisibility.setOnClickListener {
            if (binding.playerPassword.inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                binding.playerPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                binding.btnPasswordVisibility.setImageResource(R.drawable.ic_visibility_on)
            } else {
                binding.playerPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.btnPasswordVisibility.setImageResource(R.drawable.ic_visibility_off)
            }
        }
    }
}