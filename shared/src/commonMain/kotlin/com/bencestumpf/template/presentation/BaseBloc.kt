package com.bencestumpf.template.presentation

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.bencestumpf.template.di.CoroutineDispatchers
import com.bencestumpf.template.usecases.UseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

abstract class BaseBloc<State : Any, Action : Any> : Bloc<State, Action>, KoinComponent,
    InstanceKeeper.Instance {

    private val dispatcher by inject<CoroutineDispatcher>(named(CoroutineDispatchers.Default))

    private var commonJob = SupervisorJob()

    internal val clientScope =
        CoroutineScope(dispatcher + commonJob) + CoroutineExceptionHandler { _, throwable ->
            println("Main Coroutine exception: $throwable")
        }

    private val currentJobs = ArrayList<Job>()

    fun <T> Flow<T>.toSharedFlow() = shareIn(clientScope, SharingStarted.Lazily, replay = 1)

    protected fun <In> run(useCase: UseCase<In, Unit>, parameter: In) {
        currentJobs += clientScope.launch {
            useCase.execute(parameter)
        }
    }

    override fun onDestroy() {
        if (commonJob.isCompleted.not()) {
            commonJob.complete()
        }
    }
}
