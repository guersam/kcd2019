package com.guersam.kcd2019.domain

trait MemeFinder[F[_]] {
  def findRandomMemeByKeyword(keyword: String): F[Meme]
}
