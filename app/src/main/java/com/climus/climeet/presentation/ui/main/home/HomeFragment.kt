package com.climus.climeet.presentation.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.climus.climeet.R
import com.climus.climeet.databinding.FragmentHomeBinding
import com.climus.climeet.presentation.base.BaseFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.climus.climeet.MainNavDirections
import com.climus.climeet.data.model.response.BannerDetailInfoResponse
import com.climus.climeet.data.model.response.BestFollowGymSimpleResponse
import com.climus.climeet.data.model.response.BestRecordGymDetailInfoResponse
import com.climus.climeet.data.model.response.BestRouteDetailInfoResponse
import com.climus.climeet.data.model.response.UserHomeGymSimpleResponse
import com.climus.climeet.presentation.ui.main.home.recycler.homegym.HomeGymRVAdapter
import com.climus.climeet.presentation.ui.main.home.recycler.popularcrag.FollowOrderPopularCragRVAdapter
import com.climus.climeet.presentation.ui.main.home.recycler.popularcrag.RecordOrderPopularCragRVAdapter
import com.climus.climeet.presentation.ui.main.home.recycler.popularroute.PopularRouteRVAdapter
import com.climus.climeet.presentation.ui.main.home.recycler.popularshorts.PopularShortsRVAdapter
import com.climus.climeet.presentation.ui.main.home.viewpager.best.RankingVPAdapter
import com.climus.climeet.presentation.ui.main.home.viewpager.introduce.BannerFragment
import com.climus.climeet.presentation.ui.main.home.viewpager.introduce.BannerVPAdapter
import com.climus.climeet.presentation.ui.main.shorts.model.ShortsUiData
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private var vpBanner: List<BannerDetailInfoResponse> = emptyList()
    private var recyclerHomeGym: List<UserHomeGymSimpleResponse> = emptyList()
    private var recyclerShorts: List<ShortsUiData> = emptyList()
    private var followOrderRecyclerCrag: List<BestFollowGymSimpleResponse> = emptyList()
    private var recordOrderRecyclerCrag: List<BestRecordGymDetailInfoResponse> = emptyList()
    private var recyclerRoute: List<BestRouteDetailInfoResponse> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBannerListBetweenDates()
        viewModel.getShorts()
        viewModel.getGymRankingOrderFollowCount()
        viewModel.getGymRankingListOrderSelectionCount()
        viewModel.getRouteRankingOrderSelectionCount()
        viewModel.getHomeGyms()
        initEventObserve()
        setupOnClickListener()
        setupBestRanking()
    }

    private fun initEventObserve(){
        repeatOnStarted {
            viewModel?.let { vm ->
                vm.uiState.collect { uiState ->
                    uiState.bannerList?.let { bannerList ->
                        vpBanner = bannerList
                        setupIntroduceBanner(vpBanner)
                    }

                    uiState.homegymList?.let { homegymList ->
                        recyclerHomeGym = homegymList
                        setupHomeGymList()
                    }

                    uiState.shortsList?.let { shortsList ->
                        recyclerShorts = shortsList
                        setupPopularShorts()
                    }

                    uiState.followOrderCragList?.let { cragList ->
                        followOrderRecyclerCrag = cragList
                        setupFollowOrderPopularCrags()
                    }

                    uiState.recordOrderCragList?.let { cragList ->
                        recordOrderRecyclerCrag = cragList
                        Log.d("record", recordOrderRecyclerCrag.toString())
                        setupRecordOrderPopularCrags()
                    }

                    uiState.routeList?.let { routeList ->
                        recyclerRoute = routeList
                        setupPopularRoutes()
                    }
                }
            }
        }
    }

    private fun setupOnClickListener() {

        binding.icHomeSerach.setOnClickListener {
            navigateToSearchCrag()
        }

        binding.tvHomeRankingViewAll.setOnClickListener {
            navigateToBestClimer()
        }

        binding.tvHomeShortsViewAll.setOnClickListener {
            navigateToPopularShorts()
        }

        binding.tvHomeCragViewAll.setOnClickListener {
            navigateToPopularCrags()
        }

        binding.tvHomeRouteViewAll.setOnClickListener {
            navigateToPopularRoutes()
        }

        binding.homeFollowOrder.setOnClickListener {
            binding.homeFollowOrder.setBackgroundResource(R.drawable.rect_mainfill_nostroke_5radius)
            binding.homeFollowOrder.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            binding.homeRecordOrder.setBackgroundResource(R.drawable.rect_grey6fill_nostroke_5radius)
            binding.homeRecordOrder.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            binding.rvHomeFollowOrderPopularCrags.visibility = View.VISIBLE
            binding.rvHomeRecordOrderPopularCrags.visibility = View.INVISIBLE
        }

        binding.homeRecordOrder.setOnClickListener {
            binding.homeRecordOrder.setBackgroundResource(R.drawable.rect_mainfill_nostroke_5radius)
            binding.homeRecordOrder.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            binding.homeFollowOrder.setBackgroundResource(R.drawable.rect_grey6fill_nostroke_5radius)
            binding.homeFollowOrder.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            binding.rvHomeFollowOrderPopularCrags.visibility = View.INVISIBLE
            binding.rvHomeRecordOrderPopularCrags.visibility = View.VISIBLE
        }

    }

    private fun navigateToBestClimer() {
        val action = HomeFragmentDirections.actionHomeFragmentToBestClimerFragment()
        findNavController().navigate(action)
    }

    private fun navigateToPopularShorts() {
        val action = HomeFragmentDirections.actionHomeFragmentToPopularShortsFragment()
        findNavController().navigate(action)
    }

    private fun navigateToPopularCrags() {
        val action = HomeFragmentDirections.actionHomeFragmentToPopularCragsFragment()
        findNavController().navigate(action)
    }

    private fun navigateToPopularRoutes() {
        val action = HomeFragmentDirections.actionHomeFragmentToPopularRoutesFragment()
        findNavController().navigate(action)
    }

    private fun navigateToSearchCrag() {
        val action = MainNavDirections.actionHomeFragmentToSearchCragFragment()
        findNavController().navigate(action)
    }

    private fun setupIntroduceBanner(vpBanner : List<BannerDetailInfoResponse>) {
        val bannerAdapter = BannerVPAdapter(this, binding.vpHomeIntroduceBanner)

        for (bannerInfo in vpBanner) {
            val bannerFragment = BannerFragment(bannerInfo)
            bannerAdapter.addFragment(bannerFragment)
        }

        val viewPager = binding.vpHomeIntroduceBanner
        val itemCount = bannerAdapter.itemCount
        val indicator = binding.tvIndicatorFraction
        viewPager.adapter = bannerAdapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        indicator.text = getString(R.string.viewpager2_banner, 1, itemCount)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            var currentState = 0
            var currentPos = 0

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPos = position
                val currentPageNumber = position + 1
                val totalPages = viewPager.adapter?.itemCount ?: 0
                indicator.text = getString(R.string.viewpager2_banner, currentPageNumber, totalPages)
            }

            override fun onPageScrollStateChanged(state: Int) {
                currentState = state
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (currentState == ViewPager2.SCROLL_STATE_DRAGGING && currentPos == position) {
                    // 현재 위치가 0이고, 좌측으로 스와이프하는 경우
                    if (currentPos == 0 && positionOffsetPixels > 0) {
                        viewPager.currentItem = 1
                    }
                    // 현재 위치가 마지막 페이지이고, 좌측으로 스와이프하는 경우
                    else if (currentPos == viewPager.adapter?.itemCount ?: 0 - 1 && positionOffsetPixels > 0) {
                        viewPager.currentItem = 0
                    }
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })

    }

    private fun setupRecyclerView(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>, orientation: Int) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity(), orientation, false)
    }

    private fun setupHomeGymList() {
        val homeGymRVAdapter = HomeGymRVAdapter(recyclerHomeGym)
        setupRecyclerView(binding.rvHomeHomegym, homeGymRVAdapter, LinearLayoutManager.HORIZONTAL)
    }

    private fun setupBestRanking() {
        val rankingAdapter = RankingVPAdapter(this)
        binding.vpHomeBestRanking.adapter = rankingAdapter

        val tabMenu = arrayListOf(" 완등 ", " 시간 ", " 레벨 ")
        TabLayoutMediator(binding.tbHomeBestRanking, binding.vpHomeBestRanking) { tab, position ->
            tab.text = tabMenu[position]
        }.attach()
    }

    private fun setupPopularShorts() {
        val popularShortsRVAdapter = PopularShortsRVAdapter(recyclerShorts)
        setupRecyclerView(binding.rvHomeShorts, popularShortsRVAdapter, LinearLayoutManager.HORIZONTAL)
    }

    private fun setupFollowOrderPopularCrags() {
        val followOrderPopularCragRVAdapter = FollowOrderPopularCragRVAdapter(followOrderRecyclerCrag)
        setupRecyclerView(binding.rvHomeFollowOrderPopularCrags, followOrderPopularCragRVAdapter, LinearLayoutManager.HORIZONTAL)
    }

    private fun setupRecordOrderPopularCrags() {
        val recordOrderPopularCragRVAdapter = RecordOrderPopularCragRVAdapter(recordOrderRecyclerCrag)
        setupRecyclerView(binding.rvHomeRecordOrderPopularCrags, recordOrderPopularCragRVAdapter, LinearLayoutManager.HORIZONTAL)
    }

    private fun setupPopularRoutes() {
        val popularRouteRVAdapter = PopularRouteRVAdapter(recyclerRoute)
        setupRecyclerView(binding.rvHomePopularRoutes, popularRouteRVAdapter, LinearLayoutManager.HORIZONTAL)
    }

}