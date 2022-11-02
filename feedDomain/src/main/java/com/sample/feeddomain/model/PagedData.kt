package com.sample.feeddomain.model

data class PagedData<PaginatedData>(
  val pagination: Pagination,
  val data: PaginatedData?,
)
