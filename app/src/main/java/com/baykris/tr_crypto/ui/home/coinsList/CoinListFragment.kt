package com.baykris.tr_crypto.ui.home.coinsList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.baykris.tr_crypto.R
import com.baykris.tr_crypto.adapters.CoinsListAdapter
import com.baykris.tr_crypto.adapters.OnItemClickCallback
import com.baykris.tr_crypto.databinding.FragmentListBinding
import com.baykris.tr_crypto.core.common.MainNavigationFragment
import com.baykris.tr_crypto.ui.home.coinsList.CoinListViewModel
import com.baykris.tr_crypto.ui.projectProfile.ProjectProfileActivity
import com.baykris.tr_crypto.util.Constants
import com.baykris.tr_crypto.util.extensions.doOnChange
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*

@AndroidEntryPoint
class CoinListFragment : MainNavigationFragment(), OnItemClickCallback {

    private val viewModel: CoinListViewModel by viewModels()
    private lateinit var binding: FragmentListBinding
    private var coinsListAdapter = CoinsListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@CoinListFragment.viewModel
            }
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        viewModel.loadCoinsFromApi()
    }

    override fun initializeViews() {
        coinsListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = coinsListAdapter
        }
    }

    override fun observeViewModel() {
        viewModel.isLoading.doOnChange(this) {
            coinsListLoading.visibility =
                if (viewModel.isListEmpty() && it) View.VISIBLE else View.GONE

            if (it) {
                coinsListErrorView.visibility = View.GONE
            }
        }

        viewModel.coinsListData.doOnChange(this) {
            coinsListAdapter.updateList(it)

            coinsListErrorView.visibility =
                if (viewModel.isListEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.favouriteStock.doOnChange(this) {
            it?.let {
                showToast(
                    getString(if (it.isFavourite) R.string.added_to_favourite else R.string.removed_to_favourite).format(
                        it.symbol
                    )
                )
            }
        }
    }

    override fun onItemClick(symbol: String, id: String) {
        requireActivity().run {
            startActivity(
                Intent(this, ProjectProfileActivity::class.java)
                    .apply {
                        putExtra(Constants.EXTRA_SYMBOL, symbol)
                        putExtra(Constants.EXTRA_SYMBOL_ID, id)
                    }
            )
        }

    }

    override fun onFavouriteClicked(symbol: String) {
        viewModel.updateFavouriteStatus(symbol)
    }
}