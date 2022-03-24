package com.thisapp.databasepelanggan.ui.kalkulator

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.thisapp.databasepelanggan.R
import com.thisapp.databasepelanggan.databinding.KalkulatorFragmentBinding

class KalkulatorFragment : Fragment() {

    companion object {
        fun newInstance() = KalkulatorFragment()
    }

    private lateinit var viewModel: KalkulatorViewModel
    private var _binding : KalkulatorFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(KalkulatorViewModel::class.java)
        _binding = KalkulatorFragmentBinding.inflate(inflater,container, false)
        val root: View = binding.root
        viewModel.text.observe(viewLifecycleOwner, {
            binding.textv.text= it
        })
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

