package com.utkuglsvn.roombasic.ui.splashUi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.utkuglsvn.roombasic.R
import com.utkuglsvn.roombasic.databinding.FragmentSplashBinding
import com.utkuglsvn.roombasic.utils.isShowActionBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SPLASH_TIME:Long = 2000

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().isShowActionBar(false)
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(SPLASH_TIME)
            navigateToFragment()
        }
    }

    private fun navigateToFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_listFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}