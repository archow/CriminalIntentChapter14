package com.example.criminalintentchapter14

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.criminalintentchapter14.databinding.FragmentCrimeDetailBinding
import com.example.criminalintentchapter14.viewmodels.CrimeDetailViewModel
import com.example.criminalintentchapter14.viewmodels.CrimeDetailViewModelFactory
import kotlinx.coroutines.launch
import java.util.Date

class CrimeDetailFragment : Fragment() {
    private var _binding: FragmentCrimeDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
        "Binding should not be null. Is the view visible?"
    }
    private val crimeDetailArgs: CrimeDetailFragmentArgs by navArgs()
    private val crimeDetailViewModel : CrimeDetailViewModel by viewModels {
        CrimeDetailViewModelFactory(crimeDetailArgs.crimeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply { 
            crimeTitle.doOnTextChanged { text, _, _, _ ->  
                crimeDetailViewModel.updateCrime { crime ->
                    crime.copy(title = text.toString())
                }
            }
            crimeSolved.setOnCheckedChangeListener { _, isChecked ->
                crimeDetailViewModel.updateCrime { crime ->
                    crime.copy(isSolved = isChecked)
                }
            }
        }
        
        viewLifecycleOwner.lifecycleScope.launch { 
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                crimeDetailViewModel.crime.collect { crime ->
                    crime?.let {
                        updateUi(it)
                    }
                }
            }
        }

        setFragmentResultListener(DatePickerFragment.REQUEST_KEY_DATE) { _, bundle ->
            val newDate =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE, Date::class.java)
                } else bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date
            newDate?.let {
                crimeDetailViewModel.updateCrime { oldCrime ->
                    oldCrime.copy(date = it)
                }
            }
        }
    }

    private fun updateUi(crime: Crime) {
        binding.apply {
            if (crimeTitle.text.toString() != crime.title) {
                crimeTitle.setText(crime.title)
            }
            crimeDate.apply {
                text = crime.date.toString()
                setOnClickListener {
                    findNavController().navigate(
                        CrimeDetailFragmentDirections.selectDate(crime.date)
                    )
                }
            }
            crimeSolved.isChecked = crime.isSolved

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}