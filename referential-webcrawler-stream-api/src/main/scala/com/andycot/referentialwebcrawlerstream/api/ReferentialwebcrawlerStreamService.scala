package com.andycot.referentialwebcrawlerstream.api

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

/**
  * The referential-webcrawler stream interface.
  *
  * This describes everything that Lagom needs to know about how to serve and
  * consume the ReferentialwebcrawlerStream service.
  */
trait ReferentialwebcrawlerStreamService extends Service {

  def stream: ServiceCall[Source[String, NotUsed], Source[String, NotUsed]]

  override final def descriptor = {
    import Service._

    named("referential-webcrawler-stream")
      .withCalls(
        namedCall("stream", stream)
      ).withAutoAcl(true)
  }
}

