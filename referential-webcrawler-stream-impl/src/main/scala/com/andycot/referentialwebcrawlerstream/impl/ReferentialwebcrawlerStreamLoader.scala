package com.andycot.referentialwebcrawlerstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.andycot.referentialwebcrawlerstream.api.ReferentialwebcrawlerStreamService
import com.andycot.referentialwebcrawler.api.ReferentialwebcrawlerService
import com.softwaremill.macwire._

class ReferentialwebcrawlerStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new ReferentialwebcrawlerStreamApplication(context) {
      override def serviceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new ReferentialwebcrawlerStreamApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[ReferentialwebcrawlerStreamService]
  )
}

abstract class ReferentialwebcrawlerStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[ReferentialwebcrawlerStreamService](wire[ReferentialwebcrawlerStreamServiceImpl])

  // Bind the ReferentialwebcrawlerService client
  lazy val referentialwebcrawlerService = serviceClient.implement[ReferentialwebcrawlerService]
}
