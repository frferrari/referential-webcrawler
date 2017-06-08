package com.andycot.referentialwebcrawler.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.andycot.referentialwebcrawler.api.ReferentialwebcrawlerService
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.softwaremill.macwire._

class ReferentialwebcrawlerLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new ReferentialwebcrawlerApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new ReferentialwebcrawlerApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[ReferentialwebcrawlerService]
  )
}

abstract class ReferentialwebcrawlerApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with LagomKafkaComponents
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[ReferentialwebcrawlerService](wire[ReferentialwebcrawlerServiceImpl])

  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = ReferentialwebcrawlerSerializerRegistry

  // Register the referential-webcrawler persistent entity
  persistentEntityRegistry.register(wire[ReferentialwebcrawlerEntity])
}
