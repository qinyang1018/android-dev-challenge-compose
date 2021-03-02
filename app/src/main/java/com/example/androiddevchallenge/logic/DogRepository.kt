/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.logic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * DogRepository
 *
 * @author Q yang
 * @since 2021/2/25
 */
class DogRepository(private val dataHelper: DogDataHelper) {

    suspend fun getDogs() = withContext(Dispatchers.IO) {
        dataHelper.getDogs()
    }
}
