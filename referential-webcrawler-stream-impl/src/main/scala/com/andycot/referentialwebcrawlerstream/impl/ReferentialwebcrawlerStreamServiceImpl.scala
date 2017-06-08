package com.andycot.referentialwebcrawlerstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.andycot.referentialwebcrawlerstream.api.ReferentialwebcrawlerStreamService
import com.andycot.referentialwebcrawler.api.ReferentialwebcrawlerService

import scala.concurrent.Future

/**
  * Implementation of the ReferentialwebcrawlerStreamService.
  */
class ReferentialwebcrawlerStreamServiceImpl(referentialwebcrawlerService: ReferentialwebcrawlerService) extends ReferentialwebcrawlerStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(referentialwebcrawlerService.hello(_).invoke()))
  }
}
