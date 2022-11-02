package com.sample.corexdomain.mapper

/**
 * [mapTo] allows to use a 1to1 mappers  for mapping lists.
 */
fun <T, R> List<T>.mapWith(mapper: Mapper<T, R>): List<R> {
  return map { mapper.map(it) }
}
/**
 * [toList] allows to use a 1to1 mappers  for mapping lists.
 */
fun <T,R> Mapper<T, R>.toList(): Mapper<List<T>, List<R>> {

  return object : Mapper<List<T>, List<R>> {
    override fun map(input: List<T>): List<R> {
      return input?.map {
        map(it)
      }?: listOf()
    }


  }
}