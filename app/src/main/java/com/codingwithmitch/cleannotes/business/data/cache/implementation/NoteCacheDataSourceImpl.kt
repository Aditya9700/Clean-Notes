package com.codingwithmitch.cleannotes.business.data.cache.implementation

import com.codingwithmitch.cleannotes.business.data.cache.abstraction.NoteCacheDataSource
import com.codingwithmitch.cleannotes.business.domain.model.Note
import com.codingwithmitch.cleannotes.framework.datasource.cache.abstraction.NoteDaoService
import com.codingwithmitch.cleannotes.framework.datasource.cache.database.ORDER_BY_ASC_DATE_UPDATED
import com.codingwithmitch.cleannotes.framework.datasource.cache.database.ORDER_BY_ASC_TITLE
import com.codingwithmitch.cleannotes.framework.datasource.cache.database.ORDER_BY_DESC_DATE_UPDATED
import com.codingwithmitch.cleannotes.framework.datasource.cache.database.ORDER_BY_DESC_TITLE
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteCacheDataSourceImpl
@Inject
constructor(
    private val noteDaoService: NoteDaoService
): NoteCacheDataSource {

    override suspend fun insertNote(note: Note): Long {
        return noteDaoService.insertNote(note)
    }

    override suspend fun deleteNote(primaryKey: String): Int {
        return noteDaoService.deleteNote(primaryKey)
    }

    override suspend fun updateNote(primaryKey: String, newTitle: String, newBody: String?): Int {
        return noteDaoService.updateNote(primaryKey, newTitle, newBody)
    }

    override suspend fun searchNotes(
        query: String,
        filterAndOrder: String,
        page: Int
    ): List<Note> {
        return when{

            filterAndOrder.contains(ORDER_BY_DESC_DATE_UPDATED) ->{
                noteDaoService.searchNotesOrderByDateDESC(
                    query = query,
                    page = page)
            }

            filterAndOrder.contains(ORDER_BY_ASC_DATE_UPDATED) ->{
                noteDaoService.searchNotesOrderByDateASC(
                    query = query,
                    page = page)
            }

            filterAndOrder.contains(ORDER_BY_DESC_TITLE) ->{
                noteDaoService.searchNotesOrderByTitleDESC(
                    query = query,
                    page = page)
            }

            filterAndOrder.contains(ORDER_BY_ASC_TITLE) ->{
                noteDaoService.searchNotesOrderByTitleASC(
                    query = query,
                    page = page)
            }
            else ->
                noteDaoService.searchNotesOrderByDateDESC(
                    query = query,
                    page = page
                )
        }
    }

    override suspend fun getNumNotes(): Int {
        return noteDaoService.getNumNotes()
    }

    override suspend fun insertNotes(notes: List<Note>): LongArray{
        return noteDaoService.insertNotes(notes)
    }
}






















