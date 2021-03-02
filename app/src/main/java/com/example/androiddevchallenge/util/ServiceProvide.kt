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
package com.example.androiddevchallenge.util

import com.example.androiddevchallenge.logic.DogDataHelper
import com.example.androiddevchallenge.logic.DogRepository

/**
 * ServiceProvide
 *
 * @author Q yang
 * @since 2020/2/25
 */
object ServiceProvide {

    fun provideRepository() = DogRepository(provideDataHelper())

    private fun provideDataHelper() = DogDataHelper()
}
