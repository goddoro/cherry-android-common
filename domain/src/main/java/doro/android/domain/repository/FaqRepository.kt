package doro.android.domain.repository

import doro.android.domain.entity.Faq

interface FaqRepository {
    suspend fun getList(): List<Faq>
}