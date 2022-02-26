package io.github.droidkaigi.confsched2018.presentation.sessions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.android.support.DaggerFragment
import io.github.droidkaigi.confsched2018.R
import io.github.droidkaigi.confsched2018.databinding.FragmentAllSessionsBinding
import io.github.droidkaigi.confsched2018.model.Session
import io.github.droidkaigi.confsched2018.presentation.NavigationController
import io.github.droidkaigi.confsched2018.presentation.Result
import io.github.droidkaigi.confsched2018.presentation.common.view.OnTabReselectedListener
import io.github.droidkaigi.confsched2018.presentation.sessions.item.DateSessionsSection
import io.github.droidkaigi.confsched2018.presentation.sessions.item.SpeechSessionItem
import io.github.droidkaigi.confsched2018.util.ProgressTimeLatch
import io.github.droidkaigi.confsched2018.util.SessionAlarm
import io.github.droidkaigi.confsched2018.util.ext.addOnScrollListener
import io.github.droidkaigi.confsched2018.util.ext.isGone
import io.github.droidkaigi.confsched2018.util.ext.setLinearDivider
import io.github.droidkaigi.confsched2018.util.ext.setTextIfChanged
import io.github.droidkaigi.confsched2018.util.ext.setVisible
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AllSessionsFragment
    : DaggerFragment(), OnTabReselectedListener {

    private lateinit var binding: FragmentAllSessionsBinding

    private val sessionsSection = DateSessionsSection()

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var navigationController: NavigationController
    @Inject lateinit var sessionAlarm: SessionAlarm
    @Inject lateinit var sharedRecycledViewPool: RecyclerView.RecycledViewPool

    private val allSessionsViewModel: AllSessionsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(AllSessionsViewModel::class.java)
    }

    private val onFavoriteClickListener = { session: Session.SpeechSession ->
        allSessionsViewModel.onFavoriteClick(session)
        sessionAlarm.toggleRegister(session)
    }

    private val onFeedbackListener = { session: Session.SpeechSession ->

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllSessionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()

        val progressTimeLatch = ProgressTimeLatch {
            binding.progress.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                allSessionsViewModel.sessions.collect { result ->
                    when (result) {
                        is Result.Success -> {
                            val sessions = result.data
                            sessionsSection.updateSessions(
                                sessions, onFavoriteClickListener,
                                onFeedbackListener, true
                            )
                        }
                        is Result.Failure -> {
                            Timber.e(result.e)
                        }
                        else -> Unit
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                allSessionsViewModel.isLoading.collect { isLoading ->
                    progressTimeLatch.loading = isLoading
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                allSessionsViewModel.refreshResult.collect { result ->
                    when (result) {
                        is Result.Failure -> {
                            // If user is offline, not error. So we write log to debug
                            Timber.d(result.e)
                            val view = view ?: return@collect
                            Snackbar.make(view, R.string.session_fetch_failed, Snackbar.LENGTH_LONG).apply {
                                setAction(R.string.session_load_retry) {
                                    allSessionsViewModel.onRetrySessions()
                                }
                            }.show()
                        }
                        else -> Unit
                    }
                }
            }
        }
        lifecycle.addObserver(allSessionsViewModel)
    }

    override fun onTabReselected() {
        binding.sessionsRecycler.smoothScrollToPosition(0)
    }

    private fun setupRecyclerView() {
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            add(sessionsSection)
            setOnItemClickListener({ item, _ ->
                val sessionItem = item as? SpeechSessionItem ?: return@setOnItemClickListener
                navigationController.navigateToSessionDetailActivity(sessionItem.session)
            })
        }
        binding.sessionsRecycler.apply {
            adapter = groupAdapter
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            addOnScrollListener(
                onScrollStateChanged = { _: RecyclerView?, newState: Int ->
                    if (binding.sessionsRecycler.isGone()) return@addOnScrollListener
                    setDayHeaderVisibility(newState != RecyclerView.SCROLL_STATE_IDLE)
                },
                onScrolled = { _, _, _ ->
                    val linearLayoutManager = layoutManager as LinearLayoutManager
                    val firstPosition = linearLayoutManager.findFirstVisibleItemPosition()
                    val dayNumber = sessionsSection.getDateNumberOrNull(firstPosition)
                    dayNumber ?: return@addOnScrollListener
                    val dayTitle = getString(R.string.session_day_title, dayNumber)
                    binding.dayHeader.setTextIfChanged(dayTitle)
                })
            setLinearDivider(
                R.drawable.shape_divider_vertical_12dp,
                layoutManager as LinearLayoutManager
            )
            setRecycledViewPool(sharedRecycledViewPool)
            (layoutManager as LinearLayoutManager).recycleChildrenOnDetach = true
        }
    }

    private fun setDayHeaderVisibility(visibleDayHeader: Boolean) {
        val transition = TransitionInflater
            .from(context)
            .inflateTransition(R.transition.date_header_visibility)
        TransitionManager.beginDelayedTransition(binding.sessionsConstraintLayout, transition)
        binding.dayHeader.setVisible(visibleDayHeader)
    }

    companion object {
        fun newInstance(): AllSessionsFragment = AllSessionsFragment()
    }
}
