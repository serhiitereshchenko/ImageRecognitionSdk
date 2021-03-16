package com.serhii.veriff_assignment.screens.recognize

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.serhii.veriff_assignment.R
import com.serhii.veriff_assignment.databinding.RecognizeFragmentBinding
import com.serhii.veriff_assignment.mvvm.Resource
import com.serhii.veriff_assignment.mvvm.succeeded
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import timber.log.Timber

@AndroidEntryPoint
class RecognizeFragment : Fragment() {

    private var _binding: RecognizeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecognizeViewModel by viewModels()

    private val cameraFileHelper: CameraFileHelper by lazy { CameraFileHelper(requireContext()) }
    private val takePictureCallback =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                Timber.d("Image captured: ${viewModel.currentPhotoUri}")
                viewModel.onImageLoaded()
            } else {
                viewModel.onImageLoadFailed()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecognizeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoadingIndicator()
        observeImage()
        observeRecognizedText()
        observeRecognizedFaces()
        binding.captureButton.setOnClickListener { launchCamera() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeImage() {
        viewModel.image.observe(viewLifecycleOwner, {
            if (it is Resource.Error) {
                Timber.e(it.exception)
            }

            if (it.succeeded) {
                Glide.with(this).load((it as Resource.Success).data).into(binding.documentImage)
            }
        })
    }

    private fun observeRecognizedText() {
        viewModel.recognizedText.observe(viewLifecycleOwner, {
            if (it is Resource.Error) {
                Timber.e(it.exception)
            }

            if (it.succeeded) {
                binding.resultLabel.text = (it as Resource.Success).data
            }
        })
    }

    private fun observeRecognizedFaces() {
        viewModel.recognizedFaces.observe(viewLifecycleOwner, {
            if (it is Resource.Error) {
                Timber.e(it.exception)
                Toast.makeText(context, R.string.error_processing_faces, Toast.LENGTH_SHORT).show()
            }

            if (it.succeeded) {
                val message = when ((it as Resource.Success).data) {
                    0 -> getString(R.string.recognize_no_faces)
                    1 -> getString(R.string.recognize_one_face)
                    else -> getString(R.string.recognize_more_then_one_face)
                }
                binding.resultLabel.append("\n$message")
            }
        })
    }

    private fun observeLoadingIndicator() {
        viewModel.loading.observe(viewLifecycleOwner, {
            binding.captureButton.visibility = if (!it) View.VISIBLE else View.INVISIBLE
            binding.progress.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })
    }

    private fun launchCamera() {
        cameraFileHelper.createNewPhotoUri()?.let {
            viewModel.currentPhotoUri = it
            takePictureCallback.launch(it)
        }
    }
}

class CameraFileHelper(private val context: Context) {

    companion object {
        private const val FILE_PROVIDER_AUTHORITY = "com.serhii.veriff_assignment"
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    fun createNewPhotoUri(): Uri? {
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            Timber.e(ex)
            null
        }
        return if (photoFile != null) {
            FileProvider.getUriForFile(context, FILE_PROVIDER_AUTHORITY, photoFile)
        } else {
            null
        }
    }
}
