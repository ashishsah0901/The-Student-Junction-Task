package com.example.tsjtask.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.example.tsjtask.R
import com.example.tsjtask.adapter.PersonRecyclerViewAdapter
import com.example.tsjtask.data.Result
import com.example.tsjtask.databinding.FragmentMainBinding
import com.example.tsjtask.ui.MainActivity.Companion.viewModel
import com.example.tsjtask.util.Constants
import com.example.tsjtask.util.Constants.INTENT_NAME
import com.example.tsjtask.util.Resource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainFragment : Fragment(), PersonRecyclerViewAdapter.OnPersonClick {
    private lateinit var adapter: PersonRecyclerViewAdapter
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PersonRecyclerViewAdapter(requireContext(), this)
        binding.personRecyclerView.adapter = adapter
        binding.personRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        viewModel.person.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showLoadingState()
                }
                is Resource.Error -> {
                    showError(it.message.toString())
                }
                is Resource.Success -> {
                    showData(it.data!!.results)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            binding.personRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        } else if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.personRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.personRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.personRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
    }

    override fun onClick(result: Result) {
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment, Bundle().apply { putSerializable(INTENT_NAME, result) })
    }

    private fun showLoadingState() {
        binding.loadingPB.isVisible = true
        binding.errorMessageTV.isVisible = false
        binding.personRecyclerView.isVisible = false
    }

    private fun showData(result: List<Result>) {
        binding.loadingPB.isVisible = false
        binding.errorMessageTV.isVisible = false
        binding.personRecyclerView.isVisible = true
        adapter.differ.submitList(result)
    }

    private fun showError(error: String) {
        binding.loadingPB.isVisible = false
        binding.errorMessageTV.isVisible = true
        binding.personRecyclerView.isVisible = false
        binding.errorMessageTV.text = error
    }

    private fun loadData() {
        viewModel.getRandomPerson(Constants.COUNT)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.logout_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.refresh_menu_BT) {
            viewModel.result = null
            loadData()
        }
        if(item.itemId == R.id.logout_menu_BT) {
            Firebase.auth.signOut()
            val intent = Intent(requireActivity(), SignInActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}