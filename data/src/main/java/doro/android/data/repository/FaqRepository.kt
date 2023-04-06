package doro.android.data.repository

import doro.android.data.service.FaqService
import doro.android.domain.entity.Faq
import doro.android.domain.repository.FaqRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FaqRepositoryImpl @Inject constructor(
    private val faqService: FaqService,
) : FaqRepository {

    override suspend fun getList(): List<Faq> = withContext(Dispatchers.IO){
        faqService.getFaqList().faqs.map { it.toDomain()}
    }
}