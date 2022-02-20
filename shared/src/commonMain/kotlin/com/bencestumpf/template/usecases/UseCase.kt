package com.bencestumpf.template.usecases

import kotlinx.coroutines.flow.Flow

interface UseCase<Parameter, Result> {
    suspend fun execute(parameter: Parameter): Result
}

interface FlowableUseCase<Parameter, Result> {
    fun execute(parameter: Parameter): Flow<Result>
}
