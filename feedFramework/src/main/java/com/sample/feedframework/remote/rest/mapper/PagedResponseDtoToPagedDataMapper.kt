package com.sample.feedframework.remote.rest.mapper


import com.sample.corexdomain.mapper.Mapper
import com.sample.feeddomain.model.PagedData
import com.sample.feeddomain.model.Pagination
import com.sample.feedframework.remote.rest.dto.PagedDataResponseDto

class PagedResponseDtoToPagedDataMapper<PagedDataResponse, DataType>(
    private val dataResponseMapper: Mapper<PagedDataResponse, DataType>,
) : Mapper<PagedDataResponseDto<PagedDataResponse>, PagedData<DataType>> {

    override fun map(input: PagedDataResponseDto<PagedDataResponse>): PagedData<DataType> =
        PagedData(
            pagination = Pagination(
                count = input?.info?.count ?: 0,
                pages = input?.info?.pages ?: 0,
                next = input?.info?.next,
                prev = input?.info?.prev,
            ),
            data = input.results?.let {  dataResponseMapper.map(input.results)}
        )
}