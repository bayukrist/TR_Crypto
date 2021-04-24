package com.baykris.tr_crypto.ui.projectProfile

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.baykris.tr_crypto.R
import com.baykris.tr_crypto.buy_activity
import com.baykris.tr_crypto.core.common.BaseActivity
import com.baykris.tr_crypto.data.local.database.CoinsListEntity
import com.baykris.tr_crypto.databinding.ActivityProjectProfileBinding
import com.baykris.tr_crypto.sell_activity
import com.baykris.tr_crypto.util.ChartHelper
import com.baykris.tr_crypto.util.Constants
import com.baykris.tr_crypto.util.ImageLoader
import com.baykris.tr_crypto.util.UIHelper
import com.baykris.tr_crypto.util.extensions.doOnChange
import com.baykris.tr_crypto.util.extensions.dollarString
import com.baykris.tr_crypto.util.extensions.emptyIfNull
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_project_profile.*



@AndroidEntryPoint
class ProjectProfileActivity : BaseActivity() {

    private val viewModel: ProjectProfileViewModel by viewModels()
    private lateinit var binding: ActivityProjectProfileBinding

    private var symbol: String? = null
    private var symbolId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_project_profile)
        binding.apply {
            lifecycleOwner = this@ProjectProfileActivity
            viewModel = this@ProjectProfileActivity.viewModel
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val buyCoin = findViewById<Button>(R.id.buttonBuy)
        val sellCoin = findViewById<Button>(R.id.buttonSell)
        val hargaCoin = findViewById<TextView>(R.id.coinItemPriceTextView)
        val namaCoin = findViewById<TextView>(R.id.coinItemNameTextView)
        val logoCoin = findViewById<ImageView>(R.id.coinItemImageView)

        if (intent?.hasExtra(Constants.EXTRA_SYMBOL) == true) {
            symbol = intent?.getStringExtra(Constants.EXTRA_SYMBOL)
        }

        if (intent?.hasExtra(Constants.EXTRA_SYMBOL_ID) == true) {
            symbolId = intent?.getStringExtra(Constants.EXTRA_SYMBOL_ID)
        }

        supportActionBar?.title = symbol ?: ""
        observeViewModel()

        viewModel.historicalData(symbolId)

        buyCoin.setOnClickListener{
            val stringHargaCoin = hargaCoin.text.toString()
            val stringNamaCoin = namaCoin.text.toString()
            logoCoin.buildDrawingCache()
            val imageCoin: Bitmap = logoCoin.getDrawingCache()

            val bundle = Bundle()
            bundle.putParcelable("LogoCoin",imageCoin)
            val intent = Intent(this@ProjectProfileActivity, buy_activity::class.java)
            intent.putExtra("HargaCoin",stringHargaCoin)
            intent.putExtra("NamaCoin",stringNamaCoin)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        sellCoin.setOnClickListener{
            val stringHargaCoin = hargaCoin.text.toString()
            val stringNamaCoin = namaCoin.text.toString()
            logoCoin.buildDrawingCache()
            val imageCoin: Bitmap = logoCoin.getDrawingCache()

            val bundle = Bundle()
            bundle.putParcelable("LogoCoin",imageCoin)
            val intent = Intent(this@ProjectProfileActivity, sell_activity::class.java)
            intent.putExtra("HargaCoin",stringHargaCoin)
            intent.putExtra("NamaCoin",stringNamaCoin)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        symbol?.let {
            viewModel.projectBySymbol(it).doOnChange(this) { project ->
                populateViews(project)
            }

            viewModel.historicalData.doOnChange(this) { historicalPriceList ->
               lineChartTitle.text = getString(R.string.line_chart_title).format(30)
               ChartHelper.displayHistoricalLineChart(lineChart, it, historicalPriceList)
            }

            viewModel.dataError.doOnChange(this) { error ->
                if (error) showToast(getString(R.string.historical_data_error))
            }
        }
    }

    private fun populateViews(project: CoinsListEntity) {
        coinItemSymbolTextView.text = project.symbol
        coinItemNameTextView.text = project.name.emptyIfNull()
        coinItemPriceTextView.text = project.price.dollarString()
        UIHelper.showChangePercent(coinItemChangeTextView, project.changePercent)
        ImageLoader.loadImage(coinItemImageView, project.image ?: "")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}